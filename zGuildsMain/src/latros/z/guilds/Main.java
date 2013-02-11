package latros.z.guilds;

import java.io.File;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class Main extends JavaPlugin {
	// ------------ INTERNAL TODO LIST -------------
	// ModifyGuild.leaveGuild() -> Broadcast the guild leave to the online guild members
	// Recruitment.joinGuild() -> Broadcast the guild join to the online guild members
	// MemberManagement.kick() -> Broadcast to the guild, and send a message to the kicked player, about the kick
	// MemberManagement.setNewLeader() -> Broadcast to the guild, and send a message to the new leader, about the new leader declaration
	// Recruitment.sendInvite() -> check if they're already in A guild first, if so, don't send them the invite
	// Util.doesGuildExist() -> check if the guild exists, use this method in...
	// Misc.getGuildInfo() ^^^^^^^^^^^
	// MaxMember functionality for leaving / joining guilds
	// Charter functionality for leaving / joining guilds
	// Redo most formatting and coloring to be standardized and pretty
	// Make all commands .tolowercase()
	
	// ------------ LAUNCH FEATURES GENERAL -------------
	// create(D), disband(D), invite(D), cancelinvite(D), checkinvite(D), join(D), leave(D), 
	// promote(D), demote(D), kick(D), setnewleader(D), info(D),
	// list(D), viewroster(D), settype(D), setmaxmembers(D), help(D),
	// renamerank(D), setdefaultrank(D), ranklist(D), playerinfo(D),
	// TOTAL: 22 / 22
	
	// ------------ LAUNCH FEATURES LEVEL UNLOCKS -------------
	// sethome(D), hometele(D), compass, help (D)
	// TOTAL: 3/4
	
	// ------------ LAUNCH FEATURES ADMIN -------------
	// setlevel(D), removemember(D), addmember(D), banplayer, hometeleother, sethomeother, help(D)
	// TOTAL: 3/7
	
	static String name, displayname, version, description;
	static Main plugin;
	static File configFile;
    static File playersFile;
    static File guildsFile;
    static Commands commands;
    static int len;
    static byte[] buf;
    public static FileConfiguration config;
    public static FileConfiguration players;
    public static FileConfiguration guilds;
    
	public void onEnable(){
	    
		plugin = this;
		name = getDescription().getName();
		displayname = getDescription().getFullName();
		version = getDescription().getVersion();
		description = getDescription().getDescription();
		
		configFile = new File(getDataFolder(), "Config.yml");
		playersFile = new File(getDataFolder(), "Players.yml");
		guildsFile = new File(getDataFolder(), "Guilds.yml");
		
	    try {
	        firstRun();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		config = new YamlConfiguration();
		players = new YamlConfiguration();
		guilds = new YamlConfiguration();
	    loadYamls();
	    
		commands = new Commands();
		getCommand("guild").setExecutor(commands);
		
		getServer().getPluginManager().registerEvents(new latros.z.guilds.Listeners.ChatListener(),
				this);
		getServer().getPluginManager().registerEvents(new latros.z.guilds.Listeners.LoginListener(),
				this);
		
		getLogger().info("[###### zGuilds Enabled Successfully ######]");
	}
	
	public void onDisable(){
		saveYamls();
		getLogger().info("[###### zGuilds Disabled Successfully ######]");
	}
	
	public static void saveYamls(){
		try{
			config.save(configFile);
			players.save(playersFile);
			guilds.save(guildsFile);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void loadYamls(){
		try{
			config.load(configFile);
			players.load(playersFile);
			guilds.load(guildsFile);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void firstRun() throws Exception{
	    if(!configFile.exists()){
	        configFile.getParentFile().mkdirs();
	        copy(getResource("Config.yml"), configFile);
	    }
	    if(!playersFile.exists()){
	        playersFile.getParentFile().mkdirs();
	        copy(getResource("Players.yml"), playersFile);
	    }
	    if(!guildsFile.exists()){
	        guildsFile.getParentFile().mkdirs();
	        copy(getResource("Guilds.yml"), guildsFile);
	    }
	}
	
	private void copy(InputStream in, File file) {
	    try {
	        OutputStream out = new FileOutputStream(file);
	        buf = new byte[1024];
	        while((len=in.read(buf))>0){
	            out.write(buf,0,len);
	        }
	        out.close();
	        in.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}