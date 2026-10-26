package me.cite.event;


import me.cite.Joueur.Joueur;
import me.cite.gestionnaire.gestionnaire;
import me.cite.prok.Bankal;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;


public class PlayerConnexion implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        final Player p = e.getPlayer();
        if(!gestionnaire.get().containsJoueur(p)){
            p.kickPlayer("Vous n'êtes pas enregistré en tant que joueur");
            return;
        }
        Joueur j =  gestionnaire.get().getJoueur(p);
        j.getTeam().ajouterPlayer(p);
        gestionnaire.get().getJoueur(p).launchScore();
        if(!p.hasPlayedBefore()){
            p.teleport(gestionnaire.get().getSpawn());

        }
        p.isPermissionSet("spawn.use");

        p.setPlayerListName(j.getTeam().getColor()+p.getName());
        p.setCustomName(j.getTeam().getColor()+p.getName());
        p.setCustomNameVisible(true);
        e.setJoinMessage(ChatColor.GOLD + p.getName() +  " a rejoint la partie.");
        p.sendMessage(ChatColor.RED + "Bienvenue sur l'Ascension d'Okami V2");
    }

    @EventHandler
    public void OnPlayerDeath(PlayerDeathEvent e){
        final Player p = e.getEntity();
        final Player killer = p.getKiller();
        if(killer !=null){
            gestionnaire.get().getJoueur(killer).incrKill();
        }
        gestionnaire.get().getJoueur(p).incrDeath();

    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        final Player p = event.getPlayer();
        if(p.getBedSpawnLocation() == null){
            event.setRespawnLocation(new Location(Bankal.getWorld(),-2.0,86.0,36.9));
        }
        p.getInventory().addItem(new ItemStack(Material.COOKED_BEEF,16));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        event.setQuitMessage(ChatColor.GOLD + player.getName() + " a quitté la partie.");
    }
    @EventHandler
    public void NerfForce(BrewEvent event){
        ItemStack stack;
        PotionMeta p;
        for(int i = 0 ; i<3;++i){
            stack = event.getContents().getItem(i);
            if (stack != null && stack.getType() == Material.POTION) {
                p = (PotionMeta) event.getContents().getItem(i).getItemMeta();
                if(p.getBasePotionData().getType() == PotionType.STRENGTH){
                    event.setCancelled(true);
                    return;
                }

            }
        }
    }
    @EventHandler
    public void onTotemPop(final EntityResurrectEvent e) {
        if(e.getEntity() instanceof Player)
            e.setCancelled(true);
    }

    @EventHandler
    public void onGoldenApple( PlayerItemConsumeEvent e) {
        if(e.getItem().getType() == Material.ENCHANTED_GOLDEN_APPLE)
            e.setCancelled(true);
    }

    @EventHandler
    public void onGoldenApple( AsyncPlayerChatEvent e) {
        Joueur j = gestionnaire.get().getJoueur(e.getPlayer());
        if(j.ismuted())
            e.setCancelled(true);
    }
}
