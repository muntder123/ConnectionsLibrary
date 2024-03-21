package org.connectionslibrary;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.connectionslibrary.cobbine.SQLEntry;
import org.connectionslibrary.core.DataTypes;
import org.connectionslibrary.example.User;
import org.connectionslibrary.example.UserListenerExample;
import org.connectionslibrary.manager.DataManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class ConnectionsLibrary extends JavaPlugin implements Listener {
    @Getter
    private ConnectionData connectionData;
    public static ConnectionsLibrary connectionsLibrary;
    @Getter
    public DataManager dataManager;
    @Override
    public void onEnable() {
        connectionsLibrary = this;
        // Plugin startup logic
        this.connectionData = new ConnectionData();
        this.connectionData.connect("localhost","3306","root","","mcserver");

        dataManager = new DataManager();
        Bukkit.getPluginManager().registerEvents(new UserListenerExample(dataManager),this);
    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
