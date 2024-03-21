package org.connectionslibrary.example;

import lombok.Getter;
import lombok.Setter;
import org.connectionslibrary.cobbine.SQLEntry;
import org.connectionslibrary.cobbine.SQLObject;
import org.connectionslibrary.core.DataTypes;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */
@Getter
public class User implements SQLObject {
    private List<SQLEntry> entries;
    private List<SQLEntry> changes;

    private UUID uuid;

    public User(){
        this.entries = new ArrayList<>();
        this.changes = new ArrayList<>();
    }

    public User(UUID uuid) {
        this.entries = new ArrayList<>();
        this.changes = new ArrayList<>();
        this.uuid = uuid;
        addEntry(SQLEntry.of("uuid", uuid.toString(),DataTypes.STRING).asPrimary());
        addEntry(SQLEntry.of("kills", Integer.class,DataTypes.INTEGER));


    }


}
