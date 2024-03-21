package org.connectionslibrary.cobbine;

import org.connectionslibrary.ConnectionData;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;



public interface SQLObject {
    List<SQLEntry> getEntries();
    List<SQLEntry> getChanges();


    default void addEntry(SQLEntry entry) {
        getEntries().add(entry);
    }

    default Optional<SQLEntry> getEntry(Predicate<? super SQLEntry> filter){
       return getEntries().stream().filter(filter).findFirst();
    }

    default void updateEntry(String key, Function<SQLEntry, SQLEntry> function) {
        getEntry(e-> e.getKey().equalsIgnoreCase(key)).ifPresent(entry -> {
            function.apply(entry);
            getChanges().add(entry);
        });
    }


}
