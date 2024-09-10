package me.rimon.discordwebhookplugin;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import saber.croplb.CropLB;
import saber.croplb.utils.LBPlayer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class NewLeaderboard implements CommandExecutor {
    private Logger logger;

    public NewLeaderboard(Logger logger) {
        this.logger = logger;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        CropLB cropLB = (CropLB) Bukkit.getPluginManager().getPlugin("CropLB");
        LBPlayer.Duration duration = LBPlayer.Duration.WEEK;
        String data = LeaderboardUtil.prepareLeaderboardData(cropLB, duration);

        if (player.hasPermission("DiscordWebhookPlugin.NewLeaderboard")) {
            WebhookClient webhook = WebhookClient.withUrl("https://discord.com/api/webhooks/1220799046260887724/5rD-NmVJ_Q8A475oglIhyxxqqhv5-Y1e0BfNTP8iHNbJGDkVtY2vkcKh9c8u9zRMjOmy");
            WebhookEmbed embed = new WebhookEmbedBuilder()
                    .setColor(0xff0000)
                    .setDescription(data)
                    .setTitle(new WebhookEmbed.EmbedTitle("Crops Harvested Leaderboard",null))
                    .build();
            webhook.send(embed).thenAccept(sentMessage -> {
                long messageId = sentMessage.getId();
                try (FileWriter writer = new FileWriter("messageId.txt")) {
                    writer.write(String.valueOf(messageId));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                webhook.close();
            });
        }
        return false;
    }
}

