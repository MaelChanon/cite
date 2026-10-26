package me.cite.shop;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import me.cite.IHM.ShopIHM;
import me.cite.prok.Bankal;
import net.minecraft.server.level.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.entity.Entity;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;


public class shopBuilder{
	Villager villager;



	private Map<Material,item> articles;
	private ShopIHM ihm;

	public shopBuilder( Location location,String name) {
		World w =  Bankal.getWorld();
		WorldServer world = ((CraftWorld) w).getHandle();
		articles = new HashMap<>();
		initVillager(name,w,location);
		this.ihm = new ShopIHM(name, this);

	}

	private void initVillager(String name,World w,Location location){
		villager = w.spawn(location,Villager.class);
		villager.setCustomName(name);
		villager.setInvulnerable(true);
		villager.setVelocity(new Vector(0,0,0));
		villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,Integer.MAX_VALUE,999,false,false));
		villager.setGravity(false);
		villager.setCollidable(false);
	}


	public List<Material> getList(){
		List<Material> l = new LinkedList<>();
		for(Material m: articles.keySet()) {
			l.add(m);
		}

		return l;
	}



	public boolean addItem(item it) {
		Material material = it.getMaterial();
		if(articles.containsKey(material))
			return false;
		articles.put(material, it);
		return true;
	}



	public double getValeur(Material type) {
		item i = articles.get(type);
		if(i == null)
			return -1;
		return i.getPrice();
	}


	public void enable() {
		Bankal.registerEvent(ihm);
		((ShopIHM) ihm).setShops();
	}

	public void addCount(Material m,int count){
		item it = articles.get(m);
		if(it == null);
		it.addCount(count);
	}

	public boolean isTheSop(Entity entity) {
		return villager.equals(entity);
	}

	public void open(Player player) {
		ihm.openInventory(player, 0);
	}

	public boolean containsValidInventory(Inventory inventory, int nb) {
		return  ihm.isValidInventory( inventory, nb);
	}

	public void action(ItemStack stack, Player p, String name, InventoryAction a) {
		ihm.action(stack,p,name,a);
	}

	public boolean containsInventory(Inventory inv, Player p) {
		return ihm.isInventory(inv,p);
	}

	public void close(Inventory inv,Player p) {
		ihm.close(inv,p);
	}

	public void delete() {
		villager.remove();
	}

	public List<item> getItems() {
		List<item> lst = new LinkedList<>();
		for(Material m : articles.keySet()){
			lst.add(articles.get(m));
		}
		return lst;
	}

	public String getName() {
		return villager.getCustomName();
	}

	public item getItem(Material m) {
		return articles.get(m);
	}

	public boolean changePrice(Material m,double price) {
		item it = getItem(m);
		if(it == null)
			return false;
		it.setPrice(price);
		ihm.changePrice(it);
		return true;
	}
}
