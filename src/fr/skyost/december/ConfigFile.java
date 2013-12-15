package fr.skyost.december;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import fr.skyost.december.utils.Config;

public class ConfigFile extends Config {
	
	public List<String> Worlds = new ArrayList<String>();
	
	public boolean Spout_AlwaysSnowing = true;
	
	public boolean Enable_Updater = true;
	public boolean Enable_Luge = true;
	public boolean Enable_ChristmasMobs = true;
	
	public int Presents_Delay = 600;
	public List<Material> Presents_RandomItem = new ArrayList<Material>();
	public Material Presents_Icon = Material.CHEST;
	public String Presents_Name = "§4P§fr§4e§fs§4e§fn§4t";
	public ArrayList<String> Presents_Lore = new ArrayList<String>();
	public String Presents_GiveMessage = "§6Here is a nice present for you !";
	public String Presents_OpenMessage = "Merry Christmas !";
	
	public ConfigFile(Plugin plugin) {
		CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");
		CONFIG_HEADER = "Christmas Config";
		
		Presents_Lore.add("§4From: Mother");
		Presents_Lore.add("§fTo: My child");
		Presents_Lore.add("§oRight click to open.");
		
		Presents_RandomItem.add(Material.RED_ROSE);
		Presents_RandomItem.add(Material.WOOD_SWORD);
		Presents_RandomItem.add(Material.GOLD_NUGGET);
		Presents_RandomItem.add(Material.COAL);
		Presents_RandomItem.add(Material.SNOW_BALL);
		
		Worlds.add("WorldA");
		Worlds.add("WorldB");
		Worlds.add("WorldC");
	}
	
}
