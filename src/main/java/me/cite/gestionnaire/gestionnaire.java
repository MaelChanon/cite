package me.cite.gestionnaire;


import me.cite.heads.Heads;
import me.cite.Joueur.Joueur;
import me.cite.claim.Claim;
import me.cite.claim.Claims;
import me.cite.hologram.Hologram;
import me.cite.shop.Shops;
import me.cite.shop.shopBuilder;
import me.cite.team.Equipe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Classe static qui gère la partie
 */
public class gestionnaire{
	private static gestionnaire gestionnaire = null;

	private List<Equipe> equipes;
	private Map<UUID,Joueur> joueurs;

	private Claims claims;

	private Shops shops;
	private Hologram hologram;

	private boolean pvp;

	private Heads heads;
	private Location spawn;

	private gestionnaire() {
		equipes = new ArrayList<>();
		joueurs = new HashMap<>();
		claims = new Claims();
		shops = new Shops();

	}

	public void deleteHolograms() {
		hologram.cancelHolograms();
	}

	/**
	 * indique si un player est enregistré en tant que joueur (joueur désigne aussi le staff)
	 * @param p un player
	 * @return true le p est un joueur
	 */
    public boolean containsJoueur(Player p) {
		return joueurs.containsKey(p.getUniqueId());
    }




	/**
	 * Renvoie une liste de toutes les équipes
	 * @return une liste
	 */
	public List<Equipe> getTeamList() {
		return equipes;
	}



	/**
	 * Affiche un message à tous les joueurs de la partie
	 * @param msg le message
	 */
	@SuppressWarnings("deprecation")
	public void BroadCastMSG(String msg) {
		for(Player p : Bukkit.getOnlinePlayers())
			p.sendTitle(msg, null);
	}

	/**
	 * pour recupérer l'instance static
	 * @return instance du gestionnaire
	 */
	public static gestionnaire get() {
		if (gestionnaire == null){
			gestionnaire = new gestionnaire();
		}

		return gestionnaire;
	}

	public void LoadHolograms(Location l) {
		hologram = new Hologram(l);
	}

	/**
	 * supprime un joueur d'une équipe
	 * @param j instance de joueur
	 */
	public void removeTeamPlayer(Joueur j) {
		Player p = j.getPlayer();
		for(Equipe t : equipes) {
			if(t.ConstaintPlayer(p)){
				t.removePlayer(j);
				return;
			}

		}
	}

	/**
	 * recupère une instance de joueur associé à un player
	 * @param p
	 * @return
	 */
	public Joueur getJoueur(Player p) {
		return joueurs.get(p.getUniqueId());
	}


	/**
	 * récupère l'équipe associée à un player
	 * @param p un joueur
	 * @return l'équipe s'il en a une sinon null
	 */
	public Equipe getTeam(Player p) {
		return getJoueur(p).getTeam();
	}

	/**
	 * retourne une équipe selon son nom
	 * @param name nom de l'équipe
	 * @return
	 */
	public Equipe getTeam(String name) {
		for(Equipe t : equipes) {
			if(t.getName().equals(name)) {
				return t;
			}
		}

		return null;
	}

	/**
	 * retourne le nombre d'équipes
	 * @return
	 */
	public int getNbTeams() {
		return equipes.size();
	}

	/**
	 * indique si une équipe existe
	 * @param name nom de l'équipe
	 * @return true si elle existe sinon false
	 */
	public boolean DoesTeamExists(String name) {
		try {
			getTeam(name);
			return true;
		}
		catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * ajoute un joueur au gestionnaire
	 * @param j joueur j
	 * @return le joueur ajouté
	 */
	public Joueur addPlayer(Joueur j) {
		if(!joueurs.containsKey(j.getUUID()))
			joueurs.put(j.getUUID(),j);
		return j;

	}

	/**
	 * ajoute une équipe
	 * @param e
	 */
	public void addTeam(Equipe e) {
		equipes.add(e);
	}


	public void addClaim(Claim c) {
		claims.addClaim(c);
	}

	public Claims getClaims() {
		return claims;
	}

	public Shops getShops() {

		return shops;
	}

	public void ajouterShop(shopBuilder s) {
		shops.add(s);
	}

	private List<Equipe> getNonSpectateur(){
		List<Equipe> lst = new ArrayList<>(equipes);
		for(int i=0; i<lst.size(); ++i){
			if(lst.get(0).isSpectator())
				lst.remove(i);
		}
		return lst;
	}
	public List<Equipe> getEquipes(){
		List<Equipe> lst = getNonSpectateur();
		int min;
		Equipe retenu;
		for(int i = 0; i<lst.size()-1; ++i){
			min = i;
			for(int j = i+1;j<lst.size();++j){
				if( lst.get(min).getPoints()>lst.get(j).getPoints())
					min = j;
			}
			if(min != i ){
				retenu = lst.get(i);
				lst.set(i,lst.get(min));
				lst.set(min,retenu);
			}
		}
		return lst;
	}


	public void updateHolograms() {
		hologram.updateHolograms();
	}

	public void deleteShops() {
		shops.deleteShops();
	}

	public void enableRanking() {
		hologram.enable();
	}
	public void disableRanking() {
		hologram.disable();
	}

	public void setPvp(boolean pvpEnabled) {
		pvp = pvpEnabled;
	}
	public boolean isPvpEnabled(){
		return pvp;
	}

	public void setShops(boolean shops){
		this.shops.setEnabled(shops);
	}

	public boolean isShopEnabled(){
		return  shops.isEnabled();
	}

	public Location getSpawn() {
		return spawn;
	}
	public void setSpawn(Location l){
		spawn = l;
	}

	public boolean isInSpawn(Joueur joueur) {
		return claims.isInSpawn(joueur);
	}

	public Boolean isRankingEnabled() {
		return hologram.isEnabled();
	}

    public boolean enableHeadEvent() {
		if(heads == null){
			heads = new Heads();
			return true;
		}
		return false;
    }

	public boolean addBlockHeadEvent(ItemStack it,double price) {
		if(heads == null)
			return false;
		heads.addItem(it,price);
		return true;
	}

	public boolean startHeadEvent() {
		if(heads == null)return false;
		heads.start();
		for(Player p : Bukkit.getOnlinePlayers())
			p.sendTitle(ChatColor.RED+"Debut de lévénement", null);

		return true;
	}

	public boolean finirHeadEvent() {
		heads.end();
		heads = null;
		for(Player p : Bukkit.getOnlinePlayers())
			p.sendTitle(ChatColor.RED+"Fin de l'événement", null);
		return true;
	}
}
