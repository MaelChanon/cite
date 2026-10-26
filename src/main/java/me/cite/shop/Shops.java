package me.cite.shop;

import java.util.*;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class Shops implements Listener{
	private List<shopBuilder> shops;
	private boolean enabled;

	public Shops() {
		shops = new ArrayList<>();
	}

    public void setEnabled(boolean shops) {
		enabled = shops;
    }

	public boolean isEnabled() {
		return enabled;
	}

    public void add(shopBuilder vill) {
		shops.add(vill);
	}
	
	public shopBuilder getRightShop(Entity entity) {
		for(shopBuilder shop : shops) {
			if(shop.isTheSop(entity)) {
				return shop;
			}
		}
		return null;
	}
	@EventHandler
	void entityClick(PlayerInteractEntityEvent e) {

		shopBuilder shop = getRightShop(e.getRightClicked());
		if(shop != null) {
			e.setCancelled(true);
			if(isEnabled())
				shop.open(e.getPlayer());
		}
	}

	@EventHandler
	void openInventory(InventoryClickEvent e){
		shopBuilder shop = getRightShop( e.getCurrentItem(),e.getInventory(), 10);
		if(shop != null && e.getRawSlot()>= 54){
			e.setCancelled(true);
		}
		if(shop == null){
			return;
		}
		e.setCancelled(true);
		ItemStack stack = e.getCurrentItem();
		shop.action(stack, (Player) e.getWhoClicked(),e.getView().getTitle(),e.getAction());

	}


	@EventHandler
	void onClose(InventoryCloseEvent e){
		Player p = (Player) e.getPlayer();
		Inventory inv = e.getInventory();
		shopBuilder shop = getShop(inv, p);
		if(shop == null) return;
		shop.close(inv, p);

	}

	private shopBuilder getShop(Inventory inv,Player p){
		int i = 0;
		for(shopBuilder shop : shops){
			if(shop.containsInventory( inv,p)){
				return shops.get(i);
			}
			++i;
		}
		return null;
	}
	private shopBuilder getRightShop( ItemStack it, Inventory inv, int slot){
		int i = 0;
		if(it == null){
			return null;
		}
		for(shopBuilder shop : shops){
			if(shop.containsValidInventory(inv,slot)){
				return shops.get(i);
			}
			++i;
		}
		return null;
	}

    public void deleteShops() {
		for(shopBuilder s : shops){
			s.delete();
		}
    }

	public List<shopBuilder> getshopBuilders() {
		List<shopBuilder> lst = new ArrayList<>();
		lst.addAll(shops);
		return lst;
	}

    public List<item> getItems() {
		List<item> lst = new LinkedList<>();
		for(shopBuilder s : shops){
			for(item it : s.getItems()){
				lst.add(it);
			}
		}
		Collections.sort(lst);
		return lst;
    }

	public boolean changePrice(Material m,double price) {
		for(int i = 0 ; i<shops.size(); ++i){
			if(shops.get(i).changePrice(m,price));
				return true;
		}
		return false;

	}
}
