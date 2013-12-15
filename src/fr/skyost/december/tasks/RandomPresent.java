package fr.skyost.december.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.skyost.december.Christmas;

public class RandomPresent implements Runnable {
	
	private String playername;
	
	public RandomPresent(final String playername) {
		this.playername = playername;
	}

	@Override
	public void run() {
		final Player player = Bukkit.getPlayer(playername);
		if(player != null && Christmas.config.Worlds.contains(player.getWorld().getName())) {
			player.sendMessage(Christmas.config.Presents_GiveMessage);
			player.getInventory().addItem(Christmas.present);
		}
	}

}
