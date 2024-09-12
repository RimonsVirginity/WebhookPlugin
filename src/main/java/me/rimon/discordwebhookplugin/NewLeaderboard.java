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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Logger;
import static club.minnced.discord.webhook.WebhookClient.withUrl;


public class NewLeaderboard implements CommandExecutor {
    private Logger logger;
    public DiscordWebhookPlugin plugin;

    public NewLeaderboard(DiscordWebhookPlugin plugin){
        this.plugin = plugin;
    }


    public NewLeaderboard(Logger logger) {
        this.logger = logger;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        CropLB cropLB = (CropLB) Bukkit.getPluginManager().getPlugin("CropLB");
        LBPlayer.Duration duration = LBPlayer.Duration.MONTH;
        String data = LeaderboardUtil.prepareLeaderboardData(cropLB, duration);

        if (player.hasPermission("DiscordWebhookPlugin.NewLeaderboard")) {
            WebhookClient webhook = withUrl(plugin.getConfig().getString("CropLeaderboard"));
            LocalDate now = LocalDate.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM yyyy");
            WebhookEmbed embed = new WebhookEmbedBuilder()
                    .setColor(0xff0000)
                    .setDescription(data)
                    .setTitle(new WebhookEmbed.EmbedTitle("Crops Harvested Leaderboard" ,null))
                    .setAuthor(new WebhookEmbed.EmbedAuthor(now.format(format)+ "","",""))
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

