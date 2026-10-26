package scoreBoard;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;

public class scoreboard extends BukkitRunnable {
	private boolean titlechanged = false;
	@Override
	public void run() {
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try {
            Field a = packet.getClass().getDeclaredField("a");
            a.setAccessible(true);
            Field b = packet.getClass().getDeclaredField("b");
            b.setAccessible(true);

            Object header1 = new ChatComponentText("§cBANKAI UHC\n");
            Object header2 = new ChatComponentText(ChatColor.GOLD + "BANKAI UHC\n");

            Object footer = new ChatComponentText("\n§cPlayers Online: §f" + Bukkit.getServer().getOnlinePlayers().size() + ChatColor.GRAY + "\nby Milou06 and OhNomNom");
            
            if (titlechanged) {
                a.set(packet, header2);
                titlechanged = false;

            } else {
                a.set(packet, header1);
                titlechanged = true;
            }
            b.set(packet, footer);
            
            if (Bukkit.getOnlinePlayers().size() == 0) return;
            for (Player player : Bukkit.getOnlinePlayers()) {
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
	}

}
