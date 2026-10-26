package shop;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import IHM.Ihm;
import appli.Bankal;
import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.WorldServer;


public class shopBuilder{
 EntityVillager villager;

	private Map<Material,Integer> articles;
	private Listener ihm;

	public shopBuilder( Location location,String name) {
		World w =  Bukkit.getServer().getWorld("World");
		WorldServer world = ((CraftWorld) w).getHandle();
		articles = new HashMap<>();
		villager = new EntityVillager(world);
		villager.setCustomName(name);
		villager.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		this.ihm = new Ihm(name, this);



		world.addEntity(villager);
	
	}

	public List<Material> getList(){
		List<Material> l = new LinkedList<>();
		System.out.println(articles.keySet().size());
		for(Material m: articles.keySet()) {
			l.add(m);
		}
		return l;
	}
	public Entity getEntity() {
		return villager.getBukkitEntity();
	}

	public void open(Player player) {
		// TODO Auto-generated method stub
		
	}

	public boolean addItem(Material Item ,Integer Price) {

		if(articles.containsKey(Item))
			return false;
		articles.put(Item, Price);

		return true;
	}

	public int getValeur(Material type) {
		return articles.get(type);
	}

	public void enable() {
		Bankal.registerEvent(ihm);
		((Ihm) ihm).setInvs();
	}
}
