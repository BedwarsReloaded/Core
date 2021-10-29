package com.exortions.bedwarsreloaded.core.commands;

import com.exortions.bedwarsreloaded.core.BedwarsReloadedPlugin;

public abstract class AbstractBaseCommand implements ISubCommand {

    public AbstractBaseCommand(BedwarsReloadedPlugin plugin) {
        SubCommandExecutor<? extends BedwarsReloadedPlugin> executor = plugin.getExecutor();
        executor.addSubCommand(this);
        plugin.setExecutor(executor);
    }

}
