package me.mike;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tempBanCommand implements CommandExecutor {

	public Main plugin = Main.getPlugin(Main.class);

	String warning = ChatColor.RED + "" + ChatColor.BOLD + "!" + ChatColor.RESET + ChatColor.YELLOW + " ";

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (sender.hasPermission("mban.tempban") || sender.isOp()) {
				Player player = (Player) sender;
				if (args.length == 0) {
					player.sendMessage(warning + "/tempban {player} {amount in d,h,m,s} {reason}");
					return false;
				} else {
					if (args.length == 1) {
						player.sendMessage(warning + "Please specify a time & reason!");
						return false;
					}
					if (args.length == 2) {
						player.sendMessage(warning + "Please specify a reason!");
						return false;
					}
					if (args.length >= 3) {
						StringBuilder str = new StringBuilder();
						for (int i = 2; i < args.length; i++) {
							str.append(args[i] + " ");
						}
						String reason = str.toString();
						Player target = Bukkit.getServer().getPlayer(args[0]);
						if (target == null) {
							player.sendMessage(warning + args[0] + " is not online!");
							return false;
						}
						Date currDate = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy HH:mm:ss");
						Calendar remaningDate = Calendar.getInstance();

						if (args[1].contains("d")) {
							String[] time = args[1].split("d");
							int amount = Integer.parseInt(time[0].trim());
							remaningDate.add(Calendar.DAY_OF_WEEK, amount);
						} else if (args[1].contains("h")) {
							String[] time = args[1].split("h");
							int amount = Integer.parseInt(time[0].trim());
							remaningDate.add(Calendar.HOUR_OF_DAY, amount);
						} else if (args[1].contains("m")) {
							String[] time = args[1].split("m");
							int amount = Integer.parseInt(time[0].trim());
							remaningDate.add(Calendar.MINUTE, amount);
						} else if (args[1].contains("s")) {
							String[] time = args[1].split("s");
							int amount = Integer.parseInt(time[0].trim());
							remaningDate.add(Calendar.SECOND, amount);
						}
						Date until = remaningDate.getTime();

						plugin.getDataInstance().getData().set(target.getUniqueId().toString() + ".Time",
								dateFormat.format(remaningDate.getTime()));
						plugin.getDataInstance().getData().set(target.getUniqueId().toString() + ".Reason", reason);
						plugin.getDataInstance().saveData();

						long diff = until.getTime() - currDate.getTime();
						long diffSeconds = diff / 1000 % 60;
						long diffMinutes = diff / (60 * 1000) % 60;
						long diffHours = diff / (60 * 60 * 1000) % 24;
						long diffDays = diff / (24 * 60 * 60 * 1000);

						target.kickPlayer(ChatColor.translateAlternateColorCodes('&',
								plugin.getCustomMessages().getData().getString("prefix") + "\n"
										+ plugin.getCustomMessages().getData().getString("tempban-message") + "\n\n"
										+ plugin.getCustomMessages().getData().getString("timeremaining-message")
										+ "\n&l" + diffDays + "&lD " + diffHours + "&lH " + diffMinutes + "&lM "
										+ diffSeconds + "&lS"));
					}
				}
			}
		}
		return false;
	}
}
