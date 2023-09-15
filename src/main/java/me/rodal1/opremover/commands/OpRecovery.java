package me.rodal1.opremover.commands;

import me.rodal1.opremover.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class OpRecovery implements CommandExecutor {

    Main pl = Main.getPlugin();
    Player p;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> OpPlayers = pl.getConfig().getStringList("OpPlayerList");
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is only for players!");
            return true;
        }
        if (sender.isOp()) {
            sender.sendMessage(Main.color(p, Objects.requireNonNull(pl.getConfig().getString("AlreadyOp"))));
            return true;
        }
        if (!OpPlayers.contains(sender.getName())) {
            sender.sendMessage(Main.color(p, Objects.requireNonNull(pl.getConfig().getString("NoPermission"))));
        } else {
            if (!pl.getConfig().getBoolean("UsePassword")) {
                sender.sendMessage(Main.color(p, Objects.requireNonNull(pl.getConfig().getString("OptionDisabled"))));
            } else {
                if (args.length == 0) {
                    sender.sendMessage(Main.color(p, Objects.requireNonNull(pl.getConfig().getString("PasswordUse"))));
                    return true;
                }
                if (args.length == 1) {
                    if (!args[0].equals((pl.getConfig().getString("RecoveryPassword")))) {
                        sender.sendMessage(Main.color(p, Objects.requireNonNull(pl.getConfig().getString("IncorrectPassword"))));
                    } else {
                        sender.setOp(true);
                        sender.sendMessage(Main.color(p, Objects.requireNonNull(pl.getConfig().getString("OpRecovered"))));
                        pl.getLogger().info(Main.color(p, Objects.requireNonNull(pl.getConfig().getString("RecoveredLog")).replace("%p%", sender.getName())));
                    }
                }
            }
        }
        return true;
    }
}
