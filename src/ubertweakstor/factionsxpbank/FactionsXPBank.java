/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ubertweakstor.factionsxpbank;
import java.io.File;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
	if(cmd.getName().equalsIgnoreCase("f")){ // If the player typed /basic then do the following...
		// doSomething
		return true;
	} //If this has happened the function will return true. 
        // If this hasn't happened the a value of false will be returned.
	return false; 
    }       
}
