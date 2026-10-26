package me.cite.IHM;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum specificItems {
    FILLITEM(Material.BLACK_STAINED_GLASS_PANE,""),
    APRESITEM(Material.ARROW,"page Suivante"),
    AVANTITEM(Material.ARROW,"Page Précedante"),
    TOUTPRENDRE(Material.CHEST, "Tout vendre"),

    RETOUR(Material.BARRIER, "retour");

    private ItemStack it;
    private specificItems(Material m, String name){
        it = new ItemStack(m);
        ItemMeta meta = it.getItemMeta();
        meta.setDisplayName(name);
        it.setItemMeta(meta);
    }
    public boolean equals(ItemStack s){
        return it.equals(s);
    }

        public ItemStack getIt() {
        return it;
    }
}
