package me.rodal1.opremover.commands;

import me.rodal1.opremover.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Reload implements CommandExecutor {

    Main pl = Main.getPlugin();
    Player p;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("opremover.reload")) {
            sender.sendMessage(Main.color(p, Objects.requireNonNull(pl.getConfig().getString("NoPermission"))));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(Main.color(p, "&8&m-------------&8[&cOpRemover v" + pl.getDescription().getVersion() + "&8]&m-------------\n" +
                    "&e/opremover - Shows this list of commands.\n" +
                    "&e/opremover <reload | rl> - Reloads the config.\n" +
                    "&e/oprecovery <password> - Recover Op status.\n" +
                    "&8&m----------------------------------------"));
            return true;
        }
        if (args.length == 1 &&
                args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
            pl.reloadConfig();
            sender.sendMessage(Main.color(p, Objects.requireNonNull(pl.getConfig().getString("ReloadMsg"))));
            return true;
        }
        if (args.length > 1) {
            sender.sendMessage(Main.color(p, Objects.requireNonNull(pl.getConfig().getString("CommandUse"))));
        }
        return false;
    }
}