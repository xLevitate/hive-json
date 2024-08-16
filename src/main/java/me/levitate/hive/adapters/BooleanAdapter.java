package me.levitate.quill.storage.adapters;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;

import java.io.IOException;
import java.util.Objects;

public class BooleanAdapter extends JsonAdapter<Boolean> {
    @Override
    public Boolean fromJson(JsonReader reader) throws IOException {
        return Boolean.valueOf(reader.nextString());
    }

    @Override
    public void toJson(JsonWriter writer, Boolean value) throws IOException {
        writer.value(Objects.requireNonNull(value).toString());
    }
}