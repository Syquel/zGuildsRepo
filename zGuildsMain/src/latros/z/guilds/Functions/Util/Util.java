package latros.z.guilds.Functions.Util;

import latros.z.guilds.Main;

import org.bukkit.command.CommandSender;

public class Util {
	
	static String playerName;
	static String guildName;
	static String hasActiveInvite;
	static boolean isGuildLeader;
	static boolean isGuildOfficer;
	static int getCurrentRankNumber;
	static boolean isBannedFromGuilds;
	
	public static boolean isInGuild(CommandSender s){
		playerName = s.getName().toLowerCase();
		guildName = Main.players.getString("Players." + playerName + ".Current_Guild");
		if(guildName.matches("None")){
			return false;
		}
		return true;
	}
	
	public static boolean isGuildLeader(String s){
		playerName = s.toLowerCase();
		isGuildLeader = Main.players.getBoolean("Players." + playerName + ".Guild_Leader");
		if(isGuildLeader == false){
			return false;
		}
		return true;
	}
	
	public static boolean isBannedFromGuilds(CommandSender s){
		playerName = s.getName().toLowerCase();
		isBannedFromGuilds = Main.players.getBoolean("Players." + playerName + ".Banned_from_Guilds");
		if(isBannedFromGuilds == false){
			return false;
		}
		return true;
	}

	public static boolean isOfficer(CommandSender s) {
		playerName = s.getName().toLowerCase();
		getCurrentRankNumber = Main.players.getInt("Players." + playerName + ".Current_Rank");
		if(getCurrentRankNumber != 9){
			return false;
		}
		return true;
	}

	public static boolean hasGuildInvite(CommandSender s) {
		playerName = s.getName().toLowerCase();
		hasActiveInvite = Main.players.getString("Players." + playerName + ".Current_Invitation");
		if(hasActiveInvite.matches("N/A")){
			return false;
		}
		return true;
	}

}
