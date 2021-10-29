package com.exortions.bedwarsreloaded.core;

import com.exortions.bedwarsreloaded.core.commands.Command;
import com.exortions.bedwarsreloaded.core.commands.SubCommandExecutor;
import com.exortions.bedwarsreloaded.core.update.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;

import java.util.logging.Level;

public interface IBedwarsReloadedPlugin {

    void registerListener(Listener listener);
    void registerListeners(Listener... listeners);

    void registerMainCommand(TabCompleter completer);
    void registerCommand(String cmd, CommandExecutor executor, TabCompleter... completer);
    void registerCommands(Command... commands);

    default void registerListeners() {}
    default void registerCommands() {}

    default boolean checkForUpdates() {
        new UpdateChecker().getLatestUpdate(this, version -> { if (version.equals(getBedwarsReloadedPlugin().getPluginVersion())) Bukkit.getConsoleSender().sendMessage(getPrefix() + "Plugin is up-to date."); else Bukkit.getConsoleSender().sendMessage(getPrefix() + "Plugin is out of date!\nCurrent version: " + getBedwarsReloadedPlugin().getPluginVersion() + ", Latest version:  " + version); });
        return true;
    }
    default boolean loadConfiguration() { return true; }
    default boolean loadLanguages() { return true; }
    default boolean loadMessages() { return true; }
    default boolean loadStorage() { return true; }
    default boolean loadData() { return true; }
    default boolean loadMetrics() { return true; }

    String getPluginName();

    String getFullName();

    String getPluginVersion();

    void reload();

    BedwarsReloadedPlugin getBedwarsReloadedPlugin();

    default void sendStartupMessage() {}
    default int getSpigotId() { return 0; }

    ChatColor getPrimaryColor();
    ChatColor getSecondaryColor();

    void sendMessage(String message);
    void sendMessage(String message, Object... format);

    boolean loadServices();

    void log(Level level, String message);
    void info(String message);
    void warning(String message);
    void severe(String message);

    String getPrefix();

    SubCommandExecutor<? extends BedwarsReloadedPlugin> getExecutor();
    void setExecutor(SubCommandExecutor<? extends BedwarsReloadedPlugin> executor);

    Server getRunningServer();

}
