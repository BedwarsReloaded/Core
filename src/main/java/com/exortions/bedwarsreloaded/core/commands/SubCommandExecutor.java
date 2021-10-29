package com.exortions.bedwarsreloaded.core.commands;

import com.exortions.bedwarsreloaded.core.BedwarsReloadedPlugin;
import com.exortions.bedwarsreloaded.core.util.ArrayUtils;
import com.exortions.bedwarsreloaded.core.util.Chat;
import lombok.Data;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@Data
public class SubCommandExecutor<T extends BedwarsReloadedPlugin> implements CommandExecutor {

    protected T plugin;

    protected List<ISubCommand> commands;

    protected Consumer<CommandSender> players;
    protected Consumer<CommandSender> permission;
    protected Consumer<CommandSender> arguments;
    protected Consumer<CommandSender> found;

    public SubCommandExecutor(@NotNull T plugin) {
        this.plugin = plugin;

        commands = new ArrayList<>();

        setPlayers(sender -> sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "Only players can execute this command!"));
        setPermission(sender -> sender.sendMessage(ChatColor.RED + "You don't have permission to execute this command!"));

        setArguments(sender -> {
            String msg = Chat.colorize(plugin.getPrefix() + "Running &3" + plugin.getFullName() + "&f.\n");
            AtomicBoolean perms = new AtomicBoolean(false);
            commands.forEach(cmd -> perms.set(sender.hasPermission(cmd.permission()) || perms.get()));
            sender.sendMessage(perms.get() ? msg.concat(Chat.colorize("&3 > &fType /bedwarsreloaded help for a list of commands.")) : msg.concat(Chat.colorize("&3 > &fYou do not have permission to use any commands.")));
        });

        setFound(sender -> sender.sendMessage(Chat.colorize("&cCommand not found! Type /bedwarsreloaded help for a list of commands.")));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            arguments.accept(sender);
            return true;
        }

        for (ISubCommand cmd : commands) {
            if (!args[0].equals(cmd.name())) continue;
            if (!sender.hasPermission(cmd.permission())) { permission.accept(sender); return true; }
            args = args.length > 1 ? ArrayUtils.subArray(args) : new String[0];
            if (cmd.requiresPlayer()) if (sender instanceof Player) cmd.execute((Player) sender, args); else players.accept(sender); else cmd.execute(sender, args);
            return false;
        }

        found.accept(sender);
        return false;
    }

    public void addSubCommand(ISubCommand cmd) {
        this.commands.add(cmd);
    }

    public void removeSubCommand(ISubCommand cmd) {
        this.commands.remove(cmd);
    }

}
