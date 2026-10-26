package shop;

import java.io.File;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import appli.Bankal;




public class shops implements Listener{
	private List<shopBuilder> shops = new ArrayList<>();
	private static shops get;
	
	private void setup() {
		//Bankal.getBankal().getDataFolder().mkdir();
		File file = new File(Bankal.getBankal().getDataFolder(),"shop.yml" );
		final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		Location l;
		shopBuilder s;
		Material m;
		for(String KeyPNJ : config.getKeys(false)) {
			l = new Location(Bankal.getWorld(),(double)config.get(KeyPNJ+".location.getX"),(double)config.get(KeyPNJ+".location.getY"),(double)config.get(KeyPNJ+".location.getZ"));
			s = new shopBuilder( l, KeyPNJ);
			for(String KeyItem : config.getConfigurationSection(KeyPNJ+".items").getKeys(false)) {
				m = Material.matchMaterial((String) config.getConfigurationSection(KeyPNJ+".items."+KeyItem).get("name"));
				s.addItem(m, (Integer) config.getConfigurationSection(KeyPNJ+".items."+KeyItem).get("price"));
			}
			s.enable();
			shops.add(s);
		}
		
		
	}
	private shops() {
		setup();


	}
	public static shops get() {
		if(get == null)
			get = new shops();
		return get;
	}
	public void add(shopBuilder vill) {
		shops.add(vill);
	}
	
	public shopBuilder getShop(Entity entity) {
		for(shopBuilder shop : shops) {
			if(shop.getEntity().equals(entity)) {
				return shop;
			}
		}
		return null;
	}
	@EventHandler
	void onRightClick(PlayerInteractEntityEvent e) {
		shopBuilder shop = getShop(e.getRightClicked());
		if(shop != null) {
			e.setCancelled(true);
			shop.open(e.getPlayer());
		}

	}
}
