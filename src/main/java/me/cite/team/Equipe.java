package me.cite.team;


import java.util.*;

import me.cite.Joueur.Joueur;
import me.cite.gestionnaire.gestionnaire;
import me.cite.prok.Bankal;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;



public class Equipe{


	private ChatColor color;
	private String name;
	private Team team;
	private double points;
	private Map<UUID,Joueur> joueurs;
	private boolean spectator;
	public Equipe(String name, ChatColor color,boolean op) {
		this.spectator = op;
		this.color = color;
		this.name = name;
		team = Bankal.getBankal().getServer().getScoreboardManager().getNewScoreboard().registerNewTeam(name);
		team.setPrefix(color.toString());
		joueurs = new HashMap<>();
	}

	public boolean isSpectator(){
		return spectator;
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

	/**
	 * permet de d'ajouter une instance de team à l'équipe (class de bukkit)
	 * @param team2
	 */
	public void setTeam(Team team2) {
		team = team2;
		
	}

	/**
	 * indique si un joueur fait parti de l'équipe
	 * @param p un player
	 * @return true si il fait parti de l'équipe
	 */
	@SuppressWarnings("deprecation")
	public  boolean ConstaintPlayer(Player p) {
		return joueurs.containsKey(p.getUniqueId());
	}


	/**
	 * permet d'ajouter un joueur à l'équipe
	 * @param j un joueur
	 */
	@SuppressWarnings("deprecation")
	public void ajouterJoueur(Joueur j) {

		j.addTeam(this);
		joueurs.put(j.getUUID(),j);
	}
	public void ajouterPlayer(Player p){

		if(! joueurs.containsKey(p.getUniqueId()) || team.hasPlayer(p))return;
		System.out.println(p.getName());
		team.addPlayer(p);
	}
	/**
	 * remove un joueur de l'équipe
	 * @param j un joueur
	 */
	@SuppressWarnings("deprecation")
	public void removePlayer(Joueur j) {
		Player p = j.getPlayer();
		if(!ConstaintPlayer(p))return;
		team.removePlayer(p);
		joueurs.remove(j);


	}



	public String toString() {
		return color+name;
	}


	/**
	 * returne la taille de l'équipe
	 * @return nombre de joueurs
	 */
	public int getNumberOfPlayer() {
		return joueurs.size();
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Equipe equipe = (Equipe) o;
		return points == equipe.points && color == equipe.color && Objects.equals(name, equipe.name) && Objects.equals(team, equipe.team) && Objects.equals(joueurs, equipe.joueurs);
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, name, team, points, joueurs);
	}

	/**
	 * ajoute des points à l'équipe
	 * @param points
	 */
	public void addPoints(double points) {
		this.points += points;

		UpdateScoreBoard();
		gestionnaire.get().updateHolograms();
	}
	public void setPoints(double points){
		this.points = points;
	}
	/**
	 * met à jours le scoreboard pour tous les joueurs de l'équipe
	 */
	public void UpdateScoreBoard(){

		for(OfflinePlayer p: team.getPlayers()){
			if(p.isOnline()){
				gestionnaire.get().getJoueur(p.getPlayer()).updateScoreBoard();
			}
		}
	}

	/**
	 * donne les points de l'équipe
	 * @return
	 */
	public double getPoints() {
		return  points;
	}

	public List<Joueur> getPlayers() {
		List<Joueur> j = new LinkedList<>();
		for(UUID id : joueurs.keySet()){
			j.add(joueurs.get(id));
		}
		return j;
	}


}