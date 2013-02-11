package latros.z.guilds.Functions.Admin;

import java.util.Date;

import latros.z.guilds.Main;
import latros.z.guilds.Functions.Util.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Admin {
	
	static String targetGuild;
	static int targetGuildsNewLevel;
	static String targetPlayersName;
	static String targetPlayersGuild;
	static int currRosterSize;
	static int updRosterSize;
	static String targetPlayersActualGuild;
	static int targetPlayersGuildNewMemberStartingRank;
	static Date now;
	static String nowString;
	
	public static boolean adminHomeTele(String[] args, CommandSender s) {
		return true;
	}	
	
	public static boolean adminHomeSet(String[] args, CommandSender s) {
		return true;
	}	
	
	public static boolean banPlayer(String[] args, CommandSender s) {
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.admin.banplayer")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to ban a player from using guilds. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(args.length != 3){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild ban command! Proper syntax is: \"/guild admin ban <playerName>\"");
			return false;
		}
		
		targetPlayersName = args[2].toLowerCase();
		
		if(Main.players.getConfigurationSection("Players." + targetPlayersName) == null){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "No player by that name exists.");
			return false;
		}
		
		if(!Main.players.getString("Players." + targetPlayersName + ".Current_Guild").matches("None")){
			s.sendMessage(ChatColor.RED + "Your target player is in a guild, remove them from their guild first.");
			return false;
		}
		
		if(Main.players.getBoolean("Players." + targetPlayersName + ".Banned") == true){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "That player is already banned from using Guilds.");
			return false;
		}
		
		Main.players.set("Players." + targetPlayersName + ".Banned", true);
		s.sendMessage(ChatColor.DARK_GREEN + "You banned " + targetPlayersName + " from using Guilds functionality.");
		Main.saveYamls();
		
		return true;
	}		
	
	public static boolean unbanPlayer(String[] args, CommandSender s) {
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.admin.unbanplayer")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to unban a player from using guilds. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(args.length != 3){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild unban command! Proper syntax is: \"/guild admin unban <playerName>\"");
			return false;
		}
		
		targetPlayersName = args[2].toLowerCase();
		
		if(Main.players.getConfigurationSection("Players." + targetPlayersName) == null){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "No player by that name exists.");
			return false;
		}
		
		if(Main.players.getBoolean("Players." + targetPlayersName + ".Banned") == false){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "That player isn't banned from using Guilds.");
			return false;
		}
		
		if(!Main.players.getString("Players." + targetPlayersName + ".Current_Guild").matches("None")){
			s.sendMessage(ChatColor.RED + "Your target player is in a guild, remove them from their guild first.");
			return false;
		}
		
		Main.players.set("Players." + targetPlayersName + ".Banned", false);
		s.sendMessage(ChatColor.DARK_GREEN + "You unbanned " + targetPlayersName + " from using Guilds functionality.");
		Main.saveYamls();
		
		return true;
	}
	
	public static boolean manuallyRemoveMember(String[] args, CommandSender s){
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.admin.removemember")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to remove a player from a guild. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(args.length != 4){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild remove member command! Proper syntax is: \"/guild admin removemember <guildName> <playerName>\"");
			return false;
		}
		targetPlayersName = args[3].toLowerCase();
		targetPlayersGuild = args[2].toLowerCase();
		
		if(Main.guilds.getConfigurationSection("Guilds." + targetPlayersGuild) == null){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "No guild by that name exists.");
			return false;
		}
		
		if(Main.players.getConfigurationSection("Players." + targetPlayersName) == null){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "No player by that name exists.");
			return false;
		}
		
		if(Util.isGuildLeader(targetPlayersName) == true){
			s.sendMessage(ChatColor.RED + "That player is the guild leader, you can't manually remove them from the guild. You have to set a new guild leader first.");
			return false;
		}
		
		targetPlayersActualGuild = Main.players.getString("Players." + targetPlayersName + ".Current_Guild");
		
		if(!Main.players.getString("Players." + targetPlayersName + ".Current_Guild").matches(targetPlayersGuild)){
			s.sendMessage(ChatColor.RED + "That player isn't in the guild you specified. They're in the guild " + targetPlayersActualGuild + ".");
			return false;
		}
		
		currRosterSize = Main.guilds.getInt("Guilds." + targetPlayersGuild + ".Roster_Size");
		updRosterSize = currRosterSize - 1;
		Main.players.set("Players." + targetPlayersName + ".Guild_Leader", false);
		Main.players.set("Players." + targetPlayersName + ".Current_Guild", "None");
		Main.players.set("Players." + targetPlayersName + ".Current_Rank", 0);
		Main.players.set("Players." + targetPlayersName + ".Guild_Contributions", 0);
		Main.players.set("Players." + targetPlayersName + ".Member_Since", "N/A");
		Main.players.set("Players." + targetPlayersName + ".Current_Invitation", "N/A");
		Main.guilds.set("Guilds." + targetPlayersGuild + ".Roster_Size", updRosterSize);
		s.sendMessage(ChatColor.DARK_GREEN + "You removed the user " + targetPlayersName + " from the guild " + targetPlayersGuild + ".");
		Main.saveYamls();
		return true;
	}
	
	public static boolean manuallySetNewLeader(String[] args, CommandSender s){
		return true;
	}

	public static boolean manuallyAddMember(String[] args, CommandSender s) {
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.admin.addmember")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to add a player to a guild. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(args.length != 4){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild add member command! Proper syntax is: \"/guild admin addmember <guildName> <playerName>\"");
			return false;
		}
		
		targetPlayersName = args[3].toLowerCase();
		targetPlayersGuild = args[2].toLowerCase();
		
		if(Main.guilds.getConfigurationSection("Guilds." + targetPlayersGuild) == null){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "No guild by that name exists.");
			return false;
		}
		
		if(Main.players.getConfigurationSection("Players." + targetPlayersName) == null){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "No player by that name exists.");
			return false;
		}
		
		if(!Main.players.getString("Players." + targetPlayersName + ".Current_Guild").matches("None")){
			s.sendMessage(ChatColor.RED + "Your target player is already in a guild, cannot add them to a new one.");
			return false;
		}
		
		currRosterSize = Main.guilds.getInt("Guilds." + targetPlayersGuild + ".Roster_Size");
		updRosterSize = currRosterSize + 1;
		targetPlayersGuildNewMemberStartingRank = Main.guilds.getInt("Guilds." + targetPlayersGuild + ".New_Member_Starting_Rank");
		now = new Date();
		nowString = now.toString();
		
		Main.players.set("Players." + targetPlayersName + ".Guild_Leader", false);
		Main.players.set("Players." + targetPlayersName + ".Current_Guild", targetPlayersGuild);
		Main.players.set("Players." + targetPlayersName + ".Current_Rank", targetPlayersGuildNewMemberStartingRank);
		Main.players.set("Players." + targetPlayersName + ".Guild_Contributions", 0);
		Main.players.set("Players." + targetPlayersName + ".Member_Since", nowString);
		Main.players.set("Players." + targetPlayersName + ".Current_Invitation", "N/A");
		Main.guilds.set("Guilds." + targetPlayersGuild + ".Roster_Size", updRosterSize);
		s.sendMessage(ChatColor.DARK_GREEN + "You added the user " + targetPlayersName + " to the guild " + targetPlayersGuild + ".");
		Main.saveYamls();
		return true;
	}

	public static boolean manuallySetGuildLevel(String[] args, CommandSender s) {
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.admin.setlevel")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to set a guilds level. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(args.length > 4){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild set level command! Proper syntax is: \"/guild setlevel <Guild name> <# (1-25)>\"");
			return false;
		}
		targetGuild = args[2].toLowerCase();
		if(Main.guilds.getConfigurationSection("Guilds." + targetGuild) == null){
			//Checking if that guild already exists
			s.sendMessage(ChatColor.RED + "That guild doesn't exist. Unable to change level.");
			return false;
		}
		if(!args[3].matches("^[0-9]+$")){
			//Checking if the type is valid
			s.sendMessage(ChatColor.RED + "The new level can only contain numbers.");
			return false;
		}
		if(args[3].startsWith("0")){
			s.sendMessage(ChatColor.RED + "The level you're attempting to set can't begin with '0'.");
			return false;
		}
		targetGuildsNewLevel = Integer.parseInt(args[3]);
		if(targetGuildsNewLevel > 25 || targetGuildsNewLevel < 1){
			s.sendMessage(ChatColor.RED + "You can't set a guild higher than level 25, or lower than 1.");
			return false;
		}
		Main.guilds.set("Guilds." + targetGuild + ".Level", targetGuildsNewLevel);
		s.sendMessage(ChatColor.DARK_GREEN + "You set the level of \"" + targetGuild + "\" to " + targetGuildsNewLevel + ".");
		Main.saveYamls();
		return true;
	}
	
	public static boolean getAdminCommandList(String[] args, CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.admin.help")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to get a command list. Talk to your server admin if you believe this is in error.");
			return false;
		}
		
		s.sendMessage(ChatColor.DARK_GREEN + "---- zGuilds Admin Command List ----");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild admin setlevel <guild name> <# (1-25)>");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild admin removemember <guild name> <player name>");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild admin addmember <guild name> <player name>");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild admin banplayer <player name>");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild admin hometele <guild name>");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild admin sethome <guildname>");
		return true;
	}
}
