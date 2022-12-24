package KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.ModelHandlers;

import KyleYannelli.DiscordBots.KLogger.LocalStorage.LocalStorage;
import KyleYannelli.DiscordBots.KLogger.Models.Channel;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class ChannelHandler {
    public static void updateChannel(Channel channel) throws IOException, InterruptedException {
        while(!canUseChannel(channel));

        try {
            // create channel.json.block file to indicate that the file is being updated
            LocalStorage.createFile(channel.getId() + ".channel.json.block");

            channel.save();

            // channel json can now be opened by another (theoretical) thread, deleting blocking file
            LocalStorage.deleteFile(channel.getId() + ".channel.json.block");
        }
        catch (IOException e) {
            e.printStackTrace();
            // channel json can now be opened by another (theoretical) thread, deleting blocking file
            LocalStorage.deleteFile(channel.getId() + ".channel.json.block");
        }
    }

    public static void deleteChannel(Channel channel) {
        try {
            while(!canUseChannel(channel));

            // create channel.json.block file to indicate that the file is being updated
            LocalStorage.createFile(channel.getId() + ".channel.json.block");

            LocalStorage.deleteFile(channel.getId() + ".channel.json");

            // channel json can now be opened (although shouldn't) by another (theoretical) thread, deleting blocking file
            LocalStorage.deleteFile(channel.getId() + ".channel.json.block");
        }
        catch (Exception e) {
            try {
                sleep(1000);
                while(!canUseChannel(channel));

                // create channel.json.block file to indicate that the file is being updated
                LocalStorage.createFile(channel.getId() + ".channel.json.block");

                LocalStorage.deleteFile(channel.getId() + ".channel.json");

                // channel json can now be opened (although shouldn't) by another (theoretical) thread, deleting blocking file
                LocalStorage.deleteFile(channel.getId() + ".channel.json.block");
            }
            catch (Exception e2) {
                System.out.println("Failed to delete channel " + channel.getId() + " after 2 attempts.\n Initial Attempt Error: " + e.getMessage() + "\n Second Attempt Error: " + e2.getMessage());
            }
        }
    }

    public static boolean channelExists(long channelId) {
        return LocalStorage.fileExists(channelId + ".channel.json");
    }

    public static boolean canUseChannel(Channel channel) throws InterruptedException {
        // generate random number between 100 and 1000
        int randomWait = (int) (Math.random() * 900) + 100;
        sleep(randomWait);
        return !channelFileOpen(channel.getId());
    }

    public static boolean canUseChannel(long channelId) throws InterruptedException {
        // generate random number between 100 and 1000
        int randomWait = (int) (Math.random() * 900) + 100;
        sleep(randomWait);
        return !channelFileOpen(channelId);
    }

    public static boolean channelFileOpen(long channelId) {
        return LocalStorage.fileExists("src/main/resources/" + channelId + ".channel.json.block");
    }
}
