package me.levitate.hive.adapters;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;

import java.io.IOException;

public class BooleanAdapter extends JsonAdapter<Boolean> {
    @Override
    public Boolean fromJson(JsonReader reader) throws IOException {
        return reader.nextBoolean();
    }

    @Override
    public void toJson(JsonWriter writer, Boolean value) throws IOException {
        writer.value(value);
    }
}