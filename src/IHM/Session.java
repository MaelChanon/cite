package IHM;

import Joueur.Joueur;

public class Session {
	private Joueur j;
	private Ihm ihm;
	private int page;
	
	public Session(Ihm ihm,Joueur j ) {
		this.j = j;
		this.ihm = ihm;
		page = 0;
	}
	public void suivant() {
		if (ihm.getNbPages() == page+1)
			return;
		++page;
		
	}
	public void precedent() {
		if(page == 0)return;
		page --;
	}
	public void ouvrir() {
		ihm.openInventory(j.getPlayer(), page);
	}
}
