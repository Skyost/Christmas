package fr.skyost.december;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import fr.skyost.december.listeners.EventsListener;

public class Christmas extends JavaPlugin {
	
	public static final HashMap<String, Integer> tasksIDs = new HashMap<String, Integer>();
	public static ConfigFile config;
	public static Plugin plugin;
	public static ItemStack present = new ItemStack(Material.CHEST);
	
	@Override
	public final void onEnable() {
		try {
			plugin = this;
			config = new ConfigFile(this);
			config.init();
			buildPresentItemStack();
			if(Bukkit.getPluginManager().getPlugin("Spout") != null) {
				if(Christmas.config.Spout_AlwaysSnowing) {
					Bukkit.getConsoleSender().sendMessage("[Christmas] Now using " + ChatColor.BLUE + "Spout" + ChatColor.RESET + ".");
				}
			}
			Bukkit.getPluginManager().registerEvents(new EventsListener(), this);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private final void buildPresentItemStack() {
		final ItemMeta presentMeta = present.getItemMeta();
		presentMeta.setDisplayName(Christmas.config.Presents_Name);
		presentMeta.setLore(Christmas.config.Presents_Lore);
		present.setItemMeta(presentMeta);
	}
	
}
