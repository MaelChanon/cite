package me.cite.heads;

import me.cite.Joueur.Joueur;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;


public class head {

    private ItemStack head;
    private List<Location> locations;
    private double price;



    public head(ItemStack it, double price) {
        locations = new LinkedList<>();
        head = it;
        this.price = price;
    }

    public int setBlock(Block block) {
        locations.add(block.getLocation());
        return locations.size();
    }

    public boolean IsItem(ItemStack itemInHand) {
        return this.head.equals(itemInHand);
    }

    public void spawn() {
        for(Location l : locations){
            l.getBlock().setType(Material.PLAYER_HEAD);
        }

    }

    public boolean rewardPlayer(Joueur j,Block b) {
        for(int i = 0; i<locations.size();++i)
        for(Location l : locations ){
            if(b.getLocation().equals(l)){
                j.addPoints(price);
                j.getPlayer().sendMessage("vous gagnez "+ price+ " $");
                l.getBlock().setType(Material.AIR);
                locations.remove(l);
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString(){
        return "il reste "+ locations.size() + " têtes à " +getPrice()+  " à collecter ";
    }
    public double getPrice() {
        return price;
    }

    public void despawn() {
        for(Location l : locations){
            l.getBlock().setType(Material.AIR);
        }
        locations.clear();
    }
}
