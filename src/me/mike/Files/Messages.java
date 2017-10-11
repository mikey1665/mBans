package me.mike.Files;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.mike.Main;

public class Messages {
	private Main plugin = Main.getPlugin(Main.class);
	String configName = "Messages.yml";

	public FileConfiguration messages;
	public File messagesFile;

	public void setup() {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		messagesFile = new File(plugin.getDataFolder(), configName);
		if (!messagesFile.exists()) {
			try {
				messagesFile.createNewFile();
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.GREEN + configName + " successfully loaded!");
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.RED + configName + " could not be created!");
			}
		}
		messages = YamlConfiguration.loadConfiguration(messagesFile);
	}

	public void saveData() {
		try {
			messages.save(messagesFile);
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + configName + " successfully saved!");
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + configName + " could not be saved!");
		}
	}

	public void reloadData() {
		messages = YamlConfiguration.loadConfiguration(messagesFile);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + configName + " has been reloaded!");
	}

	public FileConfiguration getData() {
		return messages;
	}
}
