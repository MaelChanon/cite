package gestionnaire;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import org.bukkit.event.Listener;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;


import Joueur.Joueur;

import appli.Bankal;

import team.Equipe;

public class gestionnaire implements Listener {
	private static gestionnaire gestionnaire = null;

	private List<Equipe> equipes;
	private List<Joueur> joueurs;
	private boolean isStart;
	private PluginManager plugin;
	private Location specPoint;

	public gestionnaire(PluginManager p) {


		equipes = new ArrayList<>();
		plugin = p;
		joueurs = new ArrayList<>();
		getPlugin().registerEvents(this, Bankal.getBankal());
	}


	public Location getSpecPoint() {
		return specPoint;
	}
	public void setSpecPoint(Location l) {
		specPoint = l;
	}
	public PluginManager getPlugin() {
		return plugin;
	}

	public List<Equipe> getTeamList() {

		return equipes;
	}
	public boolean isStart() {
		return isStart;
	}

	@SuppressWarnings("deprecation")
	public void BroadCastMSG(String msg) {
		for(Player p : Bukkit.getOnlinePlayers())
			p.sendTitle(msg, null);
	}
	public void start() {
		isStart = true;
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.getInventory().clear();
		}
	}



	public static gestionnaire get() {
		if (gestionnaire == null)
			gestionnaire = new gestionnaire(Bankal.getBankal().getServer().getPluginManager());
		return gestionnaire;
	}

	public void removeTeamPlayer(Player p) {
		for(Equipe t : equipes) {
			if(t.ConstaintPlayer(p)) {
				//t.removePlayer(p);
				return;
			}

		}
	}

	public boolean hasTeam(Player p) {
		Joueur j = getJoueur(p);
		if(j == null)
			return false;
		return j.hasTeam();
	}

	public Joueur getJoueur(Player p) {
		for(Joueur j : joueurs) {
			if(j.getUUID().equals(p.getUniqueId()))
				return j;
		}
		return addPlayer(new Joueur(p.getUniqueId(), null));
	}
	public Equipe getTeam(ItemStack i) {
		for(Equipe t : equipes) {
			if(t.getItem().equals(i))
				return t;
		}
		return null;
	}


	public Equipe getTeam(Player p) {
		return getJoueur(p).getTeam();
	}

	public Equipe getTeam(String name) {
		for(Equipe t : equipes) {

			if(t.getName().equals(name)) {
				return t;
			}
		}

		throw new IllegalArgumentException();
	}
	public int getNbTeams() {
		return equipes.size();
	}

	public boolean exist(String name) {
		try {
			getTeam(name);
			return true;
		}
		catch (IllegalArgumentException e) {
			return false;
		}
	}


	public Joueur addPlayer(Joueur j) {
		if(!joueurs.contains(j))
			joueurs.add(j);
		return j;

	}
//	public void removePlayerFromTeam(String nomJoueur, String nomTeam) {
//		Player p = Bukkit.getPlayer(nomJoueur);
//		getTeam(nomTeam).removePlayer(p);
//	}
//	public void addPlayerTo(String nomJoueur, String nomTeam) {
//		Player player = Bukkit.getPlayer(nomJoueur);
//		Joueur p = getJoueur(player);
//		if(p ==null) return;
//		if(p.hasTeam())
//			p.getTeam().removePlayer(p);
//		getTeam(nomTeam).ajouterJoueur(player);
//	}













}
