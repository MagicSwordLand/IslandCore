package net.brian.islandcore.data.objects;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Table<T> {

    final HashMap<String,T> dataMap;
    final String id;
    final Class<T> dataClass;


    public Table(String id,Class<T> dataClass){
        this.dataClass = dataClass;
        dataMap = new HashMap<>();
        this.id = id;
    }

    public T getData(String uuid){
        return dataMap.get(uuid);
    }

    public void setData(String uuid,Object data){
        dataMap.put(uuid, (T) data);
    }

    public Class<T> getDataClass(){
        return dataClass;
    }

    public String getId() {
        return id;
    }

    public Set<Map.Entry<String,T>> getDataEntries() {
        return dataMap.entrySet();
    }
}
