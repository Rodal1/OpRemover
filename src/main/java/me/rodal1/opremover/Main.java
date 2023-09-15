package me.rodal1.opremover;

import me.clip.placeholderapi.PlaceholderAPI;
import me.rodal1.opremover.bstats.MetricsLite;
import me.rodal1.opremover.commands.OpRecovery;
import me.rodal1.opremover.commands.Reload;
import me.rodal1.opremover.config.ConfigUpdater;
import me.rodal1.opremover.events.JoinQuit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        int oldVersion = 1;
        new ConfigUpdater(this).checkUpdate(oldVersion);
        Bukkit.getPluginManager().registerEvents(new JoinQuit(), this);
        Objects.requireNonNull(Bukkit.getPluginCommand("oprecovery")).setExecutor(new OpRecovery());
        Objects.requireNonNull(Bukkit.getPluginCommand("opremover")).setExecutor(new Reload());
        int pluginId = 12773;
        MetricsLite metrics = new MetricsLite(this, pluginId);
        getLogger().info("Has been enabled (Version v" + getDescription().getVersion() + ")");
        getLogger().info("Made by Rodal1");
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("Has been disabled (Version v" + getDescription().getVersion() + ")");
        getLogger().info("Made by Rodal1");
    }

    public static Main getPlugin() {
        return getPlugin(Main.class);
    }
    public static String color(Player p, String s) {
        return PlaceholderAPI.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', s));
    }
}
