package me.cite.prok;


import me.cite.commandes.cmd;

import me.cite.event.PlayerConnexion;
import me.cite.event.PlayerPVP;
import me.cite.gestionnaire.gestionnaire;
import me.cite.team.FileGetter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.stream.Collectors;


public class Bankal extends JavaPlugin {
    private static Bankal instance;

    private static cmd commande;
    private static FileGetter t;

    //AJOUTER COMMANDE POUR CHANGER TEAM DE JOUEUR (REMOVEPLAYERFROM / ADDPLAYERTO)
    @Override
    public void onEnable() {
        commande = new cmd();
        instance = this;
        t = new FileGetter(gestionnaire.get());
        t.load();
        registerCMD();
        loadListerners();
        System.out.println("toujours cette petite boule au ventreEEEE");

    }
    @Override
    public void onDisable() {
        System.out.println("tpd");
        t.saveTeams();
        t.saveConfiguration();
        t.saveItems();
        gestionnaire.get().deleteHolograms();
        gestionnaire.get().deleteShops();
    }


    public static Bankal getBankal() {
        return instance;
    }

    public void registerCMD() {
        getCommand("enableRanking").setExecutor(commande);
        getCommand("disableRanking").setExecutor(commande);
        getCommand("enablePVP").setExecutor(commande);
        getCommand("disablePVP").setExecutor(commande);
        getCommand("enableShop").setExecutor(commande);
        getCommand("disableShop").setExecutor(commande);
        getCommand("spawn").setExecutor(commande);
        getCommand("addPoints").setExecutor(commande);
        getCommand("mute").setExecutor(commande);
        getCommand("getMostSolded").setExecutor(commande);
        getCommand("demute").setExecutor(commande);
        getCommand("changePrices").setExecutor(commande);
        getCommand("enableHeadEvent").setExecutor(commande);
        getCommand("setBlockPrice").setExecutor(commande);
        getCommand("startHeadEvent").setExecutor(commande);
        getCommand("finirHeadEvent").setExecutor(commande);
    }
    private static void loadListerners() {

        instance.getServer().getPluginManager().registerEvents(gestionnaire.get().getShops(), instance);
        instance.getServer().getPluginManager().registerEvents(new PlayerConnexion(), instance);
        instance.getServer().getPluginManager().registerEvents(gestionnaire.get().getClaims(), instance);
        instance.getServer().getPluginManager().registerEvents(new PlayerPVP(), instance);


    }

    public static World getWorld(){
        return Bukkit.getServer().getWorlds().get(0);
    }
    public static void registerEvent(Listener ihm) {
        instance.getServer().getPluginManager().registerEvents(ihm, instance);

    }

}
