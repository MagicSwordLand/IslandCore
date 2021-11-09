package net.brian.islandcore.data.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public interface PostProcessable {

    void gsonPostProcess();

    class PostProcessingEnabler implements TypeAdapterFactory{

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);

            return new TypeAdapter<T>() {
                @Override
                public void write(JsonWriter jsonWriter, T t) throws IOException {
                    delegate.write(jsonWriter,t);
                }

                @Override
                public T read(JsonReader jsonReader) throws IOException {
                    T object = delegate.read(jsonReader);
                    if(object instanceof PostProcessable){
                        ((PostProcessable) object).gsonPostProcess();
                    }
                    return object;
                }
            };
        }
    }

}
