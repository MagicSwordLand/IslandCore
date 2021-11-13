package net.brian.islandcore.data;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import dev.reactant.reactant.core.dependency.injection.Inject;
import io.github.clayclaw.dbsync.module.service.DatabaseService;
import net.brian.islandcore.IslandCropsAndLiveStocks;
import net.brian.islandcore.common.objects.IslandLogger;
import net.brian.islandcore.data.objects.IslandData;
import net.brian.islandcore.data.objects.Table;


import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

@Component
public class IslandDataHandlerImpl extends DataHandler implements LifeCycleHook {

    @Inject
    private DatabaseService databaseService;

    private HashMap<Class<?>,Table<?>> tables = new HashMap<>();

    @Override
    public void register(String id, Class<?> dataClass) {
        classHashMap.put(id,dataClass);
        tables.put(dataClass,new Table<>(id,dataClass));
        try {
            PreparedStatement preparedStatement = getConnection()
                    .prepareStatement("CREATE TABLE IF NOT EXISTS island_"+id+" (" +
                            "uuid VARCHAR(255) NOT NULL UNIQUE," +
                            "datajson LONGTEXT NOT NULL," +
                            "primary key(uuid))");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T getData(String id, String uuid, Class<T> dataClass) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM island_"+id+" WHERE uuid = ? LIMIT 1");
            preparedStatement.setString(1,uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String dataJson = resultSet.getString("datajson");
                if(dataJson != null){
                    return IslandCropsAndLiveStocks.gson.fromJson(resultSet.getString("datajson"),dataClass);
                }
            }
            if(dataClass.getSuperclass().equals(IslandData.class)){
                return dataClass.getConstructor(String.class).newInstance(uuid);
            }
            return dataClass.newInstance();
        } catch (SQLException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void saveData(String id, String uuid, Object data) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO island_"+id+" (uuid,datajson) VALUES (?,?)" +
                    " ON DUPLICATE KEY UPDATE datajson = ?");
            String dataJson = IslandCropsAndLiveStocks.gson.toJson(data);
            preparedStatement.setString(1,uuid);
            preparedStatement.setString(2,dataJson);
            preparedStatement.setString(3,dataJson);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public Connection getConnection() {
        return databaseService.getConnection();
    }

    public <T> Table<T> getTable(Class<T> dataClass){
        return (Table<T>) tables.get(dataClass);
    }

    public Collection<Table<?>> getTables(){
        return tables.values();
    }

}
