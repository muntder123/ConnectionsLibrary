package org.connectionslibrary.manager;

import org.connectionslibrary.connections.SQLEntry;
import org.connectionslibrary.example.ExampleLinkedSQLTable;
import org.connectionslibrary.example.User;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DataManager {

    private final Map<UUID, User> objectsMap = new ConcurrentHashMap<>();

    private final ExampleLinkedSQLTable exampleLinkedSQLTable;

    public DataManager(){
        this.exampleLinkedSQLTable = new ExampleLinkedSQLTable("users");
    }


    public void load(UUID uuid){
        User user = exampleLinkedSQLTable.loadUserFromDatabase(uuid);
        objectsMap.put(uuid,user);
    }


    public void save(UUID uuid){
        User sqlObject = objectsMap.remove(uuid);
        exampleLinkedSQLTable.saveUserToDatabase(sqlObject);
    }


    public User getUser(UUID uuid){
        return objectsMap.get(uuid);
    }

    public SQLEntry getData(UUID uuid,SQLEntry sqlEntry){
        User user = getUser(uuid);
        return user.getEntry(e -> e.getKey().equalsIgnoreCase(sqlEntry.getKey())).orElseGet(null);
    }
}
