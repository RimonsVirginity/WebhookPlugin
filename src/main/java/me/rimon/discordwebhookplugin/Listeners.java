package me.rimon.discordwebhookplugin;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener {
    public DiscordWebhookPlugin plugin;

    public Listeners(DiscordWebhookPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String playername = player.getName();
        String Icon = "https://minotar.net/helm/" + player.getName() + "/100.png";
        WebhookClient webhook = WebhookClient.withUrl(plugin.getConfig().getString("Join/Leave"));
        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0x00FF00)
                .setAuthor(new WebhookEmbed.EmbedAuthor(player.getName(),Icon,""))
                .setDescription("**" + player.getName() + "**" + " has joined the server!")
                .build();
        webhook.send(embed);

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        String playername = player.getName();
        String Icon = "https://minotar.net/helm/" + player.getName() + "/100.png";
        WebhookClient webhook = WebhookClient.withUrl(plugin.getConfig().getString("Join/Leave"));
        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0xFF0000)
                .setAuthor(new WebhookEmbed.EmbedAuthor(player.getName(),Icon,""))
                .setDescription("**" + player.getName() + "**" + " has left the server!")
                .build();
        webhook.send(embed);

    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String chat = event.getMessage();
        String playername = player.getName();
        chat = chat.replaceAll("@everyone","`@everyone`").replaceAll("@here","`@here`");

        WebhookClient webhook = WebhookClient.withUrl(plugin.getConfig().getString("Chat"));
        webhook.send("**"+ player.getName() + "** >> " + chat);
    }
}

