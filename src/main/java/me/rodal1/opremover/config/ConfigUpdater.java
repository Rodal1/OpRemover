package me.rodal1.opremover.config;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import me.rodal1.opremover.Main;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.base.Charsets;

public class ConfigUpdater {

    Logger log = Main.getPlugin().getLogger();
    private int newVersion = 2;
    private boolean instantDeprecated = false;
    private JavaPlugin plugin;

    private File path;
    private List<String> lines;

    public ConfigUpdater(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void checkUpdate(int oldV) {
        path = new File(plugin.getDataFolder(), "config.yml");

        if (oldV == 0 || instantDeprecated) {
            if (oldV != newVersion)
                deprecateConfig();
            return;
        }

        if (Main.getPlugin().getConfig().getInt("configVersion") == newVersion) {
            log.info("The config is updated!");
            return;
        }

        lines = readFile(path);
        updateConfig();
    }

    public void updateConfig() {

        log.info("Updating the config!");

        List<String> newLines = readInsideFile("/config.yml");

        lines.removeIf(s -> s.trim().isEmpty() || s.trim().startsWith("#") || s.split(":").length == 1);
        lines.forEach(s -> {
            String[] a = s.split(":");
            String newS = joinString(Arrays.copyOfRange(a, 1, a.length), ":");
            int index = getIndex(a[0], newLines);
            if (index > -1)
                newLines.set(index, newLines.get(index).split(":")[0] + ":" + newS);
        });

        String versionLine = "configVersion: ";
        newLines.set(getIndex(versionLine, newLines), versionLine + newVersion);
        writeFile(path, newLines);
        log.info("Config is now updated!");
        //Here we reload the config
    }

    private void deprecateConfig() {
        log.info("Now your config is deprecated please check your folder for re-setting it!");
        String depName = "deprecated_config_" + LocalDate.now();
        File old = new File(path.getParentFile(), depName + ".yml");
        try {
            Files.copy(path.toPath(), old.toPath(),
                    new CopyOption[] { StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING });
            path.delete();
        } catch (Exception e) {}

        //Here we re-create the config.
    }

    public String joinString(String[] text, String character) {
        return Arrays.stream(text).collect(Collectors.joining(character));
    }

    public int getIndex(String line, List<String> list) {
        for (String s : list)
            if (s.startsWith(line) || s.equalsIgnoreCase(line))
                return list.indexOf(s);
        return -1;
    }

    public void writeFile(File file, List<String> toWrite) {
        try {
            Files.write(file.toPath(), toWrite, Charsets.ISO_8859_1);
        } catch (Exception e) {}
    }

    public List<String> readFile(File file) {
        try {
            return Files.readAllLines(file.toPath(), Charsets.ISO_8859_1);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<String> readInsideFile(String path) {
        try (InputStream in = plugin.getClass().getResourceAsStream(path);
             BufferedReader input = new BufferedReader(new InputStreamReader(in));) {
            return input.lines().collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}