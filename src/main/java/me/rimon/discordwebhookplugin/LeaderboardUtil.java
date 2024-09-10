package me.rimon.discordwebhookplugin;

import saber.croplb.CropLB;
import saber.croplb.utils.LBPlayer;

import java.util.ArrayList;

public class LeaderboardUtil {

    public static String prepareLeaderboardData(CropLB cropLB, LBPlayer.Duration duration) {
        ArrayList<LBPlayer> players = new ArrayList<>(cropLB.getPlayers().values());
        players.sort((o1, o2) -> o2.getBreakCount(duration) - o1.getBreakCount(duration));
        StringBuilder data = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            if (i >= players.size()) break;
            LBPlayer p = players.get(i);
            data.append(i + 1).append(": **").append(p.getUserName()).append("** (")
                    .append(p.getBreakCount(duration)).append(" Crops broken)").append(System.lineSeparator());
        }

        return data.toString();
    }
}
