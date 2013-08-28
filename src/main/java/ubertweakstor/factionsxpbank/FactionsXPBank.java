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
                //if it's executed at the command line:
                //xpbank balance <faction>
                //Else if it's executed by a player
                //xpbank balance                
                if(isConsole){
                    if(!(args.length==2)){
                        sender.sendMessage(ChatColor.RED+"ERROR: Invalid number "
                                + "of parameters!");
                        return true;
                    } else if(database.containsEntry(args[1])==false){
                        sender.sendMessage(ChatColor.RED+"ERROR: That faction "
                                + "doesn't exist!");
                    }                  
                    
                    sender.sendMessage(ChatColor.GREEN + "That faction's balance "
                            + "is: " + ChatColor.GOLD + database.getEntry(args[1]));
                    
                } else {
                    if (!(args.length==1)){
                        sender.sendMessage(ChatColor.RED+"ERROR: Invalid number "
                                + "of parameters!");                       
                        return true;
                    }
                    
                    
                    Player p = (Player) sender;
                    UPlayer ply = UPlayer.get(p);
                    Faction faction = ply.getFaction();
                    sender.sendMessage(ChatColor.GREEN+"Your faction's balance "
                            + "is: " + ChatColor.GOLD + database.getEntry(faction.getName()));
                    
                }
           
            
            
            
            } else if(command.equalsIgnoreCase("deposit")){
                //Takes lvls from inventory and puts into faction bank
                //xpbank deposit <amount>
                if (isConsole){
                    sender.sendMessage(
                            ChatColor.RED+"ERROR: This command can only be "
                            + "executed by a player.");
                    return true;
                } else {
                    
                    Player ply = (Player) sender;
                    Integer amount = Integer.getInteger(args[1]);
                    
                    if (!(args.length==2)){
                        sender.sendMessage(ChatColor.RED+"ERROR: Invalid number "
                                + "of parameters!");
                    } else if(amount > ply.getLevel()){
                        sender.sendMessage(ChatColor.RED+"ERROR: You don't have "
                                + "that many levels!");
                    } else if(amount < 0){
                        sender.sendMessage(ChatColor.RED+"ERROR: You can't give "
                                + "less than 0 levels!");
                    }
                    
                    UPlayer uply = UPlayer.get(ply);
                    Faction faction = uply.getFaction();
                    database.addToEntry(faction.getName(), amount);
                    ply.setLevel(ply.getLevel()-amount);                    
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
                    
                    Player ply = (Player) sender;
                    Integer amount = Integer.getInteger(args[1]);
                    UPlayer uply = UPlayer.get(ply);
                    Faction faction = uply.getFaction();
                    
                    if (!(args.length==2)){
                        sender.sendMessage(ChatColor.RED+"ERROR: Invalid number "
                                + "of parameters!");
                    } else if(amount > database.getEntry(faction.getName())){
                        sender.sendMessage(ChatColor.RED+"ERROR: The bank doesn't "
                                + "have that many levels!");
                    } else if(amount < 0){
                        sender.sendMessage(ChatColor.RED+"ERROR: You can't take "
                                + "less than 0 levels!");
                    }
                    
                    ply.setLevel(ply.getLevel()+amount);
                    database.subtractFromEntry(faction.getName(), amount);
                    
                }
            
            
            
            
            
            } else if(command.equalsIgnoreCase("pay")){
                //xpbank pay <player> <amount>
                //Takes lvls from bank and gives to player in faction
                
                if (isConsole){
                    sender.sendMessage(
                            ChatColor.RED+"ERROR: This command can only be "
                            + "executed by a player.");
                    return true;
                } else {
                    Player ply = (Player) sender;                    
                    Integer amount = Integer.getInteger(args[2]);
                    UPlayer uply = UPlayer.get(ply);
                    Faction faction = uply.getFaction();
                    
                    if (!(args.length==3)){
                        sender.sendMessage(ChatColor.RED+"ERROR: Invalid number "
                                + "of parameters!");
                    } else if(amount > database.getEntry(faction.getName())){
                        sender.sendMessage(ChatColor.RED+"ERROR: The bank doesn't "
                                + "have that many levels!");
                    } else if(amount < 0){
                        sender.sendMessage(ChatColor.RED+"ERROR: You can't give "
                                + "less than 0 levels!");
                    } else if(getServer().getPlayer(args[1])==null){
                        sender.sendMessage(ChatColor.RED+"ERROR: That player "
                                + "isn't online!");
                    }
                    
                    Player receptor = getServer().getPlayer(args[1]);
                    receptor.setLevel(receptor.getLevel()+amount);
                    database.subtractFromEntry(faction.getName(), amount);
                    
                }
            
            
            
            
            
            } else if(command.equalsIgnoreCase("give")){
                //xpbank give <faction> <amount>
                //Gives faction lvls
                
                database.ensureEntryExists(args[1]);
                database.addToEntry(args[1], Integer.parseInt(args[2]));                                    
            
            
            } else if(command.equalsIgnoreCase("take")){   
                //xpbank take <faction> <amount>
                //Takes lvls from faction
                
                database.ensureEntryExists(args[1]);
                database.subtractFromEntry(args[1], Integer.parseInt(args[2]));
                
                
            
            
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
                
                Player ply = (Player) sender;
                ply.sendMessage(ChatColor.RED+"ERROR: Not implemented yet!");
            
            
            
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
