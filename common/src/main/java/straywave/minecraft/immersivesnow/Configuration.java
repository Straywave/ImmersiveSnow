package straywave.minecraft.immersivesnow;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.architectury.injectables.annotations.ExpectPlatform;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class Configuration {
    public static ConfigurationData data = new ConfigurationData();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File PATH = getConfigDir().resolve("ImmersiveSnow.json").toFile();

    @ExpectPlatform
    public static Path getConfigDir() {
        throw new AssertionError();
    }

    public static void load() {
        try {
            data = GSON.fromJson(Files.newReader(PATH, StandardCharsets.UTF_8), ConfigurationData.class);
        } catch (FileNotFoundException ignored) {}
        save(); // Reformat, pretty print, remove excess keys, save default config
    }

    public static void save() {
        try {
            PATH.createNewFile();
            Writer writer = Files.newWriter(PATH, StandardCharsets.UTF_8);
            GSON.toJson(data, writer);
            writer.append("\n");
            writer.close();
        } catch (IOException e) {
            ImmersiveSnow.LOGGER.error(String.format("Failed writing configuration file: %s", e));
        }
    }
}
