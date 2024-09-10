package me.rimon.discordwebhookplugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class DiscordWebhookPlugin extends JavaPlugin {


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Listeners(this), this);
        this.getCommand("newleaderboard").setExecutor(new NewLeaderboard(this));
        this.getCommand("updateleaderboard").setExecutor(new UpdateLeaderboard(this));

        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {

    }
}


