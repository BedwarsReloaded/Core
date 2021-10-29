package com.exortions.bedwarsreloaded.core.commands.miscellaneous;

import com.exortions.bedwarsreloaded.core.BedwarsReloadedPlugin;
import com.exortions.bedwarsreloaded.core.commands.CommonCommand;
import com.exortions.bedwarsreloaded.core.commands.ISubCommand;
import org.apache.commons.lang.StringUtils;
import com.exortions.bedwarsreloaded.core.util.ChatColor;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public interface IHelpCommand extends CommonCommand<String> {

    @Override
    @SuppressWarnings("unchecked")
    default String run(BedwarsReloadedPlugin plugin, String prefix, String[] args, Object... other) {
        if (other.length > 0 && other.getClass().isAssignableFrom(List.class)) return run(plugin, prefix, (List<ISubCommand>) other[0], args);
        return "";
    }

    /**
     * Gets the message ran when the Help command is called. This should be sent to the implementation of the sender.
     * @param plugin BedwarsReloaded
     * @param prefix plugin#getPrefix
     * @param commands List of commands from the SubCommandExecutor.
     * @param args Arguments passed in to the help command by the SubCommandExecutor.
     * @see com.exortions.bedwarsreloaded.core.commands.SubCommandExecutor
     * @return The string sent to the implementation of the Console/Player sender by the server type.
     */
    default String run(BedwarsReloadedPlugin plugin, String prefix, List<ISubCommand> commands, String[] args) {
        // Here we make message a final element array that allows Lambda expressions to access it. It is still one string however.
        final String[] message = {""};

        message[0] = message[0].concat(prefix + "Running " + ChatColor.DARK_AQUA + plugin.getFullName() + ChatColor.WHITE + ".\n");

        // Atomic boolean is used to allow Lambda expressions to modify it.
        AtomicBoolean foundCommand = new AtomicBoolean(false);

        // If no arguments are passed, display each command.
        if (args.length == 0) commands.forEach(cmd -> { foundCommand.set(true); message[0] = message[0].concat(ChatColor.DARK_AQUA + " > " + ChatColor.WHITE + cmd.usage().split(" OR ")[0] + "\n"); });

        // If arguments are passed, display usages for specific command.
        else commands.forEach(cmd -> {
            if (args[0].equals(cmd.name())) {
                foundCommand.set(true);
                message[0] = message[0].concat(prefix + ChatColor.DARK_AQUA + StringUtils.capitalize(cmd.name()) + ChatColor.WHITE + " (" + cmd.permission() + "):\n");
                message[0] = message[0].concat(ChatColor.DARK_AQUA + " > " + ChatColor.WHITE + cmd.description() + "\n");
                String[] usages = cmd.usage().split(" OR ");
                for (String s : usages) message[0] = message[0].concat(ChatColor.DARK_AQUA + " > " + ChatColor.WHITE + s + "\n");
            }
        });

        // Return the message and remove the \n at the end.
        return foundCommand.get() ? message[0].substring(0, message[0].length()-1) : prefix + ChatColor.RED + "Command not found! Type /bedwarsreloaded help for a list of commands.";
    }

}
