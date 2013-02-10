package latros.z.guilds.Functions;

import latros.z.guilds.Main;
import latros.z.guilds.Functions.Util.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class RemoveMember {
	
	static String sendersPlayerName;
	static String sendersCurrGuild;
	static int currRosterSize;
	static int updRosterSize;
	static String targetPlayersName;
	static String sendersGuild;
	static String targetPlayersGuild;
	
	public static boolean leaveGuild(String[] args, CommandSender s){
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.leave")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to leave a guild. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!Util.isInGuild(s) == true){
			//Checking if they're already in a guild
			s.sendMessage(ChatColor.RED + "You need to be in a guild to use this command.");
			return false;
		}
		if(args.length > 1){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild leave command! Proper syntax is: \"/guild leave\"");
			return false;
		}
		if(Util.isGuildLeader(s) == true){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "You're the guild leader, you can't leave the guild! Promote a new leader first! (/guild setnewleader <player name>)");
			return false;
		}
		sendersPlayerName = s.getName().toLowerCase();
		sendersCurrGuild = Main.players.getString("Players." + sendersPlayerName + ".Current_Guild");
		currRosterSize = Main.guilds.getInt("Guilds." + sendersCurrGuild + ".Roster_Size");
		updRosterSize = currRosterSize - 1;
		Main.players.set("Players." + sendersPlayerName + ".Guild_Leader", false);
		Main.players.set("Players." + sendersPlayerName + ".Current_Guild", "None");
		Main.players.set("Players." + sendersPlayerName + ".Current_Rank", 0);
		Main.players.set("Players." + sendersPlayerName + ".Guild_Contributions", 0);
		Main.players.set("Players." + sendersPlayerName + ".Member_Since", "N/A");
		Main.players.set("Players." + sendersPlayerName + ".Current_Invitation", "N/A");
		Main.guilds.set("Guilds." + sendersCurrGuild + ".Roster_Size", updRosterSize);
		s.sendMessage(ChatColor.DARK_GREEN + "You left the guild " + sendersCurrGuild + ".");
		Main.saveYamls();
		return true;
	}
	
	public static boolean kick(String[] args, CommandSender s){
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.kick")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to kick a player from a guild. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!Util.isInGuild(s) == true){
			//Checking if they're already in a guild
			s.sendMessage(ChatColor.RED + "You need to be in a guild to use this command.");
			return false;
		}
		if(args.length > 2 || args.length == 1){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild kick command! Proper syntax is: \"/guild kick <player name>\"");
			return false;
		}
		if(Util.isGuildLeader(s) == false && Util.isOfficer(s) == false){
			//Checking if the player is the guild leader
			s.sendMessage(ChatColor.RED + "You need to be the guild leader or officer to use that command.");
			return false;
		}
		targetPlayersName = args[1].toLowerCase();
		targetPlayersGuild = Main.players.getString("Players." + targetPlayersName + ".Current_Guild");
		
		sendersPlayerName = s.getName().toLowerCase();
		sendersGuild = Main.players.getString("Players." + sendersPlayerName + ".Current_Guild");
		
		if(!sendersGuild.matches(targetPlayersGuild)){
			//Checking if the player is the guild leader
			s.sendMessage(ChatColor.RED + "That player isn't in the same guild as you, you can't kick them from their guild!");
			return false;
		}
		currRosterSize = Main.guilds.getInt("Guilds." + sendersGuild + ".Roster_Size");
		updRosterSize = currRosterSize - 1;
		Main.players.set("Players." + targetPlayersName + ".Guild_Leader", false);
		Main.players.set("Players." + targetPlayersName + ".Current_Guild", "None");
		Main.players.set("Players." + targetPlayersName + ".Current_Rank", 0);
		Main.players.set("Players." + targetPlayersName + ".Guild_Contributions", 0);
		Main.players.set("Players." + targetPlayersName + ".Member_Since", "N/A");
		Main.players.set("Players." + targetPlayersName + ".Current_Invitation", "N/A");
		Main.guilds.set("Guilds." + sendersGuild + ".Roster_Size", updRosterSize);
		s.sendMessage(ChatColor.DARK_GREEN + "You kicked the user " + targetPlayersName + " from the guild " + sendersGuild + ".");
		Main.saveYamls();
		return true;
	}
}
