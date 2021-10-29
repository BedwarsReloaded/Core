package com.exortions.bedwarsreloaded.core;

import com.exortions.bedwarsreloaded.core.annotations.service.OnEnable;
import com.exortions.bedwarsreloaded.core.annotations.service.Service;
import com.exortions.bedwarsreloaded.core.annotations.service.Services;
import com.exortions.bedwarsreloaded.core.commands.Command;
import com.exortions.bedwarsreloaded.core.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

public abstract class BedwarsReloadedPlugin extends JavaPlugin implements IBedwarsReloadedPlugin {

    @Override
    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) Bukkit.getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void registerMainCommand(TabCompleter completer) {
        var cmd = Objects.requireNonNull(getCommand("bedwarsreloaded"));
        cmd.setExecutor(getExecutor());
        cmd.setTabCompleter(completer);
    }

    @Override
    public void registerCommand(String cmd, CommandExecutor executor, TabCompleter... completer) {
        var command = Objects.requireNonNull(getCommand(cmd));
        command.setExecutor(executor);
        for (TabCompleter tabCompleter : completer) command.setTabCompleter(tabCompleter);
    }

    @Override
    public void registerCommands(Command... commands) {
        for (Command command : commands) {
            var cmd = Objects.requireNonNull(getCommand(command.getName()));
            cmd.setExecutor(command.getCommandExecutor());
            cmd.setTabCompleter(command.getTabCompleter());
        }
    }

    @Override
    public String getPluginName() {
        return getDescription().getName();
    }

    @Override
    public String getFullName() {
        return getDescription().getFullName();
    }

    @Override
    public String getPluginVersion() {
        return getDescription().getVersion();
    }

    @Override
    public void sendStartupMessage() {
        var srv = getRunningServer().getName().toLowerCase().contains("bukkit") ? "Bukkit" : "Unknown";

        sendMessage(getPrefix() + "   ___     ___    ");
        sendMessage(getPrefix() + "  |   |   |___|   " + ChatColor.RESET + getFullName());
        sendMessage(getPrefix() + "  |___|   | \\     " + ChatColor.RESET + "Running on " + srv + " - " + getRunningServer().getName());
        sendMessage(getPrefix() + "  |___|   |  \\    ");
        sendMessage(getPrefix() + "                  ");
    }

    @Override
    public ChatColor getPrimaryColor() {
        return ChatColor.DARK_AQUA;
    }

    @Override
    public ChatColor getSecondaryColor() {
        return ChatColor.AQUA;
    }

    @Override
    public void sendMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }

    @Override
    public void sendMessage(String message, Object... format) {
        if (format.length <= 0) sendMessage(message);
        final String[] msg = { message };
        Arrays.stream(format).forEach(obj -> msg[0] = msg[0].replaceFirst("%s", obj == null ? "null" : obj.toString()));
        sendMessage(message);
    }

    @Override
    public boolean loadServices() {
        var success = new AtomicBoolean(true);
        if (this.getClass().isAnnotationPresent(Services.class))
            Arrays.stream(this.getClass().getDeclaredAnnotation(Services.class).services())
                    .filter(clz -> clz.isAssignableFrom(Service.class))
                    .forEach(service -> Arrays.stream(service.getMethods())
                            .filter(method -> method.isAnnotationPresent(OnEnable.class) && method.getName().equalsIgnoreCase("onenable"))
                            .forEach(method -> {
                                try {
                                    method.invoke(this, this);
                                } catch (IllegalAccessException | InvocationTargetException ex) {
                                    getLogger().log(Level.SEVERE, "Could not invoke method annotated with @Service: " + method.getClass().getName() + "#" + method.getName(), ex);
                                    success.set(false);
                                }
                            }));
        else Bukkit.getPluginManager().disablePlugin(this);
        return success.get();
    }

    @Override
    public void log(Level level, String message) {
        getLogger().log(level, message);
    }

    @Override
    public void info(String message) {
        getLogger().info(message);
    }

    @Override
    public void warning(String message) {
        getLogger().warning(message);
    }

    @Override
    public void severe(String message) {
        getLogger().severe(message);
    }

    @Override
    public String getPrefix() {
        return Chat.colorize("&7[&3&lB&b&lR&r&7]&f ");
    }

    @Override
    public BedwarsReloadedPlugin getBedwarsReloadedPlugin() {
        return this;
    }

}
