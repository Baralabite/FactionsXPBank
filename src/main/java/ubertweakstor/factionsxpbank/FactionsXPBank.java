/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ubertweakstor.factionsxpbank;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UPlayer;
import java.io.File;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author John
 */
public class FactionsXPBank extends JavaPlugin{
    
    FactionsXPBankDatabase database;

    @Override
    public void onEnable(){
        database = new FactionsXPBankDatabase(this);        
    }
    
    @Override
    public void onDisable(){
        if (configExists()){
            saveConfig();
        } else {
            this.saveDefaultConfig();
        }
    }
    
    public boolean configExists(){            
        System.out.println(getDataFolder().toString()+"plugin.yml"); //TOOD Remove this after testing
        File f = new File(getDataFolder().toString()+"plugin.yml");
        return f.exists();
    }
    //Figuring out what to do now
    //
    //Set up database storage
    //Set up commands hook
    //Put code into commands
    //Hook into help
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){       
        boolean isConsole = !(sender instanceof Player);
	if(cmd.getName().equalsIgnoreCase("xpbank")){
            //if (args.length < 1){ //If no args exist, then send error
            //    sender.sendMessage(ChatColor.RED+"ERROR: Not enough arguments.");
            //    return true;
            //}                     
            
            String command = args[0];
            
            if (command.equalsIgnoreCase("balance")){ 
                if(isConsole){
                    //xpbank balance <faction>
                    if(!(args.length==2)){
                        sender.sendMessage(ChatColor.RED+"ERROR: Invalid number "
                                + "of parameters!");
                        return true;
                    }                                                       
                } else {
                    
                    Player p = (Player) sender;
                    UPlayer ply = UPlayer.get(p);
                    Faction faction = ply.getFaction();
                    sender.sendMessage(ChatColor.GREEN+"Your faction's name is"+faction.getName());
                    
                }
            } else if(command.equalsIgnoreCase("deposit")){
                //Takes lvls from inventory and puts into faction bank
                if (isConsole){
                    sender.sendMessage(
                            ChatColor.RED+"ERROR: This command can only be "
                            + "executed by a player.");
                    return true;
                } else {
                    
                }
            } else if(command.equalsIgnoreCase("withdraw")){
                //xpbank withdraw <amount>
                //Takes lvls from faction bank
                if (isConsole){
                    sender.sendMessage(
                            ChatColor.RED+"ERROR: This command can only be "
                            + "executed by a player.");
                    return true;
                } else {
                    
                }
            } else if(command.equalsIgnoreCase("pay")){
                //xpbank pay <player> <amount>
                //Takes lvls from bank and gives to player in faction
                
                if (isConsole){
                    sender.sendMessage(
                            ChatColor.RED+"ERROR: This command can only be "
                            + "executed by a player.");
                    return true;
                }
            } else if(command.equalsIgnoreCase("give")){
                //xpbank give <faction> <amount>
                //Gives faction lvls
                
                database.ensureEntryExists(args[1]);
                database.addToEntry(args[2], Integer.parse
                        );
            } else if(command.equalsIgnoreCase("take")){   
                //xpbank take <faction> <amount>
                //Takes lvls from faction
                
            } else if(command.equalsIgnoreCase("version")){ 
                //xpbank version
                //Displays version
                
                sender.sendMessage(ChatColor.GOLD+"FactionsXPBank v"
                        + getDescription().getVersion());
                sender.sendMessage(ChatColor.GRAY+"Author: "+ChatColor.GOLD
                        + "John 'boar401s2' Board");
            } else if(command.equalsIgnoreCase("help")){                
                //xpbank help
                //Not sure yet...
                
            } else {   
                //If unknown command is executed, and if they player is naemr, then
                //rebuke hashly, else respond nicely.
                //I still <3 you naemr :}
                if (isConsole || ((Player) sender).getName().equalsIgnoreCase("naemr")){
                    sender.sendMessage(ChatColor.RED+"ERROR: You stupid idiot, you typed in a nonexistant command!");
                } else {
                    sender.sendMessage(ChatColor.RED+"ERROR: Unknown Command!");
                }
            }
            return true;
	}
	return false; 
    }       
}
