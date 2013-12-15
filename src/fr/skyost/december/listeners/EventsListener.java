package fr.skyost.december.listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.block.SpoutWeather;
import org.getspout.spoutapi.player.SpoutPlayer;

import fr.skyost.december.Christmas;
import fr.skyost.december.tasks.RandomPresent;

@SuppressWarnings("deprecation")
public class EventsListener implements Listener {
	
	@EventHandler
	private final void onPlayerJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		if(Christmas.tasksIDs.get(player.getName()) == null) {
			Christmas.tasksIDs.put(player.getName(), Bukkit.getScheduler().scheduleSyncRepeatingTask(Christmas.plugin, new RandomPresent(player.getName()), Christmas.config.Presents_Delay * 20, Christmas.config.Presents_Delay * 20));
		}
		if(Christmas.config.Spout_AlwaysSnowing) {
			if(player instanceof SpoutPlayer) {
				System.out.println("true");
				Bukkit.getScheduler().scheduleSyncDelayedTask(Christmas.plugin, new Runnable() {

					@Override
					public void run() {
						SpoutManager.getBiomeManager().setPlayerWeather(SpoutManager.getPlayer(player), SpoutWeather.SNOW);
					}
					
				}, 60);
			}
		}
	}
	
	@EventHandler
	private final void onPlayerQuit(final PlayerQuitEvent event) {
		final String player = event.getPlayer().getName();
		final Integer taskID = Christmas.tasksIDs.get(player);
		if(taskID != null) {
			Bukkit.getScheduler().cancelTask(taskID);
			Christmas.tasksIDs.remove(player);
		}
	}
	
	@EventHandler
	private final void onPlayerInteract(final PlayerInteractEvent event) {
		final Player player = event.getPlayer();
		if(Christmas.config.Worlds.contains(player.getWorld().getName())) {
			final Action act = event.getAction();
			if(act == Action.RIGHT_CLICK_BLOCK) {
				final ItemStack inHand = player.getItemInHand();
				if(inHand.isSimilar(Christmas.present)) {
					final ItemStack present = new ItemStack(Christmas.config.Presents_RandomItem.get(new Random().nextInt(Christmas.config.Presents_RandomItem.size())));
					present.setAmount(inHand.getAmount());
					player.setItemInHand(present);
					player.sendMessage(Christmas.config.Presents_OpenMessage);
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	private final void onVehicleCreate(final VehicleCreateEvent event) {
		final Vehicle vehicle = event.getVehicle();
		if(Christmas.config.Worlds.contains(vehicle.getWorld().getName())) {
			if(Christmas.config.Enable_Luge) {
				if(vehicle.getType() == EntityType.BOAT) {
					Boat boat = (Boat)vehicle;
					boat.setWorkOnLand(true);
					boat.setMaxSpeed(2D);
				}
			}
		}
	}
	
	@EventHandler
	private final void onPlayerMove(final PlayerMoveEvent event) {
		final Player player = event.getPlayer();
		if(player.isInsideVehicle()) {
			final Entity vehicle = player.getVehicle();
			if(vehicle.getType() == EntityType.BOAT) {
				if(player.getLocation().getBlock().getRelative(0,-1,0).getType() != Material.AIR) {
					vehicle.setVelocity(vehicle.getVelocity().setY(0.8));
				}
			}
		}
	}
	
	@EventHandler
	private final void onVehicleDestory(final VehicleDestroyEvent event) {
		final Vehicle vehicle = event.getVehicle();
		if(Christmas.config.Worlds.contains(vehicle.getWorld().getName())) {
			if(Christmas.config.Enable_Luge) {
				if(vehicle.getType() == EntityType.BOAT) {
					if(event.getAttacker() == null) {
						event.setCancelled(true);
					}
				}
			}
		}
    }
	
	@EventHandler
	private final void onCreatureSpawn(final CreatureSpawnEvent event) {
		final LivingEntity entity = event.getEntity();
		if(Christmas.config.Worlds.contains(entity.getWorld().getName())) {
			if(Christmas.config.Enable_ChristmasMobs) {
				if(entity.getType() == EntityType.ZOMBIE) {
					Zombie zombie = (Zombie)event.getEntity();
				    if(zombie.isBaby()) {
				    	return;
				    }
				}
				EntityEquipment equip = entity.getEquipment();
			    ItemStack leather = new ItemStack(Material.LEATHER_CHESTPLATE);
			    LeatherArmorMeta meta = (LeatherArmorMeta)leather.getItemMeta();
			    meta.setColor(Color.RED);
			    leather.setItemMeta(meta);
			    equip.setChestplate(leather);
			    leather = new ItemStack(Material.LEATHER_BOOTS);
			    meta = (LeatherArmorMeta)leather.getItemMeta();
			    meta.setColor(Color.RED);
			    leather.setItemMeta(meta);
			    equip.setBoots(leather);
			    leather = new ItemStack(Material.LEATHER_HELMET);
			    meta = (LeatherArmorMeta)leather.getItemMeta();
			    meta.setColor(Color.RED);
			    leather.setItemMeta(meta);
			    equip.setHelmet(leather);
			}
		}
	}

}
