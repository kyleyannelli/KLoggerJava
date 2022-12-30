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
            // if the guild is not in the database, add it
            if(!GuildHandler.guildExists(server.getId())) {
                createGuildAndLogsChannel(discordApi, server.getId(), server.getName());
            }
        });
    }
    public static void handleJoinGuildEvent(DiscordApi api) {
        api.addServerJoinListener(event -> {
            System.out.println("Joined guild: " + event.getServer().getName() + " Saving guild...");
            createGuildAndLogsChannel(api, event.getServer().getId(), event.getServer().getName());
        });
    }

    public static void createGuildAndLogsChannel(DiscordApi api, long discordGuildid, String discordGuildName) {
        Guild joinedGuild = new Guild(discordGuildid, true);
        long logsChannelId = -1;
        if((logsChannelId = ifLogsChannelExistsGetId(api, joinedGuild)) == -1) {
            createLogsChannelForGuild(api, joinedGuild, true);
            System.out.println("Created logs channel for guild: " + discordGuildName);
        }
        else {
            Channel logsChannel = new Channel(logsChannelId);
            joinedGuild.setLoggingChannel(logsChannel);
            attemptToSaveGuildTwice(joinedGuild);
            System.out.println("Guild: " + discordGuildName + " saved.");
        }
    }

    public static void attemptToSaveGuildTwice(Guild guild) {
        try {
            GuildHandler.updateGuild(guild);
        }
        catch (IOException | InterruptedException e) {
            try {
                Thread.sleep(1000);
                GuildHandler.updateGuild(guild);
            }
            catch (IOException | InterruptedException e2) {
                System.out.println("Failed to save guild " + guild.getId() + " after 2 attempts.\n Initial Attempt Error: " + e.getMessage() + "\n Second Attempt Error: " + e2.getMessage());
            }
        }
    }
    
    public static void createLogsChannelForGuild(DiscordApi api, Guild guild, boolean tryAgain) {
        try {
            long createdChannelId = ChannelApiHandler.createLogChannel(api, guild.getId(), "logs");
            Channel loggingChannel = new Channel(createdChannelId);
            guild.setLoggingChannel(loggingChannel);
            // remember to update guild, using Guild save method is unsafe in direct use
            GuildHandler.updateGuild(guild);
        }
        catch (Exception e) {
            // recurse
            if(tryAgain) createLogsChannelForGuild(api, guild, false);
            System.out.println("Failed to create logs channel for guild " + guild.getId() + " after 2 attempts.\n Initial Attempt Error: " + e.getMessage());
        }
    }

    public static long ifLogsChannelExistsGetId(DiscordApi api, Guild guild) {
        return api.getServerById(guild.getId()).get().getTextChannelsByNameIgnoreCase("logs").size() > 0 ?
                api.getServerById(guild.getId()).get().getTextChannelsByNameIgnoreCase("logs").get(0).getId() :
                -1;
    }

    public static void handleLeaveGuildEvent(DiscordApi api) {
        api.addServerLeaveListener(event -> {
            System.out.println("Left guild: " + event.getServer().getName() + " Deleting guild...");
            Guild leftGuild = new Guild(event.getServer().getId(), true);
            GuildHandler.deleteGuild(leftGuild);
        });
    }
}
