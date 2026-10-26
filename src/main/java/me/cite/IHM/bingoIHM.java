package me.cite.IHM;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class bingoIHM {
    private Inventory objets;
    List<Inventory> craft;

    public bingoIHM(List<List<ItemStack>> items){
        objets = getClearInventory();
        Inventory inv;
        for(int i = 0;i<9; ++i){
            objets.addItem(items.get(i).get(0));
            inv = getClearInventory();
            inv.setItem(26,specificItems.RETOUR.getIt());
            for(int j = 0; j<9;++j){
                inv.addItem(items.get(i).get(j+1));
            }
            craft.add(inv);
        }
    }

    private Inventory getClearInventory(){

        Inventory inv;
        inv = Bukkit.createInventory(null,27);
        for(int i = 0 ; i<3;++i){
            for(int j =0 ;j<9;++j){
                if(j <3 ||j>6)
                    inv.setItem(i*9+j,specificItems.FILLITEM.getIt());
            }
        }
        return inv;
    }

    public void validerItem(int numero){
        int num = -1;
        ItemStack itemStack;
        for(int i = 0 ; i<objets.getSize(); ++i){
            if(num == numero)
                objets.setItem(i, new ItemStack(Material.GREEN_WOOL));
            itemStack = objets.getItem(i);
            if(itemStack == null || itemStack.equals(specificItems.FILLITEM.getIt()))
                continue;
            num++;
        }
    }
    public int getNumero(ItemStack itemStack){
        int num = 0;
        ItemStack current;
        for(int i = 0 ; i<objets.getSize(); ++i){
            current = objets.getItem(i);
            if(current == null || current.equals(specificItems.FILLITEM.getIt()))
                continue;
            if(itemStack.equals(current))
                return num;
            num++;

        }
        return -1;
    }
    public void openMainInventory(Player p){
        p.openInventory(objets);
    }
    public void openCraftInventory(Player p,ItemStack it){
        p.openInventory(craft.get(getNumero(it)));
    }

}
