package me.mike.Files;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.mike.Main;

public class Configuration {
	
	private Main plugin = Main.getPlugin(Main.class);
	String configName = "banData.yml";
	
	public FileConfiguration banData;
	public File banFile;
	
	public void setup() {
		if(!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		banFile = new File(plugin.getDataFolder(), configName);
		if(!banFile.exists()) {
			try {
				banFile.createNewFile();
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + configName + " successfully loaded!");
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + configName + " could not be created!");
			}
		}
		banData = YamlConfiguration.loadConfiguration(banFile);
	}
	
	public void saveData() {
		try {
			banData.save(banFile);
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + configName + " successfully saved!");
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + configName + " could not be saved!");
		}
	}
	
	public void reloadData() {
		banData = YamlConfiguration.loadConfiguration(banFile);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + configName + " has been reloaded!");
	}
	
	public FileConfiguration getData() {
		return banData;
	}
}
