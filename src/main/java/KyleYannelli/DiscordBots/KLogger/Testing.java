package KyleYannelli.DiscordBots.KLogger;

import KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.ModelHandlers.GuildHandler;
import KyleYannelli.DiscordBots.KLogger.Models.Guild;

import java.io.IOException;

public class Testing {
    public static void main(String[] args) throws IOException, InterruptedException {
        Guild testGuildOne = new Guild(123456789, true);
        Guild testGuildTwo = new Guild(987654321, true);
        GuildHandler.updateGuild(testGuildOne);
        GuildHandler.updateGuild(testGuildTwo);
    }
}
