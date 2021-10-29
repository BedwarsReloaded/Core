package com.exortions.bedwarsreloaded.core.commands;

import com.exortions.bedwarsreloaded.core.BedwarsReloadedPlugin;

public interface CommonCommand<T> {

    T run(BedwarsReloadedPlugin plugin, String prefix, String[] args, Object... other);

}
