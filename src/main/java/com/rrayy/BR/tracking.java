package com.rrayy.BR;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.Particle;
import org.bukkit.Material;

public class tracking implements Listener {
    private final BR plugin;

    public tracking(BR plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Handle player join event here
        plugin.getLogger().info(event.getPlayer().getName() + " has joined the game!");
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        
        // 다이아몬드를 클릭했을 때
        if (event.getItem() != null && event.getItem().getType() == Material.DIAMOND) {
            // 플레이어와 가장 가까운 다른 플레이어를 찾기
            Player closestPlayer = getClosestPlayer(player);
            if (closestPlayer != null) {
                player.sendMessage("가장 가까운 플레이어: " + closestPlayer.getName());
                closestPlayer.sendMessage(player.getName() + "님이 다이아몬드를 클릭했습니다!");

                Vector direction = closestPlayer.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
                closestPlayer.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, closestPlayer.getLocation(), 10, direction.getX(), direction.getY(), direction.getZ(), 0.1);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1000.0F, 1.0F);
            } else {
                player.sendMessage("주변에 다른 플레이어가 없습니다.");
            }
        } else {
            player.sendMessage("다이아몬드를 클릭해주세요!");
        }
    }

    private Player getClosestPlayer(Player player) {
        Player closestPlayer = null;
        double closestDistance = Double.MAX_VALUE;
        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
            if (onlinePlayer != player) { // 자신을 제외한 다른 플레이어만
                double distance = player.getLocation().distance(onlinePlayer.getLocation());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestPlayer = onlinePlayer;
                }
            }
        }
        return closestPlayer;
    }
}
