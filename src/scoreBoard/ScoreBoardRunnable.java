package scoreBoard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import net.md_5.bungee.api.ChatColor;

public class ScoreBoardRunnable extends BukkitRunnable {
     private Player p;

     public ScoreBoardRunnable(Player p) {

    	 this.p = p;

     }

	@Override
	public void run() {
		ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
	    Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
	    Objective objective = scoreboard.registerNewObjective("general", "dummy");
        objective.setDisplayName(ChatColor.GOLD + "     BANKAI UHC     ");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        final Score l1 = objective.getScore("");
        final Score l2 = objective.getScore(" ");
        final Score l3 = objective.getScore("  ");
        final Score l4 = objective.getScore("   ");
        final Score l5 = objective.getScore("    ");
        final Score role = objective.getScore("Role");
        final Score Joueur = objective.getScore("Joueur");
        final Score Kills = objective.getScore("Kill");
        
        l1.setScore(8);
        role.setScore(7);
        l2.setScore(6);

        l3.setScore(4);
        Joueur.setScore(3);
        l4.setScore(2);
        Kills.setScore(1);
        l5.setScore(0);
        p.setScoreboard(scoreboard);
		if(!p.isOnline())
			return;

	}

}
