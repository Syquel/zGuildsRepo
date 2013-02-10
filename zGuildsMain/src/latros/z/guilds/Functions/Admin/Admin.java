package latros.z.guilds.Functions.Admin;

import latros.z.guilds.Main;
import latros.z.guilds.Functions.Util.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Admin {
	
	static String targetGuild;
	static int targetGuildsNewLevel;
	
	public static boolean adminHomeTele(String[] args, CommandSender s) {
		return true;
	}	
	
	public static boolean adminHomeSet(String[] args, CommandSender s) {
		return true;
	}	
	
	public static boolean banPlayer(String[] args, CommandSender s) {
		return true;
	}	
	
	public static boolean manuallyRemoveMember(String[] args, CommandSender s){
		return true;
	}
	
	public static boolean manuallySetNewLeader(String[] args, CommandSender s){
		return true;
	}

	public static boolean manuallyAddMember(String[] args, CommandSender s) {
			//TODO: appropriate checks
			Boolean il = Main.players.getBoolean("Players." + args[0].toLowerCase() + ".Guild_Leader");
			if(il == true){
				s.sendMessage(ChatColor.RED + "The player " + args[0] + " is a guild leader. You cannot manually modify the guild of a player who is a guild leader.");
				return true;
			}
			Main.players.set("Players." + args[0] + ".Current_Guild", args[1]);
			Main.players.set("Players." + args[0] + ".Guild_Leader", false);
			s.sendMessage(ChatColor.DARK_GREEN + "You set the guild of " + args[0].toLowerCase() + " to " + ChatColor.RED + args[1] + ChatColor.DARK_GREEN + ".");
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
