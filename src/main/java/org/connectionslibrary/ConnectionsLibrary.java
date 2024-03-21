package org.connectionslibrary;

import lombok.Getter;
import org.connectionslibrary.manager.DataManager;

public final class ConnectionsLibrary  {
    @Getter
    private ConnectionData connectionData;
    public static ConnectionsLibrary connectionsLibrary;
    @Getter
    public DataManager dataManager;


    public ConnectionsLibrary(ConnectionData connectionData){
        this.connectionData = connectionData;
    }
}
