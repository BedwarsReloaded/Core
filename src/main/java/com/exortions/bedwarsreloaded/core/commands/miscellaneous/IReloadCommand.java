package com.exortions.bedwarsreloaded.core.commands.miscellaneous;

import com.exortions.bedwarsreloaded.core.BedwarsReloadedPlugin;
import com.exortions.bedwarsreloaded.core.commands.CommonCommand;
import com.exortions.bedwarsreloaded.core.util.Time;

public interface IReloadCommand extends CommonCommand<String> {

    @Override
    default String run(BedwarsReloadedPlugin plugin, String prefix, String[] args, Object... other) {
        return run(plugin, prefix);
    }

    default String run(BedwarsReloadedPlugin plugin, String prefix) {
        long start = System.currentTimeMillis();
        plugin.reload();
        return prefix + "Successfully reloaded " + plugin.getFullName() + " in " + Time.difference(start, System.currentTimeMillis()).toMillis() + " ms.";
    }
}
