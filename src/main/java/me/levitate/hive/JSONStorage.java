package me.levitate.hive;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import lombok.Getter;
import me.levitate.hive.adapters.BooleanAdapter;
import me.levitate.hive.adapters.UUIDAdapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * This is the class that handles the JSON storage.
 *
 * @param <K> Key
 * @param <V> Value
 */
@Getter
public class JSONStorage<K, V> {
    private final Map<K, V> storage;

    private Moshi.Builder builder;
    private File dataFolder;
    private String fileName;
    private Class<K> keyClass;
    private Class<V> valueClass;

    private File file;
    private Moshi moshi;
    private JsonAdapter<Map<K, V>> jsonAdapter;

    public JSONStorage() {
        this.storage = new HashMap<>();
        this.builder = new Moshi.Builder();
    }

    public JSONStorage<K, V> dataFolder(File dataFolder) {
        this.dataFolder = dataFolder;
        return this;
    }

    public JSONStorage<K, V> fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public JSONStorage<K, V> keyClass(Class<K> keyClass) {
        this.keyClass = keyClass;
        return this;
    }

    public JSONStorage<K, V> valueClass(Class<V> valueClass) {
        this.valueClass = valueClass;
        return this;
    }

    public <T> JSONStorage<K, V> addAdapter(Type type, JsonAdapter<T> jsonAdapter) {
        this.builder = builder.add(type, jsonAdapter);
        return this;
    }

    public JSONStorage<K, V> build() {
        if (dataFolder == null || fileName == null || keyClass == null || valueClass == null) {
            throw new IllegalStateException("Required properties are missing!");
        }

        this.file = new File(dataFolder, fileName);

        this.moshi = builder
                .add(UUID.class, new UUIDAdapter())
                .add(Boolean.class, new BooleanAdapter())
                .build();

        this.jsonAdapter = moshi.adapter(Types.newParameterizedType(Map.class, keyClass, valueClass));

        if (!dataFolder.exists() && !dataFolder.mkdirs()) {
            throw new RuntimeException("Could not create data folder: " + dataFolder);
        }

        return this;
    }

    public void save() {
        if (!storage.isEmpty()) return;

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonAdapter.toJson(storage));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write data to json file: " + file);
        }
    }

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

    public void update(K key, Consumer<V> consumer) {
        Optional.ofNullable(storage.get(key))
                .ifPresent(value -> {
                    consumer.accept(value);
                    storage.put(key, value);
                });
    }

    public void put(K key, V value) {
        storage.put(key, value);
    }

    public void remove(K key) {
        storage.remove(key);
    }
}