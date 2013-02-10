package latros.z.guilds.Functions;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import latros.z.guilds.Main;
import latros.z.guilds.Functions.Util.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MemberManagement {
	
	static List<String> onlineListInStringFormat;
	static Player[] onlineListInPlayerFormat;
	static Date now;
	static String updatedPromoteesRank;
	static String promoteesOldRank;
	static String updatedDemoteesRank;
	static String demoteesOldRank;
	static String date;
	static String name;
	static String promoteesName;
	static String demoteesName;
	static String sendersPlayerName;
	static String sendersGuild;
	static String inviteesCurrentGuildInvite;
	static String targetPlayersName;
	static String targetPlayersGuild;
	static int currentRosterSize;
	static int updatedRosterSize;
	static int promotersCurrentRank;
	static int demotersCurrentRank;
	static int promoteesCurrentRank;
	static int demoteesCurrentRank;
	static int defaultGuildRank;
	
	public static boolean promote(String[] args, CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.promote")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to promote players. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!Util.isInGuild(s) == true){
			//Checking if they're already in a guild
			s.sendMessage(ChatColor.RED + "You need to be in a guild to use this command.");
			return false;
		}
		if(args.length > 2 || args.length == 1){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild promote command! Proper syntax is: \"/guild promote PlayerName\"");
			return false;
		}
		if(Util.isGuildLeader(s) == false && Util.isOfficer(s) == false){
			//Checking if the player is the guild leader or officer
			s.sendMessage(ChatColor.RED + "You need to be the guild leader or officer to use that command.");
			return false;
		}
		
		sendersPlayerName = s.getName().toLowerCase();
		sendersGuild = Main.players.getString("Players." + sendersPlayerName + ".Current_Guild");
		promoteesName = args[1].toLowerCase();
		onlineListInStringFormat = new ArrayList<String>();
		onlineListInPlayerFormat = Bukkit.getOnlinePlayers();
		promoteesCurrentRank = Main.players.getInt("Players." + promoteesName + ".Current_Rank");
		promotersCurrentRank = Main.players.getInt("Players." + sendersPlayerName + ".Current_Rank");
		
		//More checks!
		for(Player p : onlineListInPlayerFormat){
			name = p.getName().toLowerCase();
			onlineListInStringFormat.add(name);
		}
		if(!onlineListInStringFormat.contains(promoteesName)){
			//Checking if the player is online
			s.sendMessage(ChatColor.RED + "No player by that name is online or they don't exist. Players must be online in order to receive promotion.");
			return false;
		}
		
		if(promoteesCurrentRank == 9){
			//Checking if the player is already at max "promoteable" rank (officer).
			s.sendMessage(ChatColor.RED + "This player is already at the rank below leader in your guild... they cannot be promoted any higher unless you declare them the new guild leader with \"/guild setnewleader PlayerName\".");
			return false;
		}
		
		if(promoteesCurrentRank == 8 && promotersCurrentRank == 9){
			//Checking if the target player is 1 rank below the promoters current rank.
			s.sendMessage(ChatColor.RED + "You are unable to promote the target player to the same rank as yourself within the guild. Ask your guild leader to promote this player.");
			return false;
		}
		promoteesOldRank = Main.guilds.getString("Guilds." + sendersGuild + ".Ranks." + promoteesCurrentRank);
		promoteesCurrentRank = promoteesCurrentRank + 1;
		updatedPromoteesRank = Main.guilds.getString("Guilds." + sendersGuild + ".Ranks." + promoteesCurrentRank);
		Main.players.set("Players." + promoteesName + ".Current_Rank", promoteesCurrentRank);
		for(Player p : onlineListInPlayerFormat){
			if(p.getName().toLowerCase().matches(promoteesName)){
				p.sendMessage(ChatColor.DARK_GREEN + "You were promoted FROM " + promoteesOldRank + ", TO " + updatedPromoteesRank + ", by " + s.getName() + ".");
			}
		}
		s.sendMessage(ChatColor.DARK_GREEN + "You promoted " + promoteesName + " to " + updatedPromoteesRank + " (from " + promoteesOldRank + ").");
		Main.saveYamls();
		return true;
	}
	
	public static boolean demote(String[] args, CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.demote")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to demote players. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!Util.isInGuild(s) == true){
			//Checking if they're already in a guild
			s.sendMessage(ChatColor.RED + "You need to be in a guild to use this command.");
			return false;
		}
		if(args.length > 2 || args.length == 1){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild demote command! Proper syntax is: \"/guild demote PlayerName\"");
			return false;
		}
		if(Util.isGuildLeader(s) == false && Util.isOfficer(s) == false){
			//Checking if the player is the guild leader or officer
			s.sendMessage(ChatColor.RED + "You need to be the guild leader or officer to use that command.");
			return false;
		}
		
		sendersPlayerName = s.getName().toLowerCase();
		sendersGuild = Main.players.getString("Players." + sendersPlayerName + ".Current_Guild");
		demoteesName = args[1].toLowerCase();
		onlineListInStringFormat = new ArrayList<String>();
		onlineListInPlayerFormat = Bukkit.getOnlinePlayers();
		demoteesCurrentRank = Main.players.getInt("Players." + demoteesName + ".Current_Rank");
		demotersCurrentRank = Main.players.getInt("Players." + sendersPlayerName + ".Current_Rank");
		
		//More checks!
		for(Player p : onlineListInPlayerFormat){
			name = p.getName().toLowerCase();
			onlineListInStringFormat.add(name);
		}
		if(!onlineListInStringFormat.contains(demoteesName)){
			//Checking if the player is online
			s.sendMessage(ChatColor.RED + "No player by that name is online or they don't exist. Players must be online in order to be demoted.");
			return false;
		}
		
		if(demoteesCurrentRank == 1){
			//Checking if the player is already at lowest rank.
			s.sendMessage(ChatColor.RED + "This player is already at the lowest possible rank.");
			return false;
		}
		
		if(demoteesCurrentRank == 9 && demotersCurrentRank == 9){
			//Checking if the target player is 1 rank below the promoters current rank.
			s.sendMessage(ChatColor.RED + "You are unable to demote the target player because they are the same rank as you.");
			return false;
		}
		
		if(demoteesCurrentRank > demotersCurrentRank){
			//Checking if the target player is higher rank than the demoter.
			s.sendMessage(ChatColor.RED + "You are unable to demote someone higher rank thank yourself.");
			return false;
		}
		demoteesOldRank = Main.guilds.getString("Guilds." + sendersGuild + ".Ranks." + demoteesCurrentRank);
		demoteesCurrentRank = demoteesCurrentRank - 1;
		updatedDemoteesRank = Main.guilds.getString("Guilds." + sendersGuild + ".Ranks." + demoteesCurrentRank);
		Main.players.set("Players." + demoteesName + ".Current_Rank", demoteesCurrentRank);
		for(Player p : onlineListInPlayerFormat){
			if(p.getName().toLowerCase().matches(demoteesName)){
				p.sendMessage(ChatColor.DARK_GREEN + "You were demoted FROM " + demoteesOldRank + ", TO " + updatedDemoteesRank + ", by " + s.getName() + ".");
			}
		}
		s.sendMessage(ChatColor.DARK_GREEN + "You demoted " + demoteesName + " to " + updatedDemoteesRank + " (from " + demoteesOldRank + ").");
		Main.saveYamls();
		return true;
		
	}

	public static boolean setNewLeader(String[] args, CommandSender s){
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.setnewleader")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to set a new guild leader. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!Util.isInGuild(s) == true){
			//Checking if they're already in a guild
			s.sendMessage(ChatColor.RED + "You need to be in a guild to use this command.");
			return false;
		}
		if(args.length > 2 || args.length == 1){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild Set New Leader command! Proper syntax is: \"/guild setnewleader <player name>\"");
			return false;
		}
		if(Util.isGuildLeader(s) == false){
			//Checking if the player is the guild leader
			s.sendMessage(ChatColor.RED + "You need to be the guild leader to use that command.");
			return false;
		}
		targetPlayersName = args[1].toLowerCase();
		targetPlayersGuild = Main.players.getString("Players." + targetPlayersName + ".Current_Guild");
		
		sendersPlayerName = s.getName().toLowerCase();
		sendersGuild = Main.players.getString("Players." + sendersPlayerName + ".Current_Guild");
		
		defaultGuildRank = Main.guilds.getInt("Guilds." + sendersGuild + ".New_Member_Starting_Rank");
		
		if(!sendersGuild.matches(targetPlayersGuild)){
			//Checking if the player is the guild leader
			s.sendMessage(ChatColor.RED + "That player isn't in the same guild as you, you can't set them as your guild leader!");
			return false;
		}
		Main.players.set("Players." + sendersPlayerName + ".Guild_Leader", false);
		Main.players.set("Players." + targetPlayersName + ".Guild_Leader", true);
		Main.players.set("Players." + sendersPlayerName + ".Current_Rank", defaultGuildRank);
		Main.players.set("Players." + targetPlayersName + ".Current_Rank", 10);
		s.sendMessage(ChatColor.DARK_GREEN + "You declared the user " + targetPlayersName + " as the new guild leader of " + sendersGuild + ".");
		Main.saveYamls();
		return true;
	}
}
