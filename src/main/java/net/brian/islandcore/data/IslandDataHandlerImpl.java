package net.brian.islandcore.data;

import com.google.gson.Gson;
import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import dev.reactant.reactant.core.dependency.injection.Inject;
import io.github.clayclaw.dbsync.module.service.DatabaseService;
import net.brian.islandcore.IslandCore;
import net.brian.islandcore.data.objects.Table;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
            preparedStatement.setString(2,uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return IslandCore.gson.fromJson(resultSet.getString("datajson"),dataClass);
            }
            else{
                return dataClass.newInstance();
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void saveData(String id, String uuid, Object object) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO "+id+" (uuid,datajson) VALUES (?,?)" +
                    " ON DUPLICATE KEY UPDATE datajson = ?");
            String dataJson = IslandCore.gson.toJson(object);
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
