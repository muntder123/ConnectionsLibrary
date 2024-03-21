package org.connectionslibrary.cobbine;

import lombok.Getter;
import org.connectionslibrary.core.DataTypes;


@Getter
public class SQLEntry {
  private String key;
  private Object value;
  private boolean primary;
  private DataTypes dataTypes;

    public SQLEntry(String key, Object value,DataTypes dataTypes) {
        this.key = key;
        this.value = value;
        this.dataTypes = dataTypes;
    }

    public SQLEntry asPrimary() {
        this.primary = true;
        return this;
    }

    public static SQLEntry of(String key, Object value,DataTypes datatype) {
        return new SQLEntry(key,value,datatype);
    }

    public void setValue(Object i) {
        this.value = value;
    }

    public <T> T getValue(Class<? extends T> cast) {
        return cast.cast(value);
    }

}
