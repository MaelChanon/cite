package me.cite.shop;

import org.bukkit.Material;


public class item implements Comparable<item> {
    private Material material;
    private double price;
    private int numberSell;

    public item(Material material,double price,int numberSell){
        this.material = material;
        this.price = price;
        this.numberSell = numberSell;
    }

    public Material getMaterial() {
        return this.material;
    }

    public double getPrice() {
        return this.price;
    }

    public void addCount(int count) {
        this.numberSell += count;

    }

    public int getCount() {
        return this.numberSell;
    }


    @Override
    public int compareTo( item o) {
        return o.getCount() - this.getCount();
    }

    public void setPrice(double nb) {
        price = nb;
    }
}
