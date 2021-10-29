package com.exortions.bedwarsreloaded.core.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

@Data
@AllArgsConstructor
public class Command {

    private String name;
    private CommandExecutor commandExecutor;
    private TabCompleter tabCompleter;

}
