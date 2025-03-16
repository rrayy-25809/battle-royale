package com.rrayy.BR.FightMode;

import java.util.ArrayList;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import com.rrayy.BR.BR;

import net.md_5.bungee.api.ChatColor;

public class FightMode {
    private ArrayList<Player> playerList = new ArrayList<>();
    private BossBar fightBar;
    private BossBar notFightBar;
    private BR main;
    public FightEventHandler Event;

    public FightMode(BR plugin) {
        this.main = plugin;
        this.fightBar = plugin.getServer().createBossBar("현재 PvP 모드 상태입니다.", BarColor.RED, BarStyle.SOLID);
        this.notFightBar = plugin.getServer().createBossBar("현재 PvP 모드 상태가 아닙니다.", BarColor.GREEN, BarStyle.SOLID);
        this.Event = new FightEventHandler(this);
        startLoop();
    }

    private void startLoop() {
        long second = 20L;
        main.getServer().getScheduler().scheduleSyncRepeatingTask(main, () -> {
            for (Player player : playerList) {
                player.sendMessage(net.md_5.bungee.api.ChatColor.GREEN + "PvP 모드가 해지되었습니다.");
                removePlayer(player);
            }
        }, 1, 20 * second);
    }

    public boolean isFighting(Player player) {
        return playerList.contains(player);
    }

    public void addPlayer(Player player) {
        playerList.add(player);
        notFightBar.removePlayer(player);
        fightBar.addPlayer(player);
    }

    public void removePlayer(Player player) {
        fightBar.removePlayer(player);
        playerList.remove(player);
        notFightBar.addPlayer(player);
    }

    public void initPlayer(Player player) {
        notFightBar.addPlayer(player);
    }

    public boolean PvPbyAttack(Player damager, Player endamager){
        if (!isFighting(damager) && !isFighting(endamager)) { //플레이어끼리의 공격 이벤트 핸들링
            addPlayer(endamager);
            addPlayer(damager);
            endamager.sendMessage(ChatColor.GREEN + "플레이어 " + ChatColor.RED + damager.getDisplayName() + ChatColor.GREEN + "에게 피해를 입었습니다. PvP 모드로 전환됩니다.");
            damager.sendMessage(ChatColor.GREEN + "플레이어 " + ChatColor.RED + endamager.getDisplayName() + ChatColor.GREEN + "에게 피해를 입혔습니다. PvP 모드로 전환됩니다.");
            return true;
        }
        return false;
    }
}