package latros.z.guilds.Functions.LevelUnlocks;

import latros.z.guilds.Main;
import latros.z.guilds.Functions.Util.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelUnlocks {	
	
	static String sendersName;
	static String sendersGuild;
	static Player senderAsPlayer;
	static Location sendersLocation;
	static int sendersX;
	static int sendersY;
	static int sendersZ;
	static int currentGuildsLevel;
	static int requiredLevel;
	static String sendersWorldAsString;
	static World guildsHomeLocationWorld;
	static double guildsHomeLocationX;
	static double guildsHomeLocationY;
	static double guildsHomeLocationZ;
	
	public static boolean setGuildHome(String[] args, CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.powers.sethome")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to set a guild home. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!Util.isInGuild(s) == true){
			//Checking if they're already in a guild
			s.sendMessage(ChatColor.RED + "You need to be in a guild to use this command.");
			return false;
		}
		if(args.length > 2 || args.length == 1){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild set home command! Proper syntax is: \"/guild powers sethome\"");
			return false;
		}
		if(Util.isGuildLeader(s.getName()) == false){
			//Checking if the player is the guild leader or officer
			s.sendMessage(ChatColor.RED + "You need to be the guild leader to use that command.");
			return false;
		}
		if(Main.config.getBoolean("Level_Unlocks.SetHome.Enabled") == false){
			s.sendMessage(ChatColor.RED + "That feature is not enabled on your server. Talk to your server admin if you believe this is an error.");
			return false;
		}
		sendersName = s.getName().toLowerCase();
		sendersGuild = Main.players.getString("Players." + sendersName + ".Current_Guild");
		requiredLevel = Main.config.getInt("Level_Unlocks.SetHome.Level_Unlocked");
		currentGuildsLevel = Main.guilds.getInt("Guilds." + sendersGuild + ".Level");
		if(currentGuildsLevel < requiredLevel){
			s.sendMessage(ChatColor.RED + "Your guild isn't high enough level to access that guild power. Your guild is level " + currentGuildsLevel + ", and that guild power requires level " + requiredLevel + ".");
			return false;
		}
		senderAsPlayer = (Player) s;
		sendersLocation = senderAsPlayer.getLocation();
		sendersX = (int) sendersLocation.getX();
		sendersY = (int) sendersLocation.getY();
		sendersZ = (int) sendersLocation.getZ();
		sendersWorldAsString = sendersLocation.getWorld().getName();
		Main.guilds.set("Guilds." + sendersGuild + ".Home_Location.X_Coordinate", sendersX);
		Main.guilds.set("Guilds." + sendersGuild + ".Home_Location.Y_Coordinate", sendersY);
		Main.guilds.set("Guilds." + sendersGuild + ".Home_Location.Z_Coordinate", sendersZ);
		Main.guilds.set("Guilds." + sendersGuild + ".Home_Location.World", sendersWorldAsString);
		s.sendMessage(ChatColor.DARK_GREEN + "You set your guilds home to: " + String.valueOf(sendersX) + "x," + String.valueOf(sendersY) + "y," + String.valueOf(sendersZ) + "z, on the world: " + sendersWorldAsString + ".");
		Main.saveYamls();
		return true;
	}
	
	public static boolean homeTeleport(String[] args, CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.powers.hometele")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to teleport to a guild home. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!Util.isInGuild(s) == true){
			//Checking if they're already in a guild
			s.sendMessage(ChatColor.RED + "You need to be in a guild to use this command.");
			return false;
		}
		if(args.length > 2 || args.length == 1){
			//Checking if the create command has proper args
			s.sendMessage(ChatColor.RED + "Incorrectly formatted guild home teleport command! Proper syntax is: \"/guild powers hometele\"");
			return false;
		}
		if(Main.config.getBoolean("Level_Unlocks.HomeTele.Enabled") == false){
			s.sendMessage(ChatColor.RED + "That feature is not enabled on your server. Talk to your server admin if you believe this is an error.");
			return false;
		}
		sendersName = s.getName().toLowerCase();
		sendersGuild = Main.players.getString("Players." + sendersName + ".Current_Guild");
		requiredLevel = Main.config.getInt("Level_Unlocks.HomeTele.Level_Unlocked");
		currentGuildsLevel = Main.guilds.getInt("Guilds." + sendersGuild + ".Level");
		if(currentGuildsLevel < requiredLevel){
			s.sendMessage(ChatColor.RED + "Your guild isn't high enough level to access that guild power. Your guild is level " + currentGuildsLevel + ", and that guild power requires level " + requiredLevel + ".");
			return false;
		}
		if(Main.guilds.getInt("Guilds." + sendersGuild + ".Home_Location.X_Coordinate") == 0 && Main.guilds.getInt("Guilds." + sendersGuild + ".Home_Location.Y_Coordinate") == 0 && Main.guilds.getInt("Guilds." + sendersGuild + ".Home_Location.Z_Coordinate") == 0){
			s.sendMessage(ChatColor.RED + "Your guild doesn't have a home location set. Cannot teleport.");
			return false;
		}
		senderAsPlayer = (Player) s;
		guildsHomeLocationX = Main.guilds.getDouble("Guilds." + sendersGuild + ".Home_Location.X_Coordinate");
		guildsHomeLocationY = Main.guilds.getDouble("Guilds." + sendersGuild + ".Home_Location.Y_Coordinate") + 1;
		guildsHomeLocationZ = Main.guilds.getDouble("Guilds." + sendersGuild + ".Home_Location.Z_Coordinate");
		guildsHomeLocationWorld = Bukkit.getWorld(Main.guilds.getString("Guilds." + sendersGuild + ".Home_Location.World"));
		
		Location teleportLocation = senderAsPlayer.getLocation();
		
		teleportLocation.setX(guildsHomeLocationX);
		teleportLocation.setY(guildsHomeLocationY);
		teleportLocation.setZ(guildsHomeLocationZ);
		teleportLocation.setWorld(guildsHomeLocationWorld);
		
		if(teleportLocation.getWorld() == null){
			s.sendMessage(ChatColor.RED + "That world doesn't actually exist, so in order to keep you safe, we didn't teleport you there.");
			return false;
		}
		
		senderAsPlayer.teleport(teleportLocation);
		
		s.sendMessage(ChatColor.DARK_GREEN + "You teleported to your guilds home.");
		Main.saveYamls();
		return true;
	}
	
	public static boolean compassPoint(String[] args, CommandSender s){
		return true;
	}
	
	public static boolean getPowersCommandList(String[] args, CommandSender s){
		
		//Various checks
		if(Util.isBannedFromGuilds(s) == true){
			//Checking if they are banned from the guilds system
			s.sendMessage(ChatColor.RED + "You are currently banned from interacting with the Guilds system. Talk to your server admin if you believe this is in error.");
			return false;
		}
		if(!s.hasPermission("zguilds.powers.help")){
			//Checking if they have the permission node to proceed
			s.sendMessage(ChatColor.RED + "You lack sufficient permissions to get a command list. Talk to your server admin if you believe this is in error.");
			return false;
		}
		
		s.sendMessage(ChatColor.DARK_GREEN + "---- zGuilds Powers Command List ----");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild power sethome");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild power hometele");
		s.sendMessage(ChatColor.DARK_GREEN + "/guild power compass");
		return true;
	}
}
