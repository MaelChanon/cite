package me.cite.IHM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.cite.gestionnaire.gestionnaire;
import me.cite.shop.item;
import me.cite.shop.shopBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


/**
 * Cette classe permet met en place l'interface des shops
 */
@SuppressWarnings("deprecation")
public class ShopIHM implements Listener{

	private shopBuilder shop;
	private String name;


	private  List<Inventory> boutiques;

	//permet de pouvoir changer les pages
	private Map<UUID,Session> sessions;

	public ShopIHM(String name, shopBuilder s) {
		this.shop = s;
		sessions = new HashMap<>();
		this.name = name;
		boutiques = new ArrayList<Inventory>();
	}


	/**
	 * permet de mettre en place les interfaces
	 */
	public void setShops() {
		List<Material> lst = shop.getList();
		for(int f = 0 ; f<lst.size()%28+1; ++f){
			Inventory inv = getClearInventory();
			for(int i = 0; i<6; ++i) {
				for(int j = 0 ; j<9; ++j) {
					if(i !=0 && i != 5 && j != 0 && j != 8) {
						if(lst.isEmpty()) {
							if(boutiques.size() != 0)
								inv.setItem(47, specificItems.AVANTITEM.getIt());
							boutiques.add(inv);
							return;

						}
						inv.setItem(i*9+j, createItem(lst.get(0)));
						lst.remove(0);
					}
				}
			}
			inv.setItem(51, specificItems.APRESITEM.getIt());
			if(boutiques.size() != 0)
				inv.setItem(47, specificItems.AVANTITEM.getIt());
			boutiques.add(inv);
		}


	}

	/**
	 * renvoie une page specifique du shop
	 * @param page numéro de l'inventaire
	 * @return un inventaire d'objet
	 */
	public Inventory getInventory(int page) {

		return boutiques.get(page);
	}


	/**
	 * donne un inventaire "vide"
	 * @return un inventaire
	 */
	private Inventory getClearInventory() {
		Inventory inv = Bukkit.createInventory(null, 54, name);
		for(int i = 0; i<6; ++i) {
			for(int j = 0 ; j<9; ++j) {
				if(i ==0 || i == 5) {
					inv.setItem(i*9+j, specificItems.FILLITEM.getIt());
				}
				else if(j == 0 || j == 8) {
					inv.setItem(i*9+j, specificItems.FILLITEM.getIt());
				}
			}
		}
		return inv;
	}

	private ItemStack createItem(Material m) {
		ItemStack it = new ItemStack(m,1);

		ItemMeta meta = it.getItemMeta();

		meta.setLore(Arrays.asList("prix unitaire : "+shop.getValeur(m)+" $","Click gauche>> Vendre 1","Click droit>> Vendre tout"));
		it.setItemMeta(meta);
		return it;
	}


	public void fermerInventaire() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.getOpenInventory() != null)
				p.closeInventory();
		}
	}
	public String getName() {
		return name;
	}

	public void openInventory(Player p, int nb) {

		p.openInventory(getInventory(nb));
		p.updateInventory();

	}

	public int getNbPages() {
		return boutiques.size();
	}



	private Session getSession(Player whoClicked) {
		UUID id = whoClicked.getUniqueId();
		Session s = sessions.get(id);
		if( s == null){
			s = new Session(this, gestionnaire.get().getJoueur(whoClicked));
			sessions.put(whoClicked.getUniqueId(), s);
		}
		return s;
	}

	public boolean isValidInventory(Inventory inventory, int nb) {
		if(boutiques.contains(inventory)){
			if(nb < 54)
				return true;
		}
		return false;
	}

	public void action(ItemStack stack, Player p, String name, InventoryAction a) {
		Session s = getSession(p);

		if(stack.equals(specificItems.FILLITEM.getIt()))
			return;
		if(stack.equals(specificItems.AVANTITEM.getIt())) {
			s.precedent();
			openInventory(p,s.getPage());
			s.setSwitching(false);
			return;
		}
		if(stack.equals(specificItems.APRESITEM.getIt())) {
			s.suivant();
			openInventory(p,s.getPage());
			s.setSwitching(false);
			return;
		}

		double price = shop.getValeur(stack.getType());
		if( price == -1){
			return;
		}
		if(a == InventoryAction.PICKUP_ALL){
			shop.addCount(stack.getType(),1);
			s.sell(stack.getType(),price);
			return;
		}
		if(a == InventoryAction.PICKUP_HALF){
			shop.addCount(stack.getType(),s.sellAll(stack.getType(),price));

		}

	}

	public boolean isInventory(Inventory inv,Player p) {
		if (boutiques.contains(inv)){
			return true;
		}
		return false;
	}

	private boolean isSession(Player p) {
		return sessions.get(p.getUniqueId()) != null;
	}

	public void close(Inventory inv,Player p) {
		Session s = getSession(p);
		if(s.isSwitching() == false){
			sessions.remove(p.getUniqueId());
		}


	}

	public void changePrice(item it) {
		ItemStack item;
		for(Inventory inv : boutiques){
			for(int i = 0; i<inv.getSize();++i){
				item = inv.getItem(i);
				if(item == null)
					continue;
				else if (item.getType().equals(it.getMaterial())) {
					inv.setItem(i,createItem(it.getMaterial()));
				}
			}
		}
	}
}
