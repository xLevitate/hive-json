package me.levitate.quill.storage;

import me.levitate.quill.storage.adapters.BooleanAdapter;
import me.levitate.quill.storage.adapters.UUIDAdapter;
import com.squareup.moshi.*;
import lombok.Getter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public JSONStorage(File dataFolder, String fileName, Class<K> keyClass, Class<V> valueClass) {
        this.file = new File(dataFolder, fileName);
        this.storage = new HashMap<>();

        this.moshi = new Moshi.Builder()
                .add(UUID.class, new UUIDAdapter())
                .add(Boolean.class, new BooleanAdapter())
                .build();

        final Type type = Types.newParameterizedType(Map.class, keyClass, valueClass);
        this.jsonAdapter = moshi.adapter(type);

        // Ensure the data folder exists
        if (!dataFolder.exists())
            dataFolder.mkdirs();
    }

    public void save() {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonAdapter.toJson(storage));
        } catch (IOException e) {
            System.out.println("Quill Error -> " + e.getMessage());
        }
    }

    public void load() {
        if (!file.exists())
            return;

        try {
            final String content = new String(Files.readAllBytes(file.toPath()));
            final Map<K, V> loadedMap = jsonAdapter.fromJson(content);

            if (loadedMap != null) {
                storage.clear();
                storage.putAll(loadedMap);
            }
        } catch (IOException e) {
            System.out.println("Quill Error -> " + e.getMessage());
        }
    }
}
