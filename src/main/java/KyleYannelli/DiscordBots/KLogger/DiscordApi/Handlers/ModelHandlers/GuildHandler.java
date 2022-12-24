package KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.ModelHandlers;

import KyleYannelli.DiscordBots.KLogger.LocalStorage.LocalStorage;
import KyleYannelli.DiscordBots.KLogger.Models.Guild;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class GuildHandler {
    public static void updateGuild(Guild guild) throws IOException, InterruptedException {
        while(!canUseGuild(guild));

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

    public static void deleteGuild(Guild guild) {
        try {
            while(!canUseGuild(guild));

            // create Guild.json.block file to indicate that the file is being updated
            LocalStorage.createFile(guild.getId() + ".guild.json.block");

            LocalStorage.deleteFile(guild.getId() + ".guild.json");

            // Guild json can now be opened (although shouldn't) by another (theoretical) thread, deleting blocking file
            LocalStorage.deleteFile(guild.getId() + ".guild.json.block");
        }
        catch (Exception e) {
            try {
                sleep(1000);
                while(!canUseGuild(guild));

                // create Guild.json.block file to indicate that the file is being updated
                LocalStorage.createFile(guild.getId() + ".guild.json.block");

                LocalStorage.deleteFile(guild.getId() + ".guild.json");

                // Guild json can now be opened (although shouldn't) by another (theoretical) thread, deleting blocking file
                LocalStorage.deleteFile(guild.getId() + ".guild.json.block");
            }
            catch (Exception e2) {
                System.out.println("Failed to delete guild " + guild.getId() + " after 2 attempts.\n Initial Attempt Error: " + e.getMessage() + "\n Second Attempt Error: " + e2.getMessage());
            }
        }
    }

    public static boolean guildExists(long guildId) {
        return LocalStorage.fileExists(guildId + ".guild.json");
    }

    public static boolean canUseGuild(Guild guild) throws InterruptedException {
        // generate random number between 100 and 1000
        int randomWait = (int) (Math.random() * 900) + 100;
        sleep(randomWait);
        return !guildFileOpen(guild.getId());
    }

    public static boolean canUseGuild(long guildId) throws InterruptedException {
        // generate random number between 100 and 1000
        int randomWait = (int) (Math.random() * 900) + 100;
        sleep(randomWait);
        return !guildFileOpen(guildId);
    }

    public static boolean guildFileOpen(long guildId) {
        return LocalStorage.fileExists("src/main/resources/" + guildId + ".guild.json.block");
    }
}
