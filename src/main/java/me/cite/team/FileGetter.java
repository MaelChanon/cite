package me.cite.team;

import me.cite.Joueur.Joueur;
import me.cite.claim.Claim;
import me.cite.gestionnaire.gestionnaire;
import me.cite.prok.Bankal;
import me.cite.shop.item;
import me.cite.shop.shopBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileGetter {
    private final gestionnaire gestionnaire;



    public FileGetter(gestionnaire g){
        gestionnaire = g;
    }

    public void loadTeams() {
        Equipe e;
        Joueur j;

        File file = new File(Bankal.getBankal().getDataFolder(), "team.yml");
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (String KeyTeam : config.getKeys(false)) {
            e = new Equipe((String) config.get(KeyTeam + ".Name"), ChatColor.valueOf((String) config.get(KeyTeam + ".Color")), (Boolean) config.get(KeyTeam+".spectator"));
            e.setPoints((Double) config.get(KeyTeam + ".Points"));

            gestionnaire.addTeam(e);

            for (String KeyPlayer : config.getConfigurationSection(KeyTeam + ".Players").getKeys(false)) {
                j = new Joueur(UUID.fromString((String) config.getConfigurationSection(KeyTeam + ".Players." + KeyPlayer).get("UUID")), e, (Boolean) config.getConfigurationSection(KeyTeam + ".Players." + KeyPlayer).get("IsOP"));
                j.setKill((Integer) config.getConfigurationSection(KeyTeam + ".Players." + KeyPlayer).get("Kill"));
                j.setDeath((Integer) config.getConfigurationSection(KeyTeam + ".Players." + KeyPlayer).get("Death"));
                j.setPoints((Double) config.getConfigurationSection(KeyTeam + ".Players." + KeyPlayer).get("Points"));
                gestionnaire.addPlayer(j);
                e.ajouterJoueur(j);
            }
        }

    }

    public void saveTeams() {
        File file = new File(Bankal.getBankal().getDataFolder(), "team.yml");
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        List<Equipe> lst = new LinkedList<>();
        List<Joueur> joueurs;
        lst.addAll(gestionnaire.getTeamList());
        Joueur j;
        for (String keyTeam : config.getKeys(false)) {
            config.getConfigurationSection(keyTeam).set("Points", lst.get(0).getPoints());
            joueurs = lst.get(0).getPlayers();


            for (String KeyPlayer : config.getConfigurationSection(keyTeam + ".Players").getKeys(false)) {
                for (int i = 0; i < joueurs.size(); ++i) {
                    j = joueurs.get(i);
                    if (j.getUUID().equals(UUID.fromString((String) config.getConfigurationSection(keyTeam + ".Players." + KeyPlayer).get("UUID")))) {
                        config.getConfigurationSection(keyTeam + ".Players." + KeyPlayer).set("Kill", j.getKill());
                        config.getConfigurationSection(keyTeam + ".Players." + KeyPlayer).set("Points", j.getPoints());
                        config.getConfigurationSection(keyTeam + ".Players." + KeyPlayer).set("Death", j.getDeath());
                        joueurs.remove(j);
                    }
                }
            }
            lst.remove(0);
        }
        try {
            config.save(file);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    public void saveItems(){
        File file = new File(Bankal.getBankal().getDataFolder(), "shop.yml");
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        List<shopBuilder> lst = gestionnaire.getShops().getshopBuilders();
        for(shopBuilder shop : lst){
            for(item it : shop.getItems()){

                config.getConfigurationSection(shop.getName()+".items."+it.getMaterial()).set("price", it.getPrice());
                config.getConfigurationSection(shop.getName()+".items."+it.getMaterial()).set("count", it.getCount());
            }
        }
        try {
            config.save(file);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    public void loadClaims() {
        File file = new File(Bankal.getBankal().getDataFolder(),"Claims.yml" );
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        Claim c;
        Equipe e;
        int x1 = 0;
        int x2 = 0;
        int y1 = 0;
        int y2 = 0;
        for(String keyClaim : config.getKeys(false)){
            e = gestionnaire.getTeam((String) config.getConfigurationSection(keyClaim).get("equipe"));
            x1 = (int) config.getConfigurationSection(keyClaim+".point1").get("X");
            x2 = (int) config.getConfigurationSection(keyClaim+".point2").get("X");
            y1 = (int) config.getConfigurationSection(keyClaim+".point1").get("Y");
            y2 = (int) config.getConfigurationSection(keyClaim+".point2").get("Y");
            c = new Claim(x1, y1,x2,y2,e);
            gestionnaire.addClaim(c);
        }
    }
    public void loadShops(){
        File file = new File(Bankal.getBankal().getDataFolder(),"shop.yml" );
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        Location l;
        shopBuilder s;
        item it;
        for(String KeyPNJ : config.getKeys(false)) {
            l = new Location(Bankal.getWorld(),(double)config.get(KeyPNJ+".location.getX"),(double)config.get(KeyPNJ+".location.getY"),(double)config.get(KeyPNJ+".location.getZ"));
            s = new shopBuilder( l, KeyPNJ);
            for(String KeyItem : config.getConfigurationSection(KeyPNJ+".items").getKeys(false)) {
                it = new item(Material.matchMaterial(KeyItem),(double) config.getConfigurationSection(KeyPNJ+".items."+KeyItem).get("price"), (Integer) config.getConfigurationSection(KeyPNJ+".items."+KeyItem).get("count"));
                if(it == null){
                    System.out.println("CECI EST UN CONTROLE DE POLICE : "+KeyItem);
                }
                s.addItem(it);
            }
            s.enable();
            gestionnaire.ajouterShop(s);
        }
    }
    public void loadConfiguration(){
        File file = new File(Bankal.getBankal().getDataFolder(),"configuration.yml" );
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        Location l = new Location(Bankal.getWorld(), (double) config.get("Score.X"),(double) config.get("Score.Y"),(double) config.get("Score.Z"));
        gestionnaire.setPvp((Boolean) config.get("pvpEnabled"));
        gestionnaire.setShops((Boolean) config.get("pnjEnabled"));
        gestionnaire.LoadHolograms(l);
        if(!(Boolean) config.get("scoreEnabled"))
            gestionnaire.disableRanking();
        Location spawn = new Location(Bankal.getWorld(), (double) config.get("Spawn.X"),(double)config.get("Spawn.Y"),(double) config.get("Spawn.Z"));
        gestionnaire.setSpawn(spawn);
    }
    public void saveConfiguration(){
        File file = new File(Bankal.getBankal().getDataFolder(),"configuration.yml" );
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("pvpEnabled", gestionnaire.isPvpEnabled());
        config.set("pnjEnabled", gestionnaire.isShopEnabled());
        config.set("scoreEnabled", gestionnaire.isRankingEnabled());
        try {
            config.save(file);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void load() {
        loadTeams();
        loadClaims();
        loadShops();
        loadConfiguration();
    }
}
