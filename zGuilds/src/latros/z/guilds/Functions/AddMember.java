package latros.z.guilds.Functions;

import java.util.Date;

import latros.z.guilds.Main;
import latros.z.guilds.Functions.Util.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AddMember {
	
	static int currRosterSize;
	static int updRosterSize;
	static int sendersTargetGuildNewMemberStartingRank;
	static String sendersPlayerName;
	static String guildInvitedToInputted;
	static String sendersCurrentGuildInvitation;
	static Date now;
	static String date;

	public static boolean joinGuild(String[] args, CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.join")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to invite anyone to a guild. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(Util.isInGuild(s) == true){
			//Checking if they're already in a guild, dont let them join this one if so
			s.sendMessage(ChatColor.RED + "You're already in a guild, you can't join a new one. Leave your old one first.");
			return false;
		}
		if(args.length > 2 || args.length == 1){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild join command! Proper syntax is: \"/guild join GuildName\"");
			return false;
		}
		if(Main.guilds.getConfigurationSection("Guilds." + args[1].toLowerCase()) == null){
			//Checking if that guild already exists
			s.sendMessage(ChatColor.RED + "That guild doesn't exist. They must have disbanded before you accepted the invite.");
			return false;
		}

		sendersPlayerName = s.getName().toLowerCase();
		guildInvitedToInputted = args[1].toLowerCase();
		sendersCurrentGuildInvitation = Main.players.getString("Players." + sendersPlayerName + ".Current_Invitation");
		
		
		if(guildInvitedToInputted.matches(sendersCurrentGuildInvitation) == false){
			//checking if their current guild invite matches the guild they put in their arg
			s.sendMessage(ChatColor.RED + "You aren't currently invited to a guild called " + guildInvitedToInputted + ". If you are supposed to be, tell the guild leader of that guild to invite you.");
			return false;
		}

		now = new Date();
		date = now.toString();
		currRosterSize = Main.guilds.getInt("Guilds." + guildInvitedToInputted + ".Roster_Size");
		updRosterSize = currRosterSize + 1;
		sendersTargetGuildNewMemberStartingRank = Main.guilds.getInt("Guilds." + guildInvitedToInputted + ".New_Member_Starting_Rank");
		
		Main.players.set("Players." + sendersPlayerName + ".Current_Guild", guildInvitedToInputted);
		Main.players.set("Players." + sendersPlayerName + ".Member_Since", date);
		Main.players.set("Players." + sendersPlayerName + ".Guild_Leader", false);
		Main.players.set("Players." + sendersPlayerName + ".Current_Rank", sendersTargetGuildNewMemberStartingRank);
		Main.players.set("Players." + sendersPlayerName + ".Current_Invitation", "N/A");
		Main.guilds.set("Guilds." + guildInvitedToInputted + ".Roster_Size", updRosterSize);
		Main.saveYamls();
		s.sendMessage(ChatColor.DARK_GREEN + "You joined the guild " + ChatColor.RED + guildInvitedToInputted + ChatColor.DARK_GREEN + "!");
		return true;
	}

}
