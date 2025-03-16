package com.rrayy.BR.FightMode;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FightEventHandler implements Listener {
    private FightMode fightMod;

    public FightEventHandler(FightMode fightMod) {
        this.fightMod = fightMod;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player endamager = (Player) event.getEntity();
            event.setCancelled(fightMod.PvPbyAttack(damager, endamager));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        fightMod.initPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (fightMod.isFighting(event.getPlayer())) { //전투 중에 나가면 관전 모드로 탈락 처리
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (fightMod.isFighting(player)) { //사망한 플레이어가 전투 중이면 관전 모드로 탈락 처리
            event.setKeepInventory(false);
            player.setGameMode(GameMode.SPECTATOR);
        } else {
            // PlayerInventory inventory = player.getInventory();
            // ItemStack originalItem = inventory.getItemInMainHand();
            // inventory.setItemInMainHand(new ItemStack(Material.TOTEM_OF_UNDYING)); //불사의 토템 효과 주기
            // event.setKeepInventory(true);

            // player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 1200, 10)); // 1분간 움직이지 못하게 함
            // player.sendMessage(ChatColor.RED + "당신은 사망하여 1분간 움직일 수 없습니다.");
            // inventory.setItemInOffHand(originalItem);
        }
    }
}