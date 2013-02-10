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

public class Recruitment {
	
	static String inviteeName;
	static String invitersGuild;
	static String inviteesCurrentGuildInvite;
	static List<String> onlineListInStringFormat;
	static Player[] onlineListInPlayerFormat;
	static Date now;
	static String date;
	static String name;
	static String guildInvitedToInputted;
	static String sendersPlayerName;
	static String sendersCurrentGuildInvitation;
	static int sendersTargetGuildNewMemberStartingRank;
	static int currentRosterSize;
	static int updatedRosterSize;
	
	public static boolean sendInvite(String[] args, CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.invite")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to invite anyone to a guild. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!Util.isInGuild(s) == true){
			//Checking if they're already in a guild
			s.sendMessage(ChatColor.RED + "You need to be in a guild to use this command.");
			return false;
		}
		if(args.length > 2 || args.length == 1){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild disband command! Proper syntax is: \"/guild invite PlayerName\"");
			return false;
		}
		if(Util.isGuildLeader(s) == false && Util.isOfficer(s) == false){
			//Checking if the player is the guild leader
			s.sendMessage(ChatColor.RED + "You need to be the guild leader or officer to use that command.");
			return false;
		}
		
		inviteeName = args[1].toLowerCase();
		invitersGuild = Main.players.getString("Players." + s.getName().toLowerCase() + ".Current_Guild");
		inviteesCurrentGuildInvite = Main.players.getString("Players." + inviteeName + ".Current_Invitation");
		
		if(inviteesCurrentGuildInvite.matches(invitersGuild)){
			//Checking if the invitee already has a matching invite
			s.sendMessage(ChatColor.RED + "That player has already been invited to join your guild.");
			return false;
			
		}
		onlineListInStringFormat = new ArrayList<String>();
		onlineListInPlayerFormat = Bukkit.getOnlinePlayers();
		for(Player p : onlineListInPlayerFormat){
			name = p.getName().toLowerCase();
			onlineListInStringFormat.add(name);
		}
		if(onlineListInStringFormat.contains(inviteeName)){
			s.sendMessage(ChatColor.DARK_GREEN + "You invited the player " + args[1] + " to join your guild.");
			Main.players.set("Players." + inviteeName + ".Current_Invitation", invitersGuild);
			for(Player p : onlineListInPlayerFormat){
				name = p.getName().toLowerCase();
				if(name.matches(inviteeName)){
					p.sendMessage(ChatColor.DARK_GREEN + "You were invited to join the guild " + ChatColor.RED + invitersGuild + ChatColor.DARK_GREEN + ".");
					p.sendMessage(ChatColor.DARK_GREEN + "Type \"" + ChatColor.WHITE +"/guild join " + invitersGuild + ChatColor.DARK_GREEN +"\" to join.");
				}
			}
			Main.saveYamls();
			return true;
		}
		s.sendMessage(ChatColor.RED + "No player of that name was found online currently. You can only invite a player who is online.");
		return true;
	}
	
	public static boolean getGuildInvites(String[] args, CommandSender s){
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(args.length > 1){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted check invites command! Proper syntax is: \"/guild checkinvite\"");
			return false;
		}
		if(!s.hasPermission("zguilds.player.checkinvite")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to check your guild invites. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(Util.isInGuild(s) == true){
			//Checking if they're already in a guild
			s.sendMessage(ChatColor.RED + "You're in a guild so you don't have any invitations to check.");
			return false;
		}
		if(Util.hasGuildInvite(s) == false){
			//Checking if they are even invited to a guild at all.
			s.sendMessage(ChatColor.RED + "You are not currently invited to any guild.");
			return false;
		}
		sendersPlayerName = s.getName().toLowerCase();
		sendersCurrentGuildInvitation = Main.players.getString("Players." + sendersPlayerName + ".Current_Invitation");
		s.sendMessage(ChatColor.DARK_GREEN + "You are currently invited to the guild: " + ChatColor.RED + sendersCurrentGuildInvitation + ChatColor.DARK_GREEN + ".");
		return true;
	}
	
	public static boolean dismissInvite(String[] args, CommandSender s){
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(args.length > 1){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted cancel invites command! Proper syntax is: \"/guild cancelinvite\"");
			return false;
		}
		if(!s.hasPermission("zguilds.player.dismissinvite")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to cancel your guild invites. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(Util.isInGuild(s) == true){
			//Checking if they're already in a guild
			s.sendMessage(ChatColor.RED + "You're in a guild so you don't have any invitations to join a new one.");
			return false;
		}
		if(Util.hasGuildInvite(s) == false){
			//Checking if they are even invited to a guild at all.
			s.sendMessage(ChatColor.RED + "You are not currently invited to any guild.");
			return false;
		}
		//Setting their current guild invite to blank
		sendersPlayerName = s.getName().toLowerCase();
		sendersCurrentGuildInvitation = Main.players.getString("Players." + sendersPlayerName + ".Current_Invitation");
		Main.players.set("Players." + sendersPlayerName + ".Current_Invitation", "N/A");
		s.sendMessage(ChatColor.DARK_GREEN + "You cancelled your pending guild invite with the guild " + ChatColor.RED + sendersCurrentGuildInvitation + ChatColor.DARK_GREEN + ".");
		Main.saveYamls();
		return true;
	}
}
