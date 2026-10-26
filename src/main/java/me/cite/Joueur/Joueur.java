package me.cite.Joueur;

import java.util.UUID;

import me.cite.gestionnaire.gestionnaire;
import me.cite.scoreboard.ScoreBoardManager;
import me.cite.team.Equipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;


public class Joueur {
	private UUID id;
	private int kill;
	private int death;
	private boolean alive;
	Equipe team = null;
	double points;
	boolean op;
	boolean isMuted;
	private ScoreBoardManager scoreBoardManager;
	public Joueur(UUID id, Equipe equipe,Boolean isOP) {
		this.op = isOP;
		this.id = id;
		kill = 0;
		death = 0;
		alive = true;
		team = equipe;
		points = 0;
		isMuted = false;
		scoreBoardManager = new ScoreBoardManager(this);
		scoreBoardManager.updateScoreBoard(this);
	}
	public int getKill() {
		return kill;
	}
	public double getPoints(){return  points;}
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

		public void addTeam(Equipe equipe) {
			this.team = equipe;
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

	public int sellALl(Material item, double price) {
		Player p = Bukkit.getPlayer(id);
		PlayerInventory inv = p.getInventory();
		ItemStack it;
		int total = 0;
		if(inv.contains(item))
		while(inv.contains(item)){
			it = inv.getItem(inv.first(item));
			total += it.getAmount();
			addPoints(it.getAmount()*price);
			inv.removeItem(it);
		}
		else{
			p.sendMessage("vous n'avez pas l'objet nécessaire");
		}
		return total;
	}

	public void sell(Material item ,double price) {
		Player p = Bukkit.getPlayer(id);
		PlayerInventory inv = p.getInventory();
		ItemStack it;
		if(inv.contains(item)){
			it = inv.getItem(inv.first(item));
			it.setAmount(it.getAmount()-1);
			addPoints(price);
		}
		else{
			p.sendMessage("vous n'avez pas l'objet nécessaire");
		}
	}
	public void launchScore(){
		scoreBoardManager.updateScoreBoard(this);
		scoreBoardManager.launch(getPlayer());
	}
	public void setKill(int kill) {
		this.kill = kill;
	}

	public void setDeath(int death) {
		this.death = death;
	}

	public void addPoints(double points) {
		this.points += points;
		team.addPoints(points);
	}

	public void updateScoreBoard() {
		scoreBoardManager.updateScoreBoard(this);
		scoreBoardManager.launch(getPlayer());
	}

    public int getDeath() {
		return death;
    }

	public boolean isOP() {
		return op;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

    public boolean isInSpawn() {
		return gestionnaire.get().isInSpawn(this);
    }

	public void toSpawn() {
		getPlayer().teleport(gestionnaire.get().getSpawn());
	}

    public void demute() {
		isMuted = false;
    }
	public void mute(){
		isMuted = true;
	}

	public boolean ismuted(){
		return isMuted;
	}

	public void incrDeath() {
		death += 1;
	}
}
