package latros.z.guilds.Functions;

import java.util.Set;

import latros.z.guilds.Main;
import latros.z.guilds.Var;
import latros.z.guilds.Functions.Util.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class General {
	
	static String currentPlayersGuild;
	static String inputtedGuild;
	static Boolean isFullyCreated;
	static String name;
	static String level;
	static String type;
	static String creationDate;
	static String memberCap;
	static String rosterSize;
	static int rosterSizeInt;
	static Set<String> guildList;
	static Set<String> memberList;
	static Set<String> listOfPlayers;
	static String currentGuild;
	static int counter;
	static Set<String> rankList;
	static String targetPlayersGuild;
	static String targetPlayer;
	static int targetPlayersRankNumber;
	static String targetPlayersRankString;
	static int targetPlayersContributions;
	static String targetPlayersMemberSince;
	
	public static boolean getPlayerInfo(String[] args, CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.playerinfo")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to get player info. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(args.length > 2 || args.length == 1){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild player info command! Proper syntax is: \"/guild playerinfo <player name>\"");
			return false;
		}
		targetPlayer = args[1].toLowerCase();
		if(Main.players.getConfigurationSection("Players." + targetPlayer) == null){
			s.sendMessage(ChatColor.RED + "That player doesn't exist.");
			return false;
		}
		targetPlayersGuild = Main.players.getString("Players." + targetPlayer + ".Current_Guild");
		if(targetPlayersGuild.matches("None")){
			s.sendMessage(ChatColor.RED + "The player " + targetPlayer + " is not currently in a guild.");
			return false;
		}
		targetPlayersRankNumber = Main.players.getInt("Players." + targetPlayer + ".Current_Rank");
		targetPlayersRankString = Main.guilds.getString("Guilds." + targetPlayersGuild + ".Ranks." + targetPlayersRankNumber);
		targetPlayersContributions = Main.players.getInt("Players." + targetPlayer + ".Guild_Contributions");
		targetPlayersMemberSince = Main.players.getString("Players." + targetPlayer + ".Member_Since");
		s.sendMessage(ChatColor.DARK_GREEN + "--- Player Info for: " + targetPlayer + " ---");
		s.sendMessage(ChatColor.DARK_GREEN + "Current Guild: " + targetPlayersGuild);
		s.sendMessage(ChatColor.DARK_GREEN + "Guild Contributions: " + targetPlayersContributions);
		s.sendMessage(ChatColor.DARK_GREEN + "Current Rank: " + targetPlayersRankString);
		s.sendMessage(ChatColor.DARK_GREEN + "Member Since: " + targetPlayersMemberSince);
		return true;
	}

	public static boolean getGuildInfo(String[] args, CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.invite")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to get guild info. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(args.length > 2){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild disband command! Proper syntax is: \"/guild info <guild name>\"");
			return false;
		}
		rosterSizeInt = Main.guilds.getInt("Guilds." + inputtedGuild + ".Roster_Size");
		
		if(!(rosterSizeInt > Var.GuildDefaults_Charter_Size)){
			s.sendMessage(ChatColor.RED + "Guild match found, but this guild is still in the \"charter\" phase (under " + (Var.GuildDefaults_Charter_Size + 1) + " member(s)), so there is no info to display.");
			return true;

		}
		
		inputtedGuild = args[1].toLowerCase();
		name = Main.guilds.getString("Guilds." + inputtedGuild + ".Chat_Prefix");
		level = Main.guilds.getString("Guilds." + inputtedGuild + ".Level");
		type = Main.guilds.getString("Guilds." + inputtedGuild + ".Type");
		creationDate = Main.guilds.getString("Guilds." + inputtedGuild + ".Creation_Date");
		memberCap = Main.guilds.getString("Guilds." + inputtedGuild + ".Max_Members");
		rosterSize = Main.guilds.getString("Guilds." + inputtedGuild + ".Roster_Size");
		s.sendMessage(ChatColor.DARK_GREEN + "--- Displaying Guild Info For " + name + " ---");
		s.sendMessage(ChatColor.DARK_GREEN + "Guild Name: " + name);
		s.sendMessage(ChatColor.DARK_GREEN + "Guild Level: " + level);
		s.sendMessage(ChatColor.DARK_GREEN + "Guild Type: " + type);
		s.sendMessage(ChatColor.DARK_GREEN + "Guild Creation Date: " + creationDate);
		s.sendMessage(ChatColor.DARK_GREEN + "Guild Member Cap: " + memberCap);
		s.sendMessage(ChatColor.DARK_GREEN + "Guild Roster Size: " + rosterSize);
		return true;
	}

	public static boolean getOwnGuildInfo(CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.invite")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to get guild info. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(Util.isInGuild(s) == false){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You need to be in a guild to check your own guild info (/guild info). To check another guilds info, type \"/guild info <guild name>\".");
			return false;
		}
		
		inputtedGuild = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Guild");
		rosterSizeInt = Main.guilds.getInt("Guilds." + inputtedGuild + ".Roster_Size");
		
		if(!(rosterSizeInt > Var.GuildDefaults_Charter_Size)){
			s.sendMessage(ChatColor.RED + "Guild match found, but this guild is still in the \"charter\" phase (under " + (Var.GuildDefaults_Charter_Size + 1) + " member(s)), so there is no info to display.");
			return true;
		}
		
		name = Main.guilds.getString("Guilds." + inputtedGuild + ".Chat_Prefix");
		level = Main.guilds.getString("Guilds." + inputtedGuild + ".Level");
		type = Main.guilds.getString("Guilds." + inputtedGuild + ".Type");
		creationDate = Main.guilds.getString("Guilds." + inputtedGuild + ".Creation_Date");
		memberCap = Main.guilds.getString("Guilds." + inputtedGuild + ".Max_Members");
		rosterSize = Main.guilds.getString("Guilds." + inputtedGuild + ".Roster_Size");
		s.sendMessage(ChatColor.DARK_GREEN + "--- Displaying Guild Info For " + name + " ---");
		s.sendMessage(ChatColor.DARK_GREEN + "Guild Name: " + name);
		s.sendMessage(ChatColor.DARK_GREEN + "Guild Level: " + level);
		s.sendMessage(ChatColor.DARK_GREEN + "Guild Type: " + type);
		s.sendMessage(ChatColor.DARK_GREEN + "Guild Creation Date: " + creationDate);
		s.sendMessage(ChatColor.DARK_GREEN + "Guild Member Cap: " + memberCap);
		s.sendMessage(ChatColor.DARK_GREEN + "Guild Roster Size: " + rosterSize);
		return true;
	}
	
	public static boolean getGuildList(String[] args, CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.guildlist")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to get a guild list. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(args.length > 2){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild list command! Proper syntax is: \"/guild list\"");
			return false;
		}
		StringBuilder sb = new StringBuilder();
		guildList = Main.guilds.getConfigurationSection("Guilds.").getKeys(false);
		s.sendMessage(ChatColor.DARK_GREEN + "---- List of Guilds ----");
		for(String guildNameRepresentation : guildList){
			sb.append(guildNameRepresentation + ", ");
		}
		s.sendMessage(ChatColor.DARK_GREEN + sb.toString());
		return true;
	}
	
	public static boolean getGuildRoster(String[] args, CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.viewroster")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to get a guild list. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(args.length > 2 || args.length == 1){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild roster command! Proper syntax is: \"/guild viewroster <guildname>\"");
			return false;
		}
		if(Main.guilds.getConfigurationSection("Guilds." + args[1]) == null){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "No guild of that name exists, unable to display roster.");
			return false;
		}
		StringBuilder sb = new StringBuilder();
		listOfPlayers = Main.players.getConfigurationSection("Players.").getKeys(false);
		for(String playerNameRepresentation : listOfPlayers){
			currentGuild = Main.players.getString("Players." + playerNameRepresentation + ".Current_Guild");
			if(currentGuild.matches(args[1])){
				sb.append(playerNameRepresentation + ", ");
			}
		}
		s.sendMessage(ChatColor.DARK_GREEN + "---Roster list for guild " + args[1] + "---");
		s.sendMessage(ChatColor.DARK_GREEN + sb.toString());
		return true;
	}
	
	public static boolean getGuildRanks(String[] args, CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.ranklist")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to get a guild rank list. Talk to your server admin if you believe this is in error.");
			return false;
		}
		inputtedGuild = args[1].toLowerCase();
		if(Main.guilds.getConfigurationSection("Guilds." + inputtedGuild) == null){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "No guild of that name exists.");
			return false;
		}
		StringBuilder sb = new StringBuilder();
		rankList = Main.guilds.getConfigurationSection("Guilds."+args[1].toLowerCase()+".Ranks").getKeys(false);
		s.sendMessage(ChatColor.DARK_GREEN + "---- List of Ranks for the guild: " + args[1].toLowerCase() + " ----");
		for(String rankNumberRepresentation : rankList){
			sb.append(Main.guilds.getString("Guilds."+inputtedGuild+".Ranks."+rankNumberRepresentation) + ", ");
		}
		s.sendMessage(ChatColor.DARK_GREEN + sb.toString());
		return true;
	}
	
	public static boolean getOwnGuildRanks(String[] args, CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.ranklist")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to get a guild rank list. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(Util.isInGuild(s) == false){
			s.sendMessage(ChatColor.RED + "You need to be in a guild to see your own guild rank list. To see another guilds rank list, try \"/guild ranklist <guild name>\".");
			return false;
		}
		currentGuild = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Guild");
		StringBuilder sb = new StringBuilder();
		rankList = Main.guilds.getConfigurationSection("Guilds."+currentGuild+".Ranks").getKeys(false);
		s.sendMessage(ChatColor.DARK_GREEN + "---- List of Ranks for the guild: " + currentGuild + " ----");
		for(String rankNumberRepresentation : rankList){
			sb.append(Main.guilds.getString("Guilds."+currentGuild+".Ranks."+rankNumberRepresentation) + ", ");
		}
		s.sendMessage(ChatColor.DARK_GREEN + sb.toString());
		return true;
	}

	public static boolean getGeneralCommandList(String[] args, CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.help")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to get a command list. Talk to your server admin if you believe this is in error.");
			return false;
		}
		
		s.sendMessage(ChatColor.DARK_GREEN + "---- zGuilds General Command List ----");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild create <guild name>");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild disband");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild invite <player name>");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild dismissinvite");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild cancelinvite <player name>");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild checkinvite");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild join <guild name>");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild leave");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild promote <player name>");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild demote <player name>");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild kick <player name>");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild setnewleader <player name>");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild info || /guild info <guild name>");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild list");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild viewroster <guild name>");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild ranklist <guild name> || /guild ranklist");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild playerinfo <player name>");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild settype <type>");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild setmaxmembers <#>");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild renamerank <rank #> <new name>");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild powers hometele");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild powers compass");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild powers sethome");
		return true;
	}
}
