package appli;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import commandes.cmd;
import net.minecraft.server.v1_8_R3.WorldServer;
import scoreBoard.scoreboard;
import shop.shops;
public class Bankal extends JavaPlugin {
	private static Bankal instance; 
	private static scoreboard score; 
	private static cmd commande;
	
	//AJOUTER COMMANDE POUR CHANGER TEAM DE JOUEUR (REMOVEPLAYERFROM / ADDPLAYERTO)
	@Override
	public void onEnable() {
		commande = new cmd();
		instance = this;
		score = new scoreBoard.scoreboard();
		score.runTaskTimer(this, 0, 20); //lancement du scoreboard
		registerCMD();
		loadListerners();
		System.out.println("toujours cette petite boule au ventre");
	}
	@Override
	public void onDisable() {
		System.out.println("tpd");
	}
	
	public static Bankal getBankal() {
		return instance;
	}
	
	public void registerCMD() {
		getCommand("test").setExecutor(commande); //chargement des commandes 
	}
	private static void loadListerners() {

		instance.getServer().getPluginManager().registerEvents(shops.get(), instance);
		
	}
	public static WorldServer getWorldServer(){
		World w =  Bukkit.getServer().getWorld("World");
		return ((CraftWorld) w).getHandle();
	}
	public static World getWorld(){
		return Bukkit.getServer().getWorld("World");
	}
	public static void registerEvent(Listener ihm) {
		instance.getServer().getPluginManager().registerEvents(ihm, instance);
		
	}

}
