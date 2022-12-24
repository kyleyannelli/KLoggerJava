package KyleYannelli.DiscordBots.KLogger.DiscordApi.Events;

import KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.ApiHandlers.ChannelApiHandler;
import KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.ModelHandlers.GuildHandler;
import KyleYannelli.DiscordBots.KLogger.Models.Channel;
import KyleYannelli.DiscordBots.KLogger.Models.Guild;
import org.javacord.api.DiscordApi;

import java.io.IOException;

public class GuildEvents {
    public static void handleBotStartUp(DiscordApi discordApi) {
        discordApi.getServers().forEach(server -> {
            try {
                // if the guild is not in the database, add it
                if(!GuildHandler.guildExists(server.getId())) {
                    GuildHandler.updateGuild(new Guild(server.getId(), true));
                }
            }
            catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
    public static void handleJoinGuildEvent(DiscordApi api) {
        api.addServerJoinListener(event -> {
            System.out.println("Joined guild: " + event.getServer().getName() + " Saving guild...");
            Guild joinedGuild = new Guild(event.getServer().getId(), true);
            try {
                long createdChannelId = ChannelApiHandler.createLogChannel(api, joinedGuild.getId(), "logs");
                Channel loggingChannel = new Channel(createdChannelId);
                joinedGuild.setLoggingChannel(loggingChannel);
                // remember to update guild, using Guild save method is unsafe in direct use
                GuildHandler.updateGuild(joinedGuild);
            } catch (IOException | InterruptedException e) {
                // try again
                try {
                    GuildHandler.updateGuild(joinedGuild);
                } catch (IOException | InterruptedException e2) {
                    // state that guild was not saved upon joining server and log errors
                    System.out.println("Failed to save guild " + joinedGuild.getId() + " after 2 attempts.\n Initial Attempt Error: " + e.getMessage() + "\n Second Attempt Error: " + e2.getMessage());
                }
            }
        });
    }

    public static void handleLeaveGuildEvent(DiscordApi api) {
        api.addServerLeaveListener(event -> {
            System.out.println("Left guild: " + event.getServer().getName() + " Deleting guild...");
            Guild leftGuild = new Guild(event.getServer().getId(), true);
            GuildHandler.deleteGuild(leftGuild);
        });
    }
}
