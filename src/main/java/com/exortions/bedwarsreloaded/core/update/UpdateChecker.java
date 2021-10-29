package com.exortions.bedwarsreloaded.core.update;

import com.exortions.bedwarsreloaded.core.IBedwarsReloadedPlugin;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Consumer;

@NoArgsConstructor
public class UpdateChecker {

    @SafeVarargs
    public final void getLatestUpdate(@NotNull IBedwarsReloadedPlugin plugin, @NotNull Consumer<String> consumer, @Nullable Consumer<IOException>... consumers) {
        try {
            final Scanner scanner = new Scanner(new URL("https://api.spigotmc.org/legacy/update.php?resource=" + plugin.getSpigotId()).openStream());
            if (scanner.hasNext()) consumer.accept(scanner.next());
        } catch (IOException e) {
            if (consumers.length == 0) e.printStackTrace();
            else Objects.requireNonNull(consumers[0]).accept(e);
        }
    }

}
