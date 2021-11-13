package net.brian.islandcore.data;

import java.sql.Connection;
import java.util.HashMap;
import java.util.UUID;

public abstract class DataHandler {

    protected final HashMap<String,Class<?>> classHashMap = new HashMap<>();

    protected abstract void register(String id,Class<?> dataClass);

    protected abstract <T> T getData(String id, String uuid,Class<T> dataClass);

    protected abstract void saveData(String id,String uuid,Object data);

    protected abstract Connection getConnection();


}
