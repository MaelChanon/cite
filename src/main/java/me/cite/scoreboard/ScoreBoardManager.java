package me.cite.scoreboard;

import me.cite.Joueur.Joueur;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreBoardManager {

    private Scoreboard scoreboard;
    Objective objective;
    private Score teamPoints;
    private Score l1;
    private Score l5;
    private Score team;
    private Score player_money;
    private Score team_money;

    public ScoreBoardManager(Joueur j){
        initScoreBoard(j);
    }
    public void updateScoreBoard(Joueur j){
        initScoreBoard(j);
    }
    private void initScoreBoard(Joueur j){
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("general", "dummy");

        objective.setDisplayName(ChatColor.GOLD + " ASCENSION V2 ");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score l1 = objective.getScore(ChatColor.RED + "---------------------------" + ChatColor.BLACK + "");
        Score l5 = objective.getScore(ChatColor.RED + "---------------------------");
        Score team = objective.getScore(ChatColor.GOLD + "->" + ChatColor.WHITE  + " Team "+j.getTeam().getColor()+j.getTeam().getName());
        player_money = objective.getScore(ChatColor.GOLD + "->" + ChatColor.WHITE+ " Player Money "+ChatColor.AQUA+ j.getPoints() + " $");
        team_money = objective.getScore(ChatColor.GOLD + "->" + ChatColor.WHITE  + " Team Money "+ChatColor.AQUA+ j.getTeam().getPoints()+ " $");

        l1.setScore(4);
        team.setScore(3);
        player_money.setScore(2);
        team_money.setScore(1);
        l5.setScore(0);
    }


    public void launch(Player player) {
        player.setScoreboard(scoreboard);
    }
}
