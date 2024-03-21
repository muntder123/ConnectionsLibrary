package org.connectionslibrary.example;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.connectionslibrary.manager.DataManager;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
@RequiredArgsConstructor
public class UserListenerExample implements Listener {
    private final DataManager dataManager;


    @EventHandler
    public void asyncJoin(AsyncPlayerPreLoginEvent event){
        AsyncPlayerPreLoginEvent.Result loginResult = event.getLoginResult();
        UUID uniqueId = event.getUniqueId();
        if(loginResult == AsyncPlayerPreLoginEvent.Result.ALLOWED){
            dataManager.load(uniqueId);
        }
    }

    @EventHandler
    public void save(PlayerQuitEvent event){
        Player player = event.getPlayer();
        CompletableFuture.runAsync(() -> {
            dataManager.save(player.getUniqueId());
        });
    }
}
