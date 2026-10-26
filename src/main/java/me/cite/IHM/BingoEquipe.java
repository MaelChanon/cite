package me.cite.IHM;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BingoEquipe {
    Inventory objets;
    List<Inventory> craft;
    boolean[] valide;
    private bingoIHM ihm;

    public BingoEquipe(List<List<ItemStack>> items, boolean[] clone) {
        valide = clone;
        ihm = new bingoIHM(items);
    }

    public void validerCase(int numero){}
    public boolean isBingo(boolean[] tab,int numero){
        return lineVictory(tab,numero) || rawVictory(tab,numero) || diagVictory(tab,numero);
    }

    private boolean diagVictory(boolean[] tab,int numero) {
        return  verifLines(tab,-1,-1,numero/3,numero%3);
    }

    private boolean rawVictory(boolean[] tab,int numero) {
        return  verifLines(tab,0,-1,numero/3,numero%3);
    }

    private boolean lineVictory(boolean[] tab,int numero) {
        return  verifLines(tab,1,0,numero/3,numero%3);
    }

    private boolean verifLines(boolean[] tab,int offsetX, int offsetY,int x,int y){
        boolean lineFinished = true;
        int count = 0;
        while(x>0 && x<2 && y>0 && y<2){
            x+= offsetX;
            y+= offsetY;
        }
        while(x>0 && x<2 && y>0 && y<2){
            ++count;
            if(!tab[x*3+y])
                lineFinished = false;
            if(!valide[x*3+y])
                return false;
            x+= offsetX;
            y+= offsetY;
        }
        return count== 3 && !lineFinished;
    }
}
