package IHM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import gestionnaire.gestionnaire;
import shop.shopBuilder;


@SuppressWarnings("deprecation")
public class Ihm implements Listener{

	private shopBuilder shop;
	private String name;
	private static ItemStack fillItem;
	private static ItemStack avantItem;
	private static ItemStack apresItem;
	private  List<Inventory> invs;
	private Map<UUID,Session> sessions;
	static {

		fillItem = new ItemStack(Material.STAINED_GLASS_PANE,1,DyeColor.BLACK.getData());
		avantItem = new ItemStack(Material.ARROW,1);
		apresItem = new ItemStack(Material.ARROW,1);
		ItemMeta m = avantItem.getItemMeta();
		m.setDisplayName("page précédente");
		avantItem.setItemMeta(m);
		m.setDisplayName("page suivante");
		apresItem.setItemMeta(m);
	}
	public Ihm(String name,shopBuilder s) {
		this.shop = s;
		sessions = new HashMap<>();
		this.name = name;
	}

	public void setInvs() {
		invs = new ArrayList<Inventory>();
		setInvs(shop.getList());
	}
	public Inventory getInventory(int page) {

		return invs.get(page);
	}



	private Inventory getClearInventory() {
		Inventory inv = Bukkit.createInventory(null, 54, name);
		for(int i = 0; i<6; ++i) {
			for(int j = 0 ; j<9; ++j) {
				if(i ==0 || i == 5) {
					inv.setItem(i*9+j, fillItem);
				}
				else if(j == 0 || j == 8) {
					inv.setItem(i*9+j, fillItem);
				}
			}
		}
		return inv;
	}
	private void setInvs(List<Material> lst) {
		Inventory inv = getClearInventory();

		for(int i = 0; i<6; ++i) {
			for(int j = 0 ; j<9; ++j) {
				if(i !=0 && i != 5 && j != 0 && j != 8) {
					if(lst.isEmpty()) {
						if(invs.size() != 0)
							inv.setItem(47, avantItem);
						invs.add(inv);
						return;
						
					}
	
					inv.setItem(i*9+j, createItem(lst.get(0)));
					lst.remove(0);

				}
			}
		}


		inv.setItem(51, apresItem);
		if(invs.size() != 0)
			inv.setItem(47, avantItem);
		invs.add(inv);
		setInvs(lst);
	}
	private ItemStack createItem(Material m) {
		System.out.println(m.toString());
		ItemStack it = new ItemStack(m,1);
		ItemMeta meta = it.getItemMeta();
		meta.setLore(Arrays.asList(shop.getValeur(m)+" émeraude(s)"));
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
	public void UpdateTab(Player player, ChatColor c) {
		player.setPlayerListName(c + player.getName());

	}

	public int getNbPages() {
		return invs.size();
	}
	@EventHandler
	void rightClickVillager(PlayerToggleSneakEvent e) {
		System.out.println("on est bon");
		Session s = getSession(e.getPlayer());
		s.ouvrir();

	}
	@EventHandler
	void InventoryClick(InventoryClickEvent e) {
		Inventory inv = e.getInventory();
		ItemStack current = e.getCurrentItem();
		if(current == null || inv.getName() != name) return;    
		e.setCancelled(true);
		if(current.equals(fillItem))
			return;
		Session s = getSession((Player) e.getWhoClicked());
		if(current.equals(avantItem)) {
			s.precedent();
			s.ouvrir();
			return;
		}
		if(current.equals(apresItem)) {
			s.suivant();
			s.ouvrir();
			return;
		}
	
		//int valeur = shop.getValeur(current.getType());
	}

	private Session getSession(Player whoClicked) {
		Session	s = new Session(this, gestionnaire.get().getJoueur(whoClicked));
		sessions.put(whoClicked.getUniqueId(), s);
		return s;
	}
}
