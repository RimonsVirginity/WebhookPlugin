package me.rimon.discordwebhookplugin;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import me.rimon.discordwebhookplugin.NewLeaderboard;

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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import static club.minnced.discord.webhook.WebhookClient.withUrl;

public class UpdateLeaderboard implements CommandExecutor {
    private Logger logger;
    public DiscordWebhookPlugin plugin;

    public UpdateLeaderboard(DiscordWebhookPlugin plugin){
        this.plugin = plugin;
    }

    public UpdateLeaderboard(Logger logger) {
        this.logger = logger;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        CropLB cropLB = (CropLB) Bukkit.getPluginManager().getPlugin("CropLB");
        LBPlayer.Duration duration = LBPlayer.Duration.MONTH;
        String data = LeaderboardUtil.prepareLeaderboardData(cropLB, duration);
        LocalDate now = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM yyyy");

        if (player.hasPermission("DiscordWebhookPlugin.UpdateLeaderboard")) {
            long messageId;
            try (BufferedReader reader = new BufferedReader(new FileReader("messageId.txt"))) {
                messageId = Long.parseLong(reader.readLine());
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
                player.sendMessage("Could not find or read the stored message ID.");
                return false;
            }
            WebhookClient webhook = withUrl(plugin.getConfig().getString("CropLeaderboard"));
            WebhookEmbed updatedEmbed = new WebhookEmbedBuilder()
                    .setColor(0xff0000)
                    .setTitle(new WebhookEmbed.EmbedTitle("Crops Harvested Leaderboard" ,null))
                    .setAuthor(new WebhookEmbed.EmbedAuthor(now.format(format)+ "","",""))
                    .setDescription(data)
                    .build();
            webhook.edit(messageId, new WebhookMessageBuilder().addEmbeds(updatedEmbed).build());
            webhook.close();
        }

        return false;
    }
}

