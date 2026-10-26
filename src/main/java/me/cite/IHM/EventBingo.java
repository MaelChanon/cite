package me.cite.IHM;

import me.cite.gestionnaire.gestionnaire;
import me.cite.team.Equipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class EventBingo implements Listener {
    private static final int nbItems = 9;
    List<List<ItemStack>> items;
    double prixLigne;
    double prixColonnes;
    boolean[] doneItems = new boolean[9];
    private Map<Equipe, BingoEquipe> ihms;

    public EventBingo(List<List<Material>> materiels,int prixLigne,int prixColonnes ){
        for(int i = 0 ; i<9;++i){
            doneItems[i] = false;
        }
        items = new ArrayList<>();
        for(int i = 0; i<nbItems;++i){
            items.add(getCraftList(materiels.get(i)));
        }
        this.prixColonnes = prixColonnes;
        this.prixLigne = prixLigne;
        for(Equipe e : gestionnaire.get().getTeamList()){
            ihms.put(e,new BingoEquipe(items,doneItems.clone()));
        }
    }

    private List<ItemStack> getCraftList(List<Material> materials) {
        char[] chars = {'1','2','3','4','5','6','7','8','9'};
        List<ItemStack> lst = new ArrayList<>();
        ItemStack custom = new ItemStack(materials.get(0), 1);
        materials.remove(0);
        ItemMeta meta = custom.getItemMeta();

        meta.setLore(Arrays.asList("suivez Watibasile sur Twitch"));
        custom.setItemMeta(meta);
        ShapedRecipe recette = new ShapedRecipe(custom);
        Bukkit.getServer().addRecipe(recette);
        lst.add(custom);
        Material material;
        recette.shape("123","456","789");
        for(Integer i = 0;i<materials.size()-1;++i){
            material = materials.get(i+1);
            if(material == null)
                material = material.AIR;
            recette.setIngredient(chars[i],material);
            lst.add(new ItemStack(material));
        }
        return lst;
    }
}
