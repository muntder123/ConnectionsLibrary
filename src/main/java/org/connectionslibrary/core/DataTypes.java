package org.connectionslibrary.core;

import javax.xml.crypto.Data;

public enum DataTypes {

    STRING("VARCHAR(255)",12),
    INTEGER("INT",4),
    BOOLEAN("BOOL",16),
    FLOAT("FLOAT",6),
    DOUBLE("DOUBLE",8),
    LONG("BIGINT",-5),
    BYTE("TINYINT",5);

    private final String value;
    private int data;

    DataTypes(String value,int data) {
        this.value = value;
        this.data = data;
    }


    public int getData() {
        return data;
    }

    public String getValue() {
        return value;
    }

    public static DataTypes getAccutalValue(int value){
        for (DataTypes dataTypes : DataTypes.values()) {
            if(dataTypes.data == value){
                return dataTypes;
            }
        }
        return null;
    }


}