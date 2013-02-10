package latros.z.guilds.Functions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import latros.z.guilds.Main;
import latros.z.guilds.Functions.Util.Util;

public class ModifyGuild {
	
	static String sendersPlayerName;
	static int currRosterSize;
	static int updRosterSize;
	static String sendersCurrGuild;
	static String inputtedRankNumber;
	static String newRankName;
	static String oldRankName;
	
	public static boolean checkGuild(String[] args, CommandSender s){
		//TODO: appropriate checks
		String st = Main.players.getString("Players." + args[0].toLowerCase() + ".Current_Guild");
		if(st.matches("None")){
			s.sendMessage(ChatColor.DARK_GREEN + "The player " + ChatColor.RED + args[0] + ChatColor.DARK_GREEN + " is not currently in a guild.");
			return false;
		}
		s.sendMessage(ChatColor.DARK_GREEN + "The player " + args[0] + " is currently in the guild \"" + ChatColor.RED + st + ChatColor.DARK_GREEN + "\".");
		return true;
	}

	public static boolean checkGuildInfo(String[] args, CommandSender s){
		String inputtedGuild = args[0].toLowerCase();
		Boolean fullyCreated = Main.guilds.getBoolean("Guilds." + inputtedGuild + ".Created");
		if(fullyCreated == false){
			String name = Main.guilds.getString("Guilds." + inputtedGuild + ".Chat_Prefix");
			String level = Main.guilds.getString("Guilds." + inputtedGuild + ".Level");
			String type = Main.guilds.getString("Guilds." + inputtedGuild + ".Type");
			String creationDate = Main.guilds.getString("Guilds." + inputtedGuild + ".Creation_Date");
			String memberCap = Main.guilds.getString("Guilds." + inputtedGuild + ".Max_Members");
			String rosterSize = Main.guilds.getString("Guilds." + inputtedGuild + ".Roster_Size");
			s.sendMessage(ChatColor.DARK_GREEN + "--- Found Guild Match ---");
			s.sendMessage(ChatColor.DARK_GREEN + "Guild Name: " + name);
			s.sendMessage(ChatColor.DARK_GREEN + "Guild Level: " + level);
			s.sendMessage(ChatColor.DARK_GREEN + "Guild Type: " + type);
			s.sendMessage(ChatColor.DARK_GREEN + "Guild Creation Date: " + creationDate);
			s.sendMessage(ChatColor.DARK_GREEN + "Guild Member Cap: " + memberCap);
			s.sendMessage(ChatColor.DARK_GREEN + "Guild Roster Size: " + rosterSize);
			return true;
		}
		s.sendMessage(ChatColor.RED + "Guild match found, but this guild is still in the \"charter\" phase (under 3 members), so there is no info to display.");
		return true;
	}
	
