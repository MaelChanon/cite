package commandes;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import shop.shopBuilder;
import shop.shops;

public class cmd implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender.isOp() && cmd.getName().equalsIgnoreCase("test") && sender instanceof Player) {
			Player p = (Player) sender;
			shops.get().add(new shopBuilder( p.getLocation(), "slt"));
			return true;
		}
		return false;
	}
}
