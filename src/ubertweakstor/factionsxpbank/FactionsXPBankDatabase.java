/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ubertweakstor.factionsxpbank;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author John
 */
public class FactionsXPBankDatabase {
    
    HashMap<String, Integer> database = new HashMap<>();
    FactionsXPBank parent;
    
    public FactionsXPBankDatabase(FactionsXPBank instance){
        parent = instance;
    }
    
    public void setEntry(String entry, Integer data){
        database.put(entry, data);
    }
    
    public Integer getEntry(String entry){
        return (Integer) database.get(entry);
    }
    
    public void purgeDatabase(){
        database.clear();
    }
    
    public boolean containsEntry(String entry){
        return database.containsKey(entry);
    }
    
    public void loadDatabase(){
        try{
            
            FileInputStream fis = new FileInputStream(parent.getConfig().getString("DatabasePath"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            try {            
                HashMap<String, Integer> database = (HashMap<String, Integer>) ois.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FactionsXPBankDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            ois.close();                    
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FactionsXPBankDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FactionsXPBankDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveDatabase(){        
        try {            
            FileOutputStream fos;
            fos = new FileOutputStream(parent.getConfig().getString("DatabasePath"));
            ObjectOutputStream oos;
            oos = new ObjectOutputStream(fos);
            oos.writeObject(database);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FactionsXPBankDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FactionsXPBankDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean databaseExists(){
        File f = new File(parent.getConfig().getString("DatabasePath"));
        return f.exists();
    }
    
}
