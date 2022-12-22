package KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.ModelHandlers;

import KyleYannelli.DiscordBots.KLogger.LocalStorage.LocalStorage;
import KyleYannelli.DiscordBots.KLogger.Models.Guild;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class GuildHandler {
    public static void updateGuild(Guild guild) throws IOException, InterruptedException {
        while(guildFileOpen(guild.getId())) {
            // wait for guild file to be closed
            sleep(100);
        }
        try {
            // create Guild.json.block file to indicate that the file is being updated
            LocalStorage.createFile(guild.getId() + ".guild.json.block");

            guild.save();

            // Guild json can now be opened by another (theoretical) thread, deleting blocking file
            LocalStorage.deleteFile(guild.getId() + ".guild.json.block");
        }
        catch (IOException e) {
            e.printStackTrace();
            // Guild json can now be opened by another (theoretical) thread, deleting blocking file
            LocalStorage.deleteFile(guild.getId() + ".guild.json.block");
        }
    }

    public static boolean guildFileOpen(long guildId) {
        return LocalStorage.fileExists("src/main/resources/" + guildId + ".guild.json.block");
    }
}
