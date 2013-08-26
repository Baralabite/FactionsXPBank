/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ubertweakstor.factionsxpbank;
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
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        Player player;
        boolean isConsole = !(sender instanceof Player);
        if(!isConsole){
            player = (Player) sender;
        }
	if(cmd.getName().equalsIgnoreCase("xpbank")){
            if (args.length < 1){ //If no args exist, then send error
                sender.sendMessage(ChatColor.RED+"ERROR: Not enough arguments.");
                return true;
            }                     
            
            String command = args[0];
            
            if (command.equalsIgnoreCase("balance")){ 
                if(isConsole){
                    
                } else {
                    
                }
            } else if(command.equalsIgnoreCase("deposit")){
                if (isConsole){
                    sender.sendMessage(
                            ChatColor.RED+"ERROR: This command can only be "
                            + "executed by a player.");
                } else {
                    
                }
            } else if(command.equalsIgnoreCase("withdraw")){
                if (isConsole){
                    sender.sendMessage(
                            ChatColor.RED+"ERROR: This command can only be "
                            + "executed by a player.");
                } else {
                    
                }
            } else if(command.equalsIgnoreCase("pay")){
            } else if(command.equalsIgnoreCase("give")){
            } else if(command.equalsIgnoreCase("take")){                           
            } else if(command.equalsIgnoreCase("version")){                
            } else if(command.equalsIgnoreCase("help")){                
            } else {                
            }
            return true;
	}
	return false; 
    }       
}
