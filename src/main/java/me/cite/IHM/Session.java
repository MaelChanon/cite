package me.cite.IHM;


import me.cite.Joueur.Joueur;
import org.bukkit.Material;

public class Session {
	private boolean switching;
	private Joueur j;

	private int page;

	public Session(ShopIHM ihm, Joueur j ) {
		this.j = j;

		page = 0;
	}
	public void suivant() {
		++page;
		setSwitching(true);
	}
	public void precedent() {
		page --;
		setSwitching(true);
	}



	public void sell(Material m, double price){
		j.sell(m,price);
	}
	public int sellAll(Material m,double price){
		return  j.sellALl(m,price);
	}

	public int getPage() {
		return page;
	}


	public void setSwitching(boolean b){
		switching = b;
	}
	public boolean isSwitching(){
		return switching;
	}

}
