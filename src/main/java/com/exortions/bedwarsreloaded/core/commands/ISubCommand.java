package com.exortions.bedwarsreloaded.core.commands;

import com.exortions.bedwarsreloaded.core.BedwarsReloadedPlugin;
import com.exortions.bedwarsreloaded.core.annotations.command.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface ISubCommand {

    default String name() {
        if (getClass().isAnnotationPresent(Name.class)) return getClass().getDeclaredAnnotation(Name.class).name();
        return getClass().getSimpleName().toLowerCase().replaceFirst("command", "");
    }

    default String permission() {
        if (getClass().isAnnotationPresent(Permission.class)) return getClass().getDeclaredAnnotation(Permission.class).permission();
        return getPlugin().getPluginName().toLowerCase() + "." + name();
    }

    default String usage() {
        if (getClass().isAnnotationPresent(Usage.class)) return getClass().getDeclaredAnnotation(Usage.class).usage();
        return "/" + getPlugin().getPluginName().toLowerCase() + " " + name();
    }

    default String description() {
        if (getClass().isAnnotationPresent(Description.class)) return getClass().getDeclaredAnnotation(Description.class).description();
        return "";
    }

    default boolean requiresPlayer() {
        return getClass().isAnnotationPresent(RequiresPlayer.class);
    }

    default void execute(CommandSender sender, String[] args) {}
    default void execute(Player player, String[] args) {}

    BedwarsReloadedPlugin getPlugin();

}
