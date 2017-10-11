package me.mike;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class pardonCommand implements CommandExecutor {

	public Main plugin = Main.getPlugin(Main.class);
	String warning = ChatColor.RED + "" + ChatColor.BOLD + "!" + ChatColor.RESET + ChatColor.YELLOW + " ";

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("mban.pardon") || sender.isOp()) {
			if (args.length == 0) {
				sender.sendMessage(warning + "/pardon {player}");
				return false;
			} else {
				String target = args[0];
				OfflinePlayer op = Bukkit.getOfflinePlayer(target);
				plugin.getDataInstance().getData().set(op.getUniqueId().toString(), null);
				plugin.getDataInstance().saveData();
			}
		}
		return false;
	}

}
