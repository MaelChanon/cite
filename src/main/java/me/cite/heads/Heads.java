package me.cite.heads;

import me.cite.gestionnaire.gestionnaire;
import me.cite.prok.Bankal;
import me.cite.Joueur.Joueur;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class Heads implements Listener {
    private boolean isStarted;
    private List<head> tetes;

    public Heads(){
        tetes = new LinkedList<>();
        Bankal.registerEvent(this);
    }

    public void addItem(ItemStack it,double price) {
        tetes.add(new head(it,price));
    }
    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event){
        final Block block = event.getBlock();
        for(head h : tetes){
            if(h.IsItem(event.getPlayer().getItemInHand())){
                event.getPlayer().sendMessage(""+h.setBlock(block)+" tetes à "+h.getPrice());
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(!isStarted)return;
        final Block block = event.getClickedBlock();
        if(block == null)return;
        final Joueur j = gestionnaire.get().getJoueur(event.getPlayer());
        for(head h : tetes){
            if(h.rewardPlayer(j,block)){
                for(head h2 : tetes){
                    Bukkit.broadcastMessage(h2.toString());
                }
                return;
            }
        }
    }

    public void start() {
        isStarted =true;
        for(head h : tetes){
            h.spawn();
        }
    }

    public void end() {
        for(head h : tetes){
            h.despawn();
        }
        HandlerList.unregisterAll(this);
        tetes.clear();
    }
}
