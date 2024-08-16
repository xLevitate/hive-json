package me.levitate.hive.adapters;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class UUIDAdapter extends JsonAdapter<UUID> {
    @Override
    public UUID fromJson(JsonReader reader) throws IOException {
        return UUID.fromString(reader.nextString());
    }

    @Override
    public void toJson(JsonWriter writer, UUID value) throws IOException {
        writer.value(Objects.requireNonNull(value).toString());
    }
}