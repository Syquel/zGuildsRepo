package latros.z.guilds;

import latros.z.guilds.Functions.AddGuild;
import latros.z.guilds.Functions.AddMember;
import latros.z.guilds.Functions.Recruitment;
import latros.z.guilds.Functions.MemberManagement;
import latros.z.guilds.Functions.General;
import latros.z.guilds.Functions.ModifyGuild;
import latros.z.guilds.Functions.RemoveGuild;
import latros.z.guilds.Functions.RemoveMember;
import latros.z.guilds.Functions.Admin.Admin;
import latros.z.guilds.Functions.LevelUnlocks.LevelUnlocks;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class Commands implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("guild") && args.length < 5 && args.length != 0) {
			
			if (args[0].toLowerCase().matches("create") && s.hasPermission("zguilds.player.create")){
				AddGuild.create(args, s);
				return true;
				//07/02/2013 Initial pass complete
			}
			
			if (args[0].toLowerCase().matches("disband")){
				RemoveGuild.disband(args, s);
				return true;
				//07/02/2013 Initial pass complete
			}
			
			if (args[0].toLowerCase().matches("invite")){
				Recruitment.sendInvite(args, s);
				return true;
				//07/02/2013 Initial pass complete
			}
			
			if (args[0].toLowerCase().matches("join")){
				AddMember.joinGuild(args, s);
				return true;
				//08/02/2013 Intial pass complete
			}
			
			if (args[0].toLowerCase().matches("leave")){
				RemoveMember.leaveGuild(args, s);
				return true;
				//08/02/2013 Initial pass complete
			}
			
			if (args[0].toLowerCase().matches("dismissinvites") || args[0].matches("dismissinvite")){
				Recruitment.dismissInvite(args, s);
				return true;
				//08/02/2013 Initial pass complete
			}
			
			if (args[0].toLowerCase().matches("dismissinvite") || args[0].matches("dismissinvites")){
				Recruitment.dismissInvite(args, s);
				return true;
				//08/02/2013 Initial pass complete
			}
			
			if (args[0].toLowerCase().matches("checkinvites") || args[0].matches("checkinvite")){
				Recruitment.getGuildInvites(args, s);
				return true;
				//08/02/2013 Initial pass complete
			}
			
			if (args[0].toLowerCase().matches("promote") || args[0].matches("increaserank")){
				MemberManagement.promote(args, s);
				return true;
				//08/02/2013 Initial pass complete
			}
			
			if (args[0].toLowerCase().matches("demote") || args[0].matches("decreaserank")){
				MemberManagement.demote(args, s);
				return true;
				//08/02/2013 Initial pass complete
			}
			
			if (args[0].toLowerCase().matches("kick") || args[0].matches("boot")){
				RemoveMember.kick(args, s);
				return true;
				//08/02/2013 Initial pass complete
			}
			
			if (args[0].toLowerCase().matches("newleader") || args[0].matches("setnewleader")){
				MemberManagement.setNewLeader(args, s);
				return true;
				//08/02/2013 Initial pass complete
			}
			
			if ((args[0].toLowerCase().matches("info") || args[0].matches("getinfo")) && args.length >= 2){
				General.getGuildInfo(args, s);
				return true;
				//08/02/2013 Initial pass complete
			}
			
			if (args[0].toLowerCase().matches("info") || args[0].matches("getinfo") && args.length == 1){
				General.getOwnGuildInfo(s);
				return true;
				//08/02/2013 Initial pass complete
			}
			
			if (args[0].toLowerCase().matches("list") || args[0].matches("listguilds") || args[0].matches("viewguilds") || args[0].matches("guildlist")){
				General.getGuildList(args, s);
				return true;
				//09/02/2013 Initial pass complete
			}
			
			if (args[0].toLowerCase().matches("viewroster") || args[0].matches("viewmembers") || args[0].matches("memberlist") || args[0].matches("listmembers") || args[0].matches("roster")){
				General.getGuildRoster(args, s);
				return true;
				//09/02/2013 Initial pass complete
			}
			
			if (args[0].toLowerCase().matches("settype") || args[0].matches("changetype") || args[0].matches("type") || args[0].matches("modifytype")){
				ModifyGuild.setGuildType(args, s);
				return true;
				//09/02/2013 Initial pass complete
			}
			
			if (args[0].toLowerCase().matches("setmaxmembers") || args[0].matches("changemaxmembers") || args[0].matches("maxmembers")){
				ModifyGuild.setMaxMembers(args, s);
				return true;
				//09/02/2013 Initial pass complete
			}
			
			if (args[0].toLowerCase().matches("renamerank") || args[0].matches("changerank") || args[0].matches("modifyrank")){
				ModifyGuild.setRankName(args, s);
				return true;
				//09/02/2013 Initial pass complete
			}
			
			if (args[0].toLowerCase().matches("setlowestrank") || args[0].matches("setdefaultrank") || args[0].matches("setbaserank") || args[0].matches("defaultrank")){
				ModifyGuild.setLowestRank(args, s);
				return true;
				//09/02/2013 Initial pass complete
			}

			if ((args[0].toLowerCase().matches("ranklist") || args[0].matches("viewranks") || args[0].matches("ranks")) && args.length > 1){
				General.getGuildRanks(args, s);
				return true;
				//09/02/2013 Initial pass complete
			}
			
			if ((args[0].toLowerCase().matches("ranklist") || args[0].matches("viewranks") || args[0].matches("ranks")) && args.length == 1){
				General.getOwnGuildRanks(args, s);
				return true;
				//09/02/2013 Initial pass complete
			}
			
			if (args[0].toLowerCase().matches("playerinfo") || args[0].matches("getplayerinfo")){
				General.getPlayerInfo(args, s);
				return true;
				//09/02/2013 Initial pass complete
			}
			
			if ((args[0].toLowerCase().matches("help") || args[0].matches("commands") || args[0].matches("viewcommands")) && args.length == 1){
				General.getGeneralCommandList(args, s);
				return true;
				//09/02/2013 Initial pass complete
			}
			
			if (args[0].toLowerCase().matches("powers") || args[0].matches("power")){
				
				if (args[1].toLowerCase().matches("sethome") || args[0].matches("markhome")){
					LevelUnlocks.setGuildHome(args, s);
					return true;
					//09/02/2013 Initial pass complete
				}
				
				if (args[1].toLowerCase().matches("hometele") || args[0].matches("hometeleport") || args[0].matches("gohome") || args[0].matches("recall")){
					LevelUnlocks.homeTeleport(args, s);
					return true;
					//10/02/2013 Initial pass complete
				}
				
				if (args[1].toLowerCase().matches("help")){
					LevelUnlocks.getPowersCommandList(args, s);
					//09/02/2013 Initial pass complete
				}
				
				if (args[1].toLowerCase().matches("compass") || args[0].matches("pointhome") || args[0].matches("direction")){
					LevelUnlocks.compassPoint(args, s);
					return true;
					//10/02/2013 Initial pass complete
				}
				s.sendMessage(ChatColor.RED + "That is not a recognized guild power command. To see a\nlist of proper commands, type: \"/guild powers help\".");
				return true;
			}
			
			if (args[0].toLowerCase().matches("admin")){
				
				if(args[1].toLowerCase().matches("setlevel")){
					Admin.manuallySetGuildLevel(args, s);
					return true;
					//09/02/2013 Initial pass complete
				}
				
				if(args[1].toLowerCase().matches("removemember")){
					Admin.manuallyRemoveMember(args, s);
					return true;
					//10/02/2013 Initial pass complete
				}
				
				if(args[1].toLowerCase().matches("addmember")){
					Admin.manuallyAddMember(args, s);
					return true;
					//10/02/2013 Initial pass complete
				}
				
				if(args[1].toLowerCase().matches("banplayer") || args[1].matches("ban")){
					Admin.banPlayer(args, s);
					return true;
					//10/02/2013 Initial pass complete
				}
				
				if(args[1].toLowerCase().matches("unbanplayer") || args[1].matches("unban")){
					Admin.unbanPlayer(args, s);
					return true;
					//10/02/2013 Initial pass complete
				}
				
				if(args[1].toLowerCase().matches("help") && args.length == 2){
					Admin.getAdminCommandList(args, s);
					return true;
					//09/02/2013 Initial pass complete
				}
				s.sendMessage(ChatColor.RED + "That is not a recognized guild admin command. To see a\nlist of proper commands, type: \"/guild admin help\".");
				return true;
			}
		}
		s.sendMessage(ChatColor.RED + "That is not a recognized guild command. To see a list of\nproper commands, type: \"/guild help\".");
		return true;
	}
}