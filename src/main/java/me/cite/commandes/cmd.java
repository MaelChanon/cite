package me.cite.commandes;




import me.cite.Joueur.Joueur;
import me.cite.gestionnaire.gestionnaire;
import me.cite.shop.item;
import me.cite.team.Equipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;


public class cmd implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

		if(! (sender instanceof Player)){
			sender.sendMessage("vous devez être un joueur pour effectuer une commande");
			return false;
		}

		Joueur j = gestionnaire.get().getJoueur(((Player) sender).getPlayer());
		if(verifCMD(cmd,"enableHeadEvent",j)){
			return enableHeadEvent(args,sender);
		}
		if(verifCMD(cmd,"finirHeadEvent",j)){
			return finirHeadEvent(args,sender);
		}
		if(verifCMD(cmd,"setBlockPrice",j)){
			return setBlockPrice(args,sender);
		}
		if(verifCMD(cmd,"enableRanking",j)){
			return enableRanking(args,sender);
		}
		if(verifCMD(cmd,"getMostSolded",j)){

			return getMostSolded(args,sender);
		}
		if(verifCMD(cmd,"disableRanking",j)){
			return disableRanking(args,sender);
		}
		if(verifCMD(cmd,"enablePVP",j)){
			return enablePVP(args,sender);
		}
		if(verifCMD(cmd,"disablePVP",j)){
			return disablePVP(args,sender);
		}

		if(verifCMD(cmd,"enableShop",j)){
			return enableShop(args,sender);
		}
		if(verifCMD(cmd,"disableShop",j)){
			return disableShop(args,sender);
		}
		if(verifCMD(cmd ,"addPoints",j)){
			return addPoints(args,sender);
		}
		if(cmd.getName().equalsIgnoreCase("spawn")){
			return spawn(args,sender,j);
		}
		if(verifCMD(cmd,"mute",j)){
			return mute(args,sender);
		}
		if(verifCMD(cmd,"demute",j)){
			return demute(args,sender);
		}
		if(verifCMD(cmd,"changePrices",j)){
			return changePrices(args,sender);
		}
		if(verifCMD(cmd,"startHeadEvent",j)){
			return startHeadEvent(args,sender);
		}
		return false;
	}

	private boolean finirHeadEvent(String[] args, CommandSender sender) {
		if(args.length != 0){
			sender.sendMessage("pas besoin d'arguments");
		}
		return gestionnaire.get().finirHeadEvent();
	}

	private boolean startHeadEvent(String[] args, CommandSender sender) {
		if(args.length != 0){
			sender.sendMessage("pas besoin d'arguments");
		}
		return gestionnaire.get().startHeadEvent();
	}

	private boolean setBlockPrice(String[] args, CommandSender sender) {
		if(args.length != 1 ){
			sender.sendMessage("Mauvaise execution de la commande --> /setBlockPrice [prix]");
			return false;
		}
		final Player p = (Player) sender;
		final ItemStack it = p.getItemInHand();

		if(it == null){
			sender.sendMessage("vous devez avoir un objet dans la main");
			return false;
		}
		double nb;
		try{
			nb = Double.valueOf(args[0]);
		}
		catch (NumberFormatException e){
			sender.sendMessage("la valeur saisie n'est pas un nombre");
			return false;
		}
		ItemMeta meta = it.getItemMeta();
		meta.setLore(Arrays.asList(nb +"$"));
		it.setItemMeta(meta);
		return gestionnaire.get().addBlockHeadEvent(it,nb);
	}

	private boolean enableHeadEvent(String[] args, CommandSender sender) {
		return gestionnaire.get().enableHeadEvent();
	}

	private boolean changePrices(String[] args, CommandSender sender) {
		if(args.length != 2 ){
			sender.sendMessage("Mauvaise execution de la commande --> /changePrices [nom Item] [prix]");
			return false;
		}
		try{
			Material m = Material.valueOf(args[0]);
			double nb = Double.valueOf(args[1]);
			if(!gestionnaire.get().getShops().changePrice(m,nb))
				throw new RuntimeException();
		}
		catch (NumberFormatException e){
			sender.sendMessage("la valeur saisie n'est pas un nombre");
			return false;
		}
		catch (Exception e){
			sender.sendMessage("l'item n'existe pas");
		}
		return true;

	}

	private boolean getMostSolded(String[] args, CommandSender sender) {
		System.out.println("trop zaeb");
		if(args.length != 0){
			sender.sendMessage("Pas besoin d'arguments");
			return false;
		}
		for(item it : gestionnaire.get().getShops().getItems()){
			System.out.println(it.getMaterial()+ " : "+ it.getCount() +" = "+it.getCount()*it.getPrice());
		}
		return true;
	}

	private boolean demute(String[] args, CommandSender sender) {
		if(args.length !=1){
			sender.sendMessage("Mauvaise execution de la commande --> /demute [nom du joueur]");
			return false;
		}
		Joueur j;
		try{
			j = gestionnaire.get().getJoueur(Bukkit.getPlayer(args[0]));
		}
		catch(Exception e){
			sender.sendMessage("Le joueur n'existe pas ");
			return false;
		}
		j.demute();
		return true;
	}

	private boolean mute(String[] args, CommandSender sender) {
		if(args.length !=1){
			sender.sendMessage("Mauvaise execution de la commande --> /mute [nom du joueur]");
			return false;
		}
		Joueur j;
		try{
			j = gestionnaire.get().getJoueur(Bukkit.getPlayer(args[0]));
		}
		catch(Exception e){
			sender.sendMessage("Le joueur n'existe pas ");
			return false;
		}
		j.mute();
		return true;
	}

	private boolean addPoints(String[] args, CommandSender sender) {
		if(args.length != 2 ){
			sender.sendMessage("Mauvaise execution de la commande --> /addPoints [nom d'equipe/nom du joueur] [nombre de points]");
			return false;
		}
		Equipe equipe = gestionnaire.get().getTeam(args[0]);
		Player p = Bukkit.getPlayer(args[0]);

		if(equipe == null && p == null){
			sender.sendMessage("l'equipe ou le joueur n'existe pas");
			return false;
		}
		double nb;
		try{
			nb = Double.valueOf(args[1]);
		}
		catch (NumberFormatException e){
			sender.sendMessage("la valeur saisie n'est pas un nombre");
			return false;
		}
		if(equipe !=null){
			equipe.addPoints(nb);
		} else if (p != null) {
			Joueur j = gestionnaire.get().getJoueur(p);
			j.addPoints(nb);
		}
		return true;
	}

	private boolean spawn(String[] args, CommandSender sender,Joueur j) {
		if(args.length != 0){
			sender.sendMessage("Pas besoin d'arguments");
			return false;
		}
		if(!j.isInSpawn()){
			sender.sendMessage("Vous ne pouvez effectuer cette commande que dans le spawn");
			return false;
		}
		j.toSpawn();
		return true;
	}

	private boolean enablePVP(String[] args, CommandSender sender) {
		if(args.length != 0){
			sender.sendMessage("Pas besoin d'arguments");
			return false;
		}
		gestionnaire.get().setPvp(true);
		return true;
	}

	private boolean disablePVP(String[] args, CommandSender sender) {
		if(args.length != 0){
			sender.sendMessage("Pas besoin d'arguments");
			return false;
		}
		gestionnaire.get().setPvp(false);
		return true;
	}

	private boolean enableShop(String[] args, CommandSender sender) {
		if(args.length != 0){
			sender.sendMessage("Pas besoin d'arguments");
			return false;
		}
		gestionnaire.get().setShops(true);
		return true;
	}

	private boolean disableShop(String[] args, CommandSender sender) {
		if(args.length != 0){
			sender.sendMessage("Pas besoin d'arguments");
			return false;
		}
		gestionnaire.get().setShops(false);
		return true;
	}

	private boolean verifCMD(Command cmd,String message,Joueur j){
		return cmd.getName().equalsIgnoreCase(message) && j.isOP();
	}

	private boolean enableRanking(String[] args,CommandSender sender){
		if(args.length != 0){
			sender.sendMessage("Pas besoin d'arguments");
			return false;
		}
		gestionnaire.get().enableRanking();
		return true;
	}

	private boolean disableRanking(String[] args,CommandSender sender){
		if(args.length != 0){
			sender.sendMessage("Pas besoin d'arguments");
			return false;
		}
		gestionnaire.get().disableRanking();
		return true;
	}
}
