package me.rimon.discordwebhookplugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class DiscordWebhookPlugin extends JavaPlugin {

    private String webhookURL = "https://discord.com/api/webhooks/1274862199827071088/gAb9Yoyol1QUOwyy1PZhgM3QzeUImBL75MPwMPL27v0wEW6U8dcJb12OCkmKyA3ptZ0j";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Listeners(), this);
        this.getCommand("newleaderboard").setExecutor(new NewLeaderboard(this.getLogger()));
        this.getCommand("updateleaderboard").setExecutor(new UpdateLeaderboard(this.getLogger()));
    }

    @Override
    public void onDisable() {

    }
}


