package me.cite.claim;

import me.cite.Joueur.Joueur;
import me.cite.team.Equipe;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;

import java.awt.*;

public class Claim {
    private Rectangle rectangle;
    private Equipe equipe;
    public Claim(int x1,int y1, int x2,int y2,Equipe e){
        this.equipe = e;
        rectangle = new Rectangle(x1,y1,x2-x1, y2-y1);
    }
    public boolean contains(Location l){

        return  rectangle.contains(l.getX(),l.getZ());
    }

    public boolean isBlockAllowed(Joueur joueur, Block block){
        return joueur.getTeam().equals(equipe) && block.getState() instanceof ShulkerBox;
    }

    public Equipe getTeam() {
        return equipe;
    }
}
