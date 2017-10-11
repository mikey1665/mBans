package me.mike;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class EventListener implements Listener {

	public Main plugin = Main.getPlugin(Main.class);

	@EventHandler
	public void playerJoin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		if (plugin.getDataInstance().getData().contains(player.getUniqueId().toString())) {
			Date currentDate = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy HH:mm:ss");
			try {
				Date remainingDate = dateFormat.parse(plugin.getDataInstance().getData().getString(player.getUniqueId().toString() + ".Time"));
				long diff = remainingDate.getTime() - currentDate.getTime();
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				if(!currentDate.after(remainingDate)){
					event.disallow(Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', 
							plugin.getCustomMessages().getData().getString("prefix") + "\n"
							+ plugin.getCustomMessages().getData().getString("tempban-message") + "\n\n"
							+ plugin.getCustomMessages().getData().getString("timeremaining-message") + "\n&l"
							+ diffDays +"&lD "+ diffHours +"&lH "+ diffMinutes +"&lM "+ diffSeconds + "&lS"));
				} else{
					plugin.getDataInstance().getData().set(player.getUniqueId().toString(), null);
					plugin.getDataInstance().saveData();
					event.allow();
				}
			} catch (ParseException e) {
				Bukkit.getConsoleSender().sendMessage("Could not retrieve the date from config!");
			}
		}
	}

}
