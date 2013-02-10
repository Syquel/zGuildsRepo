package latros.z.guilds.Functions;

import latros.z.guilds.Main;
import latros.z.guilds.Functions.Util.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class RemoveGuild {

	static String sendersPlayerName;
	static String sendersCurrentGuild;
	static String guildNameRepresentation;
	static String sendersActualGuildName;
	
	public static boolean disband(String[] args, CommandSender s){
		
		// Various checks to prevent the guild creation from going through if it shouldn't happen
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.disband")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to disband a guild. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!Util.isInGuild(s) == true){
			//Checking if they're already in a guild
			s.sendMessage(ChatColor.RED + "You need to be in a guild to use this command.");
			return false;
		}
		if(args.length > 1){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild disband command! Proper syntax is: \"/guild disband\"");
			return false;
		}
		if(Util.isGuildLeader(s.getName()) == false){
			//Checking if the player is the guild leader
			s.sendMessage(ChatColor.RED + "You need to be the guild leader to use that command.");
			return false;
		}
		
		//Wiping this guild!!!!!!!!
		sendersPlayerName = s.getName().toLowerCase();
		sendersCurrentGuild = Main.players.getString("Players." + sendersPlayerName + ".Current_Guild");
		sendersActualGuildName = Main.guilds.getString("Guilds." + sendersCurrentGuild + ".Chat_Prefix");
		Main.guilds.set("Guilds." + sendersCurrentGuild, null);
		for(String playerNameRepresentation : Main.players.getConfigurationSection("Players").getKeys(false)){
			playerNameRepresentation = playerNameRepresentation.toLowerCase();
			guildNameRepresentation = Main.players.getString("Players." + playerNameRepresentation + ".Current_Guild");
			if(guildNameRepresentation.matches(sendersCurrentGuild)){
				Main.players.set("Players." + playerNameRepresentation + ".Guild_Leader", false);
				Main.players.set("Players." + playerNameRepresentation + ".Current_Guild", "None");
				Main.players.set("Players." + playerNameRepresentation + ".Current_Rank", 0);
				Main.players.set("Players." + playerNameRepresentation + ".Guild_Contributions", 0);
				Main.players.set("Players." + playerNameRepresentation + ".Member_Since", "N/A");
				Main.players.set("Players." + playerNameRepresentation + ".Current_Invitation", "N/A");
			}
		}
		s.sendMessage(ChatColor.DARK_GREEN + "You disbanded your guild called " + ChatColor.DARK_GRAY + sendersActualGuildName + ChatColor.DARK_GREEN + "!");
		Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "The guild [" + ChatColor.DARK_GREEN + sendersActualGuildName + ChatColor.DARK_GRAY + "] has disbanded.");
		Main.saveYamls();
		return true;
	}
}
