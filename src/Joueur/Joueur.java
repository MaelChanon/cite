package Joueur;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import team.Equipe;

public class Joueur {
	
	private UUID id;
	private int kill;
	private boolean alive;
	Equipe team = null;
	double damage;
	public Joueur(UUID id, Equipe equipe) {
		this.id = id;
		kill = 0;
		alive = true;
		team = equipe;
		damage = 0;
	}
	public int getKill() {
		return kill;
	}
	public void incrKill() {
		kill++;
	}
	public boolean isAlive() {
		return alive;
	}
	public void revive() {
		alive = true;
	}
	public void die() {
		alive = false;
	}
	
	public UUID getUUID() {
		return id;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Joueur other = (Joueur) obj;
		if (alive != other.alive)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kill != other.kill)
			return false;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}
	public void removeTeam() {
		team = null;
		
	}
	public boolean hasTeam() {
		return team != null;
	}
	
	public Equipe getTeam() {
		return team;
	}
	public void addDamage(double damage) {
		this.damage += damage;
		
	}
	public void addTeam(Equipe equipe) {
		this.team = equipe;
	}
	public double getDamage() {
		return damage;
	}
	public void kill() {
		alive = false;
		
	}
	public String getName() {
		return Bukkit.getPlayer(id).getDisplayName();
	}
	public Player getPlayer() {
		return Bukkit.getPlayer(id);
		
	}
}
