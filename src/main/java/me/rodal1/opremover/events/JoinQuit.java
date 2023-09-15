package me.rodal1.opremover.events;

import me.rodal1.opremover.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.Objects;

public class JoinQuit implements Listener {

    Main pl = Main.getPlugin();

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        p(e.getPlayer());
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        p(e.getPlayer());
    }

    private void p(Player p) {
        List<String> Address = pl.getConfig().getStringList("IPAddressList");
        List<String> OpPlayers = pl.getConfig().getStringList("OpPlayerList");
        if ((p.isOp() && OpPlayers.contains(p.getName())) || (p.isOp() && Address.contains(Objects.requireNonNull(p.getAddress()).getHostString()))) {
            p.setOp(false);
            if (pl.getConfig().getBoolean("UseGamemode")) {
                p.setGameMode(GameMode.valueOf(pl.getConfig().getString("Gamemode")));
            }
            if (pl.getConfig().getBoolean("LogToConsole")) {
                pl.getLogger().info(Main.color(p, Objects.requireNonNull(pl.getConfig().getString("ConsoleLog")).replace("%p%", p.getName())));
            }
        }
    }
}
