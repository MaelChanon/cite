package me.cite.hologram;

import me.cite.gestionnaire.gestionnaire;
import me.cite.prok.Bankal;
import me.cite.team.Equipe;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class Hologram {
    private List<ArmorStand> holograms;
    private boolean enabled = true;


    public Hologram(Location l){
        holograms = new ArrayList<>();
        List<Equipe> equipes = gestionnaire.get().getEquipes();
        ArmorStand h;
        for(int i = 0; i<equipes.size(); ++i){
            h = (ArmorStand) Bankal.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
            transform(h,equipes.get(i));
            holograms.add(h);
            l.setY(l.getY()+0.5);
        }
    }

    private void transform(ArmorStand h, Equipe e) {
        h.setVisible(false);
        h.setCustomNameVisible(true);
        h.setGravity(false);
        h.setInvulnerable(true);
        h.setCustomName(getPoints(e));
    }

    private String getPoints(Equipe e){
        return e.getColor()+e.getName()+ ChatColor.WHITE + " : "+e.getPoints()+ " $";
    }
    public void cancelHolograms(){

        for(int i = 0; i<holograms.size(); ++i){
            holograms.get(i).remove();
        }
        holograms.clear();
    }
    public void updateHolograms(){
        List<Equipe> equipes = gestionnaire.get().getEquipes();
        for(int i = 0; i<holograms.size(); ++i){
            holograms.get(i).setCustomName(getPoints(equipes.get(i)));
        }
    }

    public void enable() {
        enabled = true;
        for(ArmorStand a : holograms){
            a.setCustomNameVisible(true);
        }
    }
    public void disable() {
        enabled = false;
        for(ArmorStand a : holograms){
            a.setCustomNameVisible(false);
        }
    }

    public Boolean isEnabled() {
        return enabled;
    }
}
