package me.levitate.hive;

import com.squareup.moshi.*;
import lombok.Getter;
import me.levitate.hive.adapters.BooleanAdapter;
import me.levitate.hive.adapters.UUIDAdapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public abstract class JSONStorage<K, V> {
    private final File file;
    private final Map<K, V> storage;
    private final Moshi moshi;
    private final JsonAdapter<Map<K, V>> jsonAdapter;

    public JSONStorage(File dataFolder, String fileName, Class<K> keyClass, Class<V> valueClass) {
        this.file = new File(dataFolder, fileName);
        this.storage = new HashMap<>();

        this.moshi = new Moshi.Builder()
                .add(UUID.class, new UUIDAdapter())
                .add(Boolean.class, new BooleanAdapter())
                .build();

        this.jsonAdapter = moshi.adapter(Types.newParameterizedType(Map.class, keyClass, valueClass));

        if (!dataFolder.exists() && !dataFolder.mkdirs()) {
            throw new RuntimeException("Could not create data folder: " + dataFolder);
        }
    }

    /**
     * Saves all the data from memory to file.
     */
    public void save() {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonAdapter.toJson(storage));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write data to json file: " + file);
        }
    }

    /**
     * Loads all the data from file to memory.
     */
    public void load() {
        if (!file.exists()) return;

        try {
            final String content = new String(Files.readAllBytes(file.toPath()));
            final Map<K, V> loadedMap = jsonAdapter.fromJson(content);

            if (loadedMap != null) {
                storage.clear();
                storage.putAll(loadedMap);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read data from json file: " + file);
        }
    }
}
