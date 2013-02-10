package latros.z.guilds.Listeners;

import latros.z.guilds.Main;
import latros.z.guilds.Functions.Util.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener{
	
	static String playersName;
	static String playersGuildPrefix;
	static String playersCurrentGuild;
	static Player talker;
	static CommandSender s;
	@EventHandler
	public boolean onPlayerChat(AsyncPlayerChatEvent e) {
		s = (CommandSender) e.getPlayer();
		talker = e.getPlayer();
		if(Util.isInGuild(s) == false){
			e.getPlayer().setDisplayName(e.getPlayer().getName());
			return false;
		}
		playersName = e.getPlayer().getName().toLowerCase();
		playersCurrentGuild = Main.players.getString("Players." + playersName + ".Current_Guild");
		playersGuildPrefix = Main.guilds.getString("Guilds." + playersCurrentGuild + ".Chat_Prefix");
		talker.setDisplayName(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + playersGuildPrefix + ChatColor.DARK_GRAY + "]" + " " + ChatColor.WHITE + e.getPlayer().getName());
		return true;
	}
}