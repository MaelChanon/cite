package me.cite.event;

import me.cite.gestionnaire.gestionnaire;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class PlayerPVP  implements Listener {
    @EventHandler
    public void onPlayerPVP(EntityDamageByEntityEvent e){

        if(e.getDamager() instanceof Player && e.getEntity() instanceof  Player && !gestionnaire.get().isPvpEnabled())
            e.setCancelled(true);

    }
}
