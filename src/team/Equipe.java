package team;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Team;


import Joueur.Joueur;

public class Equipe {


	private ChatColor color;
	private String name;
	private Team team;
	private Location location;
	private List<Joueur> joueurs;
	private ItemStack block;
	private Inventory inv;
	public Equipe(String name, ChatColor color) {
		this.color = color;
		this.name = name;
		team = null;
		block = getBlock(color,name);
		joueurs = new LinkedList<>();
	}
	
	public Location getSpawnPoint() {
		return location;
	}
	private static ItemStack getBlock(ChatColor c, String name) {
		switch(c) {
			case RED:
				return getWool(14,name);
			case YELLOW:
				return getWool(4,name);
			case DARK_BLUE:
				return getWool(11,name);
			case GREEN:
				return getWool(13,name);
			case DARK_PURPLE:
				return getWool(10,name);
			default:
				return null;
		}
	}

	private static ItemStack getWool(int numero, String Display) {
		ItemStack wools1 = new ItemStack(Material.WOOL, 1, (byte)numero);
		 ItemMeta woolm1 = wools1.getItemMeta();
		 woolm1.setDisplayName(Display);
		 wools1.setItemMeta(woolm1);
		 return wools1;
	}
	public ItemStack getItem() {
		return setItemMeta(block);
	}
	
	@SuppressWarnings("deprecation")
	private ItemStack setItemMeta(ItemStack block) {
        List<String> lst = new ArrayList<>();
        ItemMeta meta = block.getItemMeta();
        for(OfflinePlayer p2 : getTeam().getPlayers()) {
        	if (lst.size()>3) {
        		lst.add("...");
        		break;
        	}
        	lst.add(p2.getName());
        }
        meta.setLore(lst);
        block.setItemMeta(meta);
        return block;
	}
	
	public String getName() {
		return name;
	}
	public ChatColor getColor() {
		return color;
	}
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team2) {
		team = team2;
		
	}
	
	public boolean isDead() {
		for(Joueur p : joueurs) {
			if(p.isAlive())
				return false;
		}
		return true;
	}



	@SuppressWarnings("deprecation")
	public  boolean ConstaintPlayer(Player p) {
		for(OfflinePlayer p2 : getTeam().getPlayers()) {
			if(p2.getUniqueId().equals(p.getUniqueId()))
				return true;
		}
		return false;
	}


	public Location getLocation() {
		return location;
	}

//	@SuppressWarnings("deprecation")
//	public void ajouterJoueur(Player p) {
//		if(ConstaintPlayer(p))return;
//		team.addPlayer(p);
//		Joueur j = gestionnaire.get().getJoueur(p);
//		j.addTeam(this);
//		joueurs.add(j);	
//		AffichageTeams.get().UpdateTab(p,getColor());
//	}
//
//	@SuppressWarnings("deprecation")
//
//	public void removePlayer(Player p) {
//		if(!ConstaintPlayer(p))return;
//		team.removePlayer(p);
//		for(Joueur p2 : joueurs) {
//			if(p2.getUUID().equals(p.getUniqueId())) {
//				joueurs.remove(p2);
//				p2.removeTeam();
//				AffichageTeams.get().UpdateTab(p, ChatColor.GRAY);
//				return;
//			}		
//		}
//		throw new joueurSansTeamEx();
//	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipe other = (Equipe) obj;
		if (block == null) {
			if (other.block != null)
				return false;
		} else if (!block.equals(other.block))
			return false;
		if (color != other.color)
			return false;
		if (inv == null) {
			if (other.inv != null)
				return false;
		} else if (!inv.equals(other.inv))
			return false;
		if (joueurs == null) {
			if (other.joueurs != null)
				return false;
		} else if (!joueurs.equals(other.joueurs))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}

	public void setSpawnPoint(Location location2) {
		location = location2;
		
	}
	public String toString() {
		return color+name;
	}

	@SuppressWarnings({ "unlikely-arg-type", "deprecation" })
	public void removeAllPlayers() {
		team.getPlayers().removeAll(joueurs);
		joueurs.removeAll(joueurs);
	}

	public int getNumberOfPlayer() {
		return joueurs.size();
	}

	public Inventory getInv() {
		return inv;
	}

	public void setInv(Inventory inv) {
		this.inv = inv;
	}



//	public void removePlayer(Joueur p) {
//		removePlayer(Bukkit.getPlayer(p.getUUID()));
//		
//	}

	public void reviveAllPlayer() {
		for(Joueur j : joueurs) {
			j.revive();
		}
		
	}





}