package latros.z.guilds.Listeners;

import latros.z.guilds.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginListener implements Listener{
	
	static String playerName;
	static int xprequired;
	static int i;
	static String iAsString;
	
	@EventHandler
	public boolean onPlayerLogin(PlayerJoinEvent e){
		playerName = e.getPlayer().getName().toLowerCase();
		if(Main.players.getConfigurationSection("Players." + playerName) == null){
			Main.players.set("Players." + playerName + ".Guild_Leader", false);
			Main.players.set("Players." + playerName + ".Current_Guild", "None");
			Main.players.set("Players." + playerName + ".Current_Rank", 0);
			Main.players.set("Players." + playerName + ".Guild_Contributions", 0);
			Main.players.set("Players." + playerName + ".Member_Since", "N/A");
			Main.players.set("Players." + playerName + ".Current_Invitation", "N/A");
			Main.players.set("Players." + playerName + ".Banned", false);
			Main.saveYamls();
		}
		if(Main.config.getConfigurationSection("Guild_Creation_Defaults") == null){
			//Guild Generic Configuration
			Main.config.set("General.Guild_Charter_Size", 3);
			
			//Guild XP Required Per Level Configuration
			for(i = 2; i < 26; ++i){
				xprequired = xprequired + 100;
				iAsString = String.valueOf(i); 
				Main.config.set("Levels_XP_Required." + iAsString, xprequired);
			}
			
			//Guild Level Unlocks Configuration
			Main.config.set("Level_Unlocks.HomeTele.Enabled", true);
			Main.config.set("Level_Unlocks.HomeTele.Cooldown_(Seconds)", 0.5);
			Main.config.set("Level_Unlocks.HomeTele.Level_Unlocked", 2);
			Main.config.set("Level_Unlocks.SetHome.Enabled", true);
			Main.config.set("Level_Unlocks.SetHome.Cooldown_(Minutes)", 0.5);
			Main.config.set("Level_Unlocks.SetHome.Level_Unlocked", 2);
			
			//Guild Creation Defaults Configuration
			Main.config.set("Guild_Creation_Defaults.Created", false);
			Main.config.set("Guild_Creation_Defaults.Charter_Size", 1);
			Main.config.set("Guild_Creation_Defaults.Level", 1);
			Main.config.set("Guild_Creation_Defaults.Type", "Undefined");
			Main.config.set("Guild_Creation_Defaults.Max_Members", 25);
			Main.config.set("Guild_Creation_Defaults.New_Member_Starting_Rank", 1);
			Main.config.set("Guild_Creation_Defaults.Home_Location.X_Coordinate", 0);
			Main.config.set("Guild_Creation_Defaults.Home_Location.Y_Coordinate", 0);
			Main.config.set("Guild_Creation_Defaults.Home_Location.Z_Coordinate", 0);
			Main.config.set("Guild_Creation_Defaults.Home_Location.World", "World");
			Main.config.set("Guild_Creation_Defaults.Ranks.1", "Cupcake");
			Main.config.set("Guild_Creation_Defaults.Ranks.2", "Recruit");
			Main.config.set("Guild_Creation_Defaults.Ranks.3", "Recruit+");
			Main.config.set("Guild_Creation_Defaults.Ranks.4", "Member");
			Main.config.set("Guild_Creation_Defaults.Ranks.5", "Member+");
			Main.config.set("Guild_Creation_Defaults.Ranks.6", "Member++");
			Main.config.set("Guild_Creation_Defaults.Ranks.7", "Veteran");
			Main.config.set("Guild_Creation_Defaults.Ranks.8", "Veteran+");
			Main.config.set("Guild_Creation_Defaults.Ranks.9", "Guild Officer");
			Main.config.set("Guild_Creation_Defaults.Ranks.10", "Guild Leader");
			
			//Guild "currently not implemented" Configuration
			Main.config.set("CURRENTLY_NOT_IMPLEMENTED.War_Enabled", false);
			Main.config.set("CURRENTLY_NOT_IMPLEMENTED.Levels_Enabled", true);
			Main.config.set("CURRENTLY_NOT_IMPLEMENTED.Level_Unlocks.BoatSpawn.Enabled", false);
			Main.config.set("CURRENTLY_NOT_IMPLEMENTED.Level_Unlocks.BoatSpawn.Cooldown_(Hours)", 24);
			Main.config.set("CURRENTLY_NOT_IMPLEMENTED.Level_Unlocks.BoatSpawn.Level_Unlocked", -1);
			Main.config.set("CURRENTLY_NOT_IMPLEMENTED.Level_Unlocks.GuildHallCreation.Enabled", false);
			Main.config.set("CURRENTLY_NOT_IMPLEMENTED.Level_Unlocks.GuildHallCreation.Level_Unlocked", -1);
			Main.config.set("CURRENTLY_NOT_IMPLEMENTED.Level_Unlocks.PoliticalSystemUsage.Enabled", false);
			Main.config.set("CURRENTLY_NOT_IMPLEMENTED.Level_Unlocks.PoliticalSystemUsage.Level_Unlocked", -1);
			Main.config.set("CURRENTLY_NOT_IMPLEMENTED.Level_Unlocks.GuardDog.Enabled", false);
			Main.config.set("CURRENTLY_NOT_IMPLEMENTED.Level_Unlocks.GuardDog.Cooldown_(Hours)", 24);
			Main.config.set("CURRENTLY_NOT_IMPLEMENTED.Level_Unlocks.GuardDog.Level_Unlocked", -1);
			Main.config.set("CURRENTLY_NOT_IMPLEMENTED.Level_Unlocks.GuardCat.Enabled", false);
			Main.config.set("CURRENTLY_NOT_IMPLEMENTED.Level_Unlocks.GuardCat.Cooldown_(Hours)", 24);
			Main.config.set("CURRENTLY_NOT_IMPLEMENTED.Level_Unlocks.GuardCat.Level_Unlocked", -1);
			Main.saveYamls();
			return true;
		}
		return true;
	}
}
