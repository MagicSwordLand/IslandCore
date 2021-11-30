package net.brian.islandcore.data.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.brian.islandcore.IslandCropsAndLiveStocks;
import net.brian.islandcore.common.objects.IslandLogger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.logging.Level;

public interface PostProcessable {


    void gsonPostDeserialize();
    void gsonPostSerialize();

    class PostProcessingEnabler implements TypeAdapterFactory{
        private final JavaPlugin plugin;
        private final Executor mainThread;

        public PostProcessingEnabler(JavaPlugin plugin){
            this.plugin = plugin;
            mainThread = Bukkit.getScheduler().getMainThreadExecutor(plugin);
        }

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);

            return new <T>TypeAdapter<T>() {
                @Override
                public void write(JsonWriter jsonWriter, T t) throws IOException {
                    if (t instanceof PostProcessable) {
                        try {
                            CompletableFuture.runAsync(((PostProcessable) t)::gsonPostSerialize
                            , mainThread).get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    delegate.write(jsonWriter,t);
                }

                @Override
                public T read(JsonReader jsonReader) throws IOException {
                    T object = delegate.read(jsonReader);
                    if (object instanceof PostProcessable) {
                        try {
                            //Run in main thread
                            CompletableFuture.runAsync(((PostProcessable) object)::gsonPostDeserialize,mainThread).get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    return object;
                }
            };
        }
    }

}
