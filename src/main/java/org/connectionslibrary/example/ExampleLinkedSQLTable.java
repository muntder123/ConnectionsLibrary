package org.connectionslibrary.example;


import org.connectionslibrary.ConnectionData;
import org.connectionslibrary.connections.LinkedSQLTable;
import org.connectionslibrary.connections.SQLEntry;
import org.connectionslibrary.core.DataTypes;

import java.util.UUID;


public class ExampleLinkedSQLTable extends LinkedSQLTable<ConnectionData> {

    public ExampleLinkedSQLTable(String table_name) {
        super(table_name);
        createTable(
        SQLEntry.of("uuid",String.class,DataTypes.STRING).asPrimary(),
        SQLEntry.of("kills",Integer.class,DataTypes.INTEGER));
    }

    public User loadUserFromDatabase(UUID uuid) {
        SQLEntry where = SQLEntry.of("uuid", uuid.toString(), DataTypes.STRING).asPrimary();
        User user = load(where, User.class);
        if (user == null) {
            user = new User(uuid);
        }
        return user;
    }


    public void saveUserToDatabase(User object) {
        // Perform saving logic to save the updated user back to the database
        save(object);
    }

}
