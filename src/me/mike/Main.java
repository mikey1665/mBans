package me.mike;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.mike.Files.Configuration;
import me.mike.Files.Messages;

/*
 * mBan a temporary banning solution
 * Uses Data to store banning info
 * Lightweight and easy to use!
 * @author: mike1665
 */

public class Main extends JavaPlugin{
	
	public static Main plugin;
	public Configuration dataFile;
	public Messages messagesFile;
	
	@Override
	public void onEnable(){
		plugin = this;
		loadConfigManager();
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		getCommand("tempban").setExecutor(new tempBanCommand());
		getCommand("pardon").setExecutor(new pardonCommand());
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[mBan]" + "-" + ChatColor.GREEN + " Enabled!");
	}
	
	@Override
	public void onDisable(){
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[mBan]" + "-" + ChatColor.RED + " Disabled!");
	}
	
	public void loadConfigManager(){
		dataFile = new Configuration();
		messagesFile = new Messages();
		dataFile.setup();
		messagesFile.setup();
		dataFile.saveData();
		messagesFile.saveData();
		dataFile.reloadData();
		messagesFile.reloadData();
	}
	
	public Configuration getDataInstance(){
		return dataFile;
	}
	
	public Messages getCustomMessages(){
		return messagesFile;
	}
}