	public static boolean setGuildType(String[] args, CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.settype")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to set a guilds type. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!Util.isInGuild(s) == true){
			//Checking if they're already in a guild
			s.sendMessage(ChatColor.RED + "You need to be in a guild to use this command.");
			return false;
		}
		if(args.length > 2 || args.length == 1){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild set type command! Proper syntax is: \"/guild settype <Type>\"");
			return false;
		}
		if(Util.isGuildLeader(s) == false){
			//Checking if the player is the guild leader or officer
			s.sendMessage(ChatColor.RED + "You need to be the guild leader to use that command.");
			return false;
		}
		if(args[1].length() > 10 || args[1].length() < 3){
			//Checking if the type is valid
			s.sendMessage(ChatColor.RED + "Your guild type can only be between 3 and 10 characters.");
			return false;
		}
		sendersCurrGuild = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Guild");
		Main.guilds.set("Guilds."+sendersCurrGuild+".Type", args[1]);
		s.sendMessage(ChatColor.DARK_GREEN + "You set your guilds type to \"" + args[1] + "\".");
		return true;
	}
	
	public static boolean setMaxMembers(String[] args, CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.setmaxmembers")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to set a guilds max member count. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!Util.isInGuild(s) == true){
			//Checking if they're already in a guild
			s.sendMessage(ChatColor.RED + "You need to be in a guild to use this command.");
			return false;
		}
		if(args.length > 2 || args.length == 1){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild set max members command! Proper syntax is: \"/guild setmaxmembers <#>\"");
			return false;
		}
		if(Util.isGuildLeader(s) == false){
			//Checking if the player is the guild leader or officer
			s.sendMessage(ChatColor.RED + "You need to be the guild leader to use that command.");
			return false;
		}
		if(!args[1].matches("^[0-9]+$")){
			//Checking if the type is valid
			s.sendMessage(ChatColor.RED + "Your guild max members can only contain numbers.");
			return false;
		}
		if(Integer.parseInt(args[1]) > 250 || Integer.parseInt(args[1]) < 3){
			s.sendMessage(ChatColor.RED + "The guild max members can't be lower than 3 or greater than 250.");
			return false;
		}
		if(args[1].startsWith("0")){
			s.sendMessage(ChatColor.RED + "The guild max members can't start with '0'.");
			return false;
		}
		sendersCurrGuild = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Guild");
		Main.guilds.set("Guilds."+sendersCurrGuild+".Max_Members", args[1]);
		s.sendMessage(ChatColor.DARK_GREEN + "You set your guilds maxmembers to \"" + args[1] + "\".");
		return true;
	}
	
	public static boolean setRankName(String[] args, CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.renamerank")){
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to set a rank name. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!Util.isInGuild(s) == true){
			s.sendMessage(ChatColor.RED + "You need to be in a guild to use this command.");
			return false;
		}
		if(args.length > 3 || args.length == 1){
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild rename rank command! Proper syntax is: \"/guild renamerank <rank#> <newname>\"");
			return false;
		}
		if(Util.isGuildLeader(s) == false){
			s.sendMessage(ChatColor.RED + "You need to be the guild leader to use that command.");
			return false;
		}
		if(!args[1].matches("^[0-9]+$")){
			s.sendMessage(ChatColor.RED + "The rank number you're attempting to change can only be  \"1-10\".");
			return false;
		}
		if(Integer.parseInt(args[1]) > 10 || Integer.parseInt(args[1]) < 1){
			s.sendMessage(ChatColor.RED + "The rank number you're attempting to change can only be  \"1-10\".");
			return false;
		}
		if(args[1].startsWith("0")){
			s.sendMessage(ChatColor.RED + "The guild rank you're trying to rename can't start with '0'.");
			return false;
		}
		if(args[2].length() > 12 || args[2].length() < 3){
			s.sendMessage(ChatColor.RED + "The new guild rank name can't be shorter than 3 characters, or longer than 12.");
			return false;
		}
		newRankName = args[2];
		inputtedRankNumber = args[1];
		sendersPlayerName = s.getName().toLowerCase();
		sendersCurrGuild = Main.players.getString("Players." + sendersPlayerName + ".Current_Guild");
		oldRankName = Main.guilds.getString("Guilds." + sendersCurrGuild + ".Ranks." + inputtedRankNumber);
		Main.guilds.set("Guilds." + sendersCurrGuild + ".Ranks." + inputtedRankNumber, newRankName);
		Main.saveYamls();
		s.sendMessage(ChatColor.DARK_GREEN + "You renamed the rank " + oldRankName + " to " + newRankName + ".");
		return true;
	}
	
	public static boolean setLowestRank(String[] args, CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.setlowestrank")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to set a guilds max member count. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!Util.isInGuild(s) == true){
			//Checking if they're already in a guild
			s.sendMessage(ChatColor.RED + "You need to be in a guild to use this command.");
			return false;
		}
		if(args.length > 2 || args.length == 1){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild set lowest rank command! Proper syntax is: \"/guild setmaxmembers <#>\"");
			return false;
		}
		if(Util.isGuildLeader(s) == false){
			//Checking if the player is the guild leader or officer
			s.sendMessage(ChatColor.RED + "You need to be the guild leader to use that command.");
			return false;
		}
		if(!args[1].matches("^[0-9]+$")){
			//Checking if the type is valid
			s.sendMessage(ChatColor.RED + "Your guild default rank can only be numbers (1-8).");
			return false;
		}
		if(Integer.parseInt(args[1]) > 8 || Integer.parseInt(args[1]) < 1){
			s.sendMessage(ChatColor.RED + "The guild default rank can't be lower than 1 (lowest rank) or greater than 8 (anything above 8 is officer/leader, which can't be the lowest rank).");
			return false;
		}
		if(args[1].startsWith("0")){
			s.sendMessage(ChatColor.RED + "The guild default rank can't start with '0'.");
			return false;
		}
		sendersCurrGuild = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Guild");
		Main.guilds.set("Guilds."+sendersCurrGuild+".New_Member_Starting_Rank", Integer.parseInt(args[1]));
		s.sendMessage(ChatColor.DARK_GREEN + "You set your guilds starting rank to \"" + args[1] + "\".");
		Main.saveYamls();
		return true;
	}
}
