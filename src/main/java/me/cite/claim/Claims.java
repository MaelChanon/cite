package me.cite.claim;

import me.cite.Joueur.Joueur;
import me.cite.gestionnaire.gestionnaire;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class Claims implements Listener {
    private Claim spawn;
    private List<Claim> claimsBase = new ArrayList<>();

    public Claims(){
    }

    public boolean isInSpawn(Joueur joueur) {
        if(!(joueur.getPlayer().getWorld().getEnvironment().equals(World.Environment.NORMAL)))
            return false;
        return spawn.contains(joueur.getPlayer().getLocation());
    }


    @EventHandler
    public void breakEvent(BlockBreakEvent e){
        final Player p = e.getPlayer();
        if(isCancel(e.getBlock(),gestionnaire.get().getJoueur(p))){
            p.sendMessage("Vous ne pouvez pas casser de blocks dans un claims");
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void entityExploseEvent(EntityExplodeEvent e){
        if( isInClaim(e.getEntity())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent e){
        final Player p = e.getPlayer();
        if(isCancel(e.getBlock(),gestionnaire.get().getJoueur(p))){
            p.sendMessage("Vous ne pouvez pas placer de blocks dans un claim");
            e.setCancelled(true);
        }

    }

    private boolean isCancel(Block b, Joueur j){

        if(!j.getPlayer().getWorld().getEnvironment().equals(World.Environment.NORMAL)||!spawn.contains(b.getLocation())){
            return false;
        }
        for(Claim c : claimsBase ){
            if(c.contains(b.getLocation())){
                if(c.isBlockAllowed(j,b))
                    return false;
            }
        }
        if(j.isOP())
            return false;
        return true;
    }

    @EventHandler
    public void onPlayerGetDamages(EntityDamageEvent e){
        Entity entity = e.getEntity();
        if(!(entity instanceof Player)  ||!entity.getWorld().getEnvironment().equals(World.Environment.NORMAL))return;
        if( isInClaim(entity)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void OnInteractAtEntity(PlayerInteractAtEntityEvent e) {
        Entity entity = e.getRightClicked();
        if(!(entity instanceof ArmorStand) || !entity.getWorld().getEnvironment().equals(World.Environment.NORMAL))return;
        if( isInClaim(entity)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void itemFrameDrop(EntityDamageByEntityEvent e) {

        Entity entity = e.getEntity();
        if(((entity instanceof ItemFrame ) || (entity instanceof ArmorStand)) && entity.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            if( isInClaim(entity)){
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        final Player p = event.getPlayer();
        final Block block = event.getClickedBlock();
        if(block == null)return;
        final Material type = block.getType();
        if(((type.equals(Material.FARMLAND) ||type.equals(Material.CHEST) || type.equals(Material.BARREL) ||block.getState() instanceof ShulkerBox) && p.getWorld().getEnvironment().equals(World.Environment.NORMAL))) {
            if(block.getState() instanceof ShulkerBox){
                for(Claim c : claimsBase){
                    if((c.contains(block.getLocation()) && c.isBlockAllowed(gestionnaire.get().getJoueur(p),block) ||gestionnaire.get().getJoueur(p).isOP()))
                        return;
                }
            }
            if(spawn.contains(block.getLocation())){
                event.setCancelled(true);
            }
        }

    }
    private boolean isInClaim(Entity entity){
        return (spawn.contains(entity.getLocation()) && entity.getWorld().getEnvironment().equals(World.Environment.NORMAL));
    }


    public void addClaim(Claim c) {
        if(c.getTeam() == null)
            spawn = c;
        else
            claimsBase.add(c);
    }
}
