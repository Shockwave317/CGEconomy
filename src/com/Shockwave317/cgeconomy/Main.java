package com.Shockwave317.cgeconomy;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    
	public static Main plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	String symbol = "$";
	
    @Override
    public void onEnable() {
    	PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " Has Been Enabled!");
	    getCommand("money").setExecutor(this);
	    PluginManager pm = this.getServer().getPluginManager();
	    pm.registerEvents(this, this); 
	    this.reloadConfig();
    }
 
    @Override
    public void onDisable() {
    	PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has Been Disabled!");
		this.reloadConfig();
		this.saveConfig();
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		String intro = ChatColor.BLACK + "[" + ChatColor.YELLOW + "Money" + ChatColor.BLACK + "] " + ChatColor.RESET + "";
    	if (args.length == 0) {
			player.sendMessage("========== " + intro + "==========");
			player.sendMessage("/money bal");
			if (player.hasPermission("cgeconomy.admin")) {
				player.sendMessage("/money bal <user>");
				player.sendMessage("/money admin give <username> <amount>");
				player.sendMessage("/money admin take <username> <amount>");
			}
			player.sendMessage("========== " + intro + "==========");
		}
    	if (args.length == 1) {
    		if (player.hasPermission("cgeconomy.player")) {
    		if (args[0].equalsIgnoreCase("bal")) {
        		int balance = getConfig().getInt("Users." + player.getName());
        		player.sendMessage("you have " + symbol + balance + " amount of money!");
    		}
    		}
    	}
    	if (args.length == 2) {
    		if (player.hasPermission("cgeconomy.admin")) {
    		if (args[0].equalsIgnoreCase("bal")) {
        		if (args[1].equalsIgnoreCase(args[1])) {
        			int playerbal = getConfig().getInt("Users." + args[1]);
        			player.sendMessage(args[1] + " has " + symbol + playerbal);
        		}
    		}
    		}
    	}
    	if (args.length == 3) {
    		if (player.hasPermission("cgeconomy.player")) {
    			if (args[0].equalsIgnoreCase("pay")) {
    				if (args[1].equalsIgnoreCase(args[1])) {
    					if (args[2].equalsIgnoreCase(args[2])) {
    						if (getConfig().contains("Users." + player.getName())){
    							if (getConfig().contains("Users." + args[1])){
    								int amt =  Integer.parseInt(args[2]);
            						//sender
            						int sbal = getConfig().getInt("Users." + player.getName());
                					int Sanswer = sbal - amt;
                					getConfig().set("Users." + args[1], Sanswer);
                					saveConfig();
                					int aSbal = getConfig().getInt("Users." + player.getName());
                					player.sendMessage("You have paid " + args[1] + symbol + args[2] + " you now have " + aSbal);
                					//reciever
                					String rString = args[1];
                					Player reciever = Bukkit.getPlayer(rString);
                					int rbal = getConfig().getInt("Users." + args[1]);
                					int Ranswer = rbal + amt;
                					getConfig().set("Users." + args[1], Ranswer);
                					saveConfig();
                					int aRbal = getConfig().getInt("Users." + args[1]);
                					reciever.sendMessage("You have recieved " + symbol + args[2] + "from " + player.getDisplayName() +  " you now have " + aRbal);
    							}
    						}
    					}
    				}
    			}
    		}
    	}
    	if (args.length == 4) {
    		if (player.hasPermission("cgeconomy.admin")) {
    		if (args[0].equalsIgnoreCase("admin")) {
        		if (args[1].equalsIgnoreCase("give")) {
        			if (args[2].equalsIgnoreCase(args[2])) {
        				if (args[3].equalsIgnoreCase(args[3])) {
            				if (getConfig().contains("Users." + args[2])) {
            					int bal = getConfig().getInt("Users." + args[2]);
            					int amt =  Integer.parseInt(args[3]);
            					int answer = bal + amt;
            					getConfig().set("Users." + args[2], answer);
            					saveConfig();
            					int lbal = getConfig().getInt("Users." + args[2]);
            					player.sendMessage(args[2] + " now has " + symbol + lbal);
            				}
        				}
        			}
        		}
        		if (args[1].equalsIgnoreCase("take")) {
        			if (args[2].equalsIgnoreCase(args[2])) {
        				if (args[3].equalsIgnoreCase(args[3])) {
            				if (getConfig().contains("Users." + args[2])) {
            					int bal = getConfig().getInt("Users." + args[2]);
            					int amt =  Integer.parseInt(args[3]);
            					int answer = bal - amt;
            					getConfig().set("Users." + args[2], answer);
            					saveConfig();
            					int lbal = getConfig().getInt("Users." + args[2]);
            					player.sendMessage(args[2] + " now has " + symbol + lbal);
            				}
        				}
        			}
        		}
    		}
    		}
    	}
    	return false; 
    }
    
    @EventHandler
    public void onPlayerFirstJoin (PlayerJoinEvent e){
    	if (e.getPlayer().hasPermission("cgeconomy.player")) {
    	if (getConfig().contains("Users." + e.getPlayer().getName())){
    		e.getPlayer().sendMessage("1");
    		int balance = getConfig().getInt("Users." + e.getPlayer().getName());
    		e.getPlayer().sendMessage("you have " + symbol + balance + " amount of money!");
    	} else {
    		e.getPlayer().sendMessage("2");
    		getConfig().set("Users." + e.getPlayer().getName(), 100);
    		saveConfig();
    		int balance = getConfig().getInt("Users." + e.getPlayer().getName());
    		e.getPlayer().sendMessage("you have gotten " + symbol + balance + " amount of money!");
    	}
		
	}
    }
}
