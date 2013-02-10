package latros.z.guilds.Functions;

import java.util.Date;

import latros.z.guilds.Main;
import latros.z.guilds.Var;
import latros.z.guilds.Functions.Util.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AddGuild {
	
	static String playerName;
	static boolean allChecks;
	static String dateCreated;
	static String guildNameInternal;
	static String guildNameDisplayed;
	static Date now;
	
	public static boolean create(String[] args, CommandSender s){
		
		// Various checks to prevent the guild creation from going through if it shouldn't happen
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.player.create")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to create a guild. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(Util.isInGuild(s) == true){
			//Checking if they're already in a guild
			s.sendMessage(ChatColor.RED + "You're already in a guild! You can't create a new one until you leave your current guild.");
			return false;
		}
		if(args.length > 2 || args.length == 1){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild name! Proper syntax is: \"/guild create GuildNameHere\"");
			return false;
		}
		if(args[1].length() > 10 || args[1].length() < 4){
			//Checking if the chosen guild name is between 4-10 characters
			s.sendMessage(ChatColor.RED + "Your chosen guild name must be between 4 and 10 characters.");
			return false;
		}
		if(!args[1].matches("^[0-9a-zA-Z]+$")){
			//Checking if the chosen guild name matches the appropriate regex (no special characters, only letters and numbers, capitals allowed), REGEX: ^[0-9a-zA-Z]+$
			s.sendMessage(ChatColor.RED + "Your chosen guild name doesn't match the requirements. Only letters (caps and lowercase) and numbers.");
			return false;
		}
		guildNameInternal = args[1].toLowerCase();
		if(Main.guilds.getConfigurationSection("Guilds." + guildNameInternal) != null){
			//Checking if that guild already exists
			s.sendMessage(ChatColor.RED + "Your chosen guild name already exists. Choose a different name.");
			return false;
		}
		
		//Variable initialization if the guild creation passed all of the necessary checks
		playerName = s.getName().toLowerCase();
		now = new Date();
		dateCreated = now.toString();
		guildNameInternal = args[1].toLowerCase();
		guildNameDisplayed = args[1];
		
		//Guild creation (assuming it got through all the above checks)!
		Main.guilds.set("Guilds." + guildNameInternal + ".Created", Var.GuildDefaults_Created); //created: true or false (out of charter phase)
		Main.guilds.set("Guilds." + guildNameInternal + ".Level", Var.GuildDefaults_Level); //level
		Main.guilds.set("Guilds." + guildNameInternal + ".Chat_Prefix", guildNameDisplayed); //chat prefix
		Main.guilds.set("Guilds." + guildNameInternal + ".Type", Var.GuildDefaults_Type); //type
		Main.guilds.set("Guilds." + guildNameInternal + ".Creation_Date", dateCreated); //creation date
		Main.guilds.set("Guilds." + guildNameInternal + ".Max_Members", Var.GuildDefaults_MaxMembers); //max members
		Main.guilds.set("Guilds." + guildNameInternal + ".Roster_Size", 1); //number of members
		Main.guilds.set("Guilds." + guildNameInternal + ".New_Member_Starting_Rank", Var.GuildDefaults_DefaultRank); //new member starting rank
		Main.guilds.set("Guilds." + guildNameInternal + ".Home_Location.X_Coordinate", Var.GuildDefaults_HomeX);
		Main.guilds.set("Guilds." + guildNameInternal + ".Home_Location.Y_Coordinate", Var.GuildDefaults_HomeY);
		Main.guilds.set("Guilds." + guildNameInternal + ".Home_Location.Z_Coordinate", Var.GuildDefaults_HomeZ);
		Main.guilds.set("Guilds." + guildNameInternal + ".Home_Location.World", Var.GuildDefaults_HomeWorld);
		Main.guilds.set("Guilds." + guildNameInternal + ".Ranks.10", Var.GuildDefaults_Rank10); // default rank 10
		Main.guilds.set("Guilds." + guildNameInternal + ".Ranks.9", Var.GuildDefaults_Rank9); // default rank 9 
		Main.guilds.set("Guilds." + guildNameInternal + ".Ranks.8", Var.GuildDefaults_Rank8); // default rank 8
		Main.guilds.set("Guilds." + guildNameInternal + ".Ranks.7", Var.GuildDefaults_Rank7); // default rank 7
		Main.guilds.set("Guilds." + guildNameInternal + ".Ranks.6", Var.GuildDefaults_Rank6); // default rank 6
		Main.guilds.set("Guilds." + guildNameInternal + ".Ranks.5", Var.GuildDefaults_Rank5); // default rank 5
		Main.guilds.set("Guilds." + guildNameInternal + ".Ranks.4", Var.GuildDefaults_Rank4); // default rank 4
		Main.guilds.set("Guilds." + guildNameInternal + ".Ranks.3", Var.GuildDefaults_Rank3); // default rank 3
		Main.guilds.set("Guilds." + guildNameInternal + ".Ranks.2", Var.GuildDefaults_Rank2); // default rank 2
		Main.guilds.set("Guilds." + guildNameInternal + ".Ranks.1", Var.GuildDefaults_Rank1); // default rank 1
		Main.players.set("Players." + playerName + ".Guild_Leader", true);
		Main.players.set("Players." + playerName + ".Current_Guild", guildNameInternal);
		Main.players.set("Players." + playerName + ".Current_Rank", 10);
		Main.players.set("Players." + playerName + ".Guild_Contributions", 0);
		Main.players.set("Players." + playerName + ".Member_Since", dateCreated);
		Main.players.set("Players." + playerName + ".Current_Invitation", "N/A");
		s.sendMessage(ChatColor.DARK_GREEN + "You created a guild called " + ChatColor.DARK_GRAY + guildNameDisplayed + ChatColor.DARK_GREEN + "!");
		Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "The guild [" + ChatColor.DARK_GREEN + guildNameDisplayed + ChatColor.DARK_GRAY + "] has been created.");
		Main.saveYamls();
		return true;
	}
}
