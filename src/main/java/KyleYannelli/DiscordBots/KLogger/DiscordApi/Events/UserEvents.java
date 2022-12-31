package KyleYannelli.DiscordBots.KLogger.DiscordApi.Events;

import KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.ApiHandlers.EmbedLogMessageCreator;
import KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.ModelHandlers.GuildHandler;
import KyleYannelli.DiscordBots.KLogger.Models.Guild;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.event.server.member.ServerMemberLeaveEvent;
import org.javacord.api.event.user.UserChangeNicknameEvent;

public class UserEvents {
    public static void listenUserChangeNicknameEvent(DiscordApi api) {
        api.addUserChangeNicknameListener(event -> {
            handleUserChangeNicknameEvent(api, event);
        });
    }

    private static void handleUserChangeNicknameEvent(DiscordApi discordApi, UserChangeNicknameEvent event) {
        Thread thread = new Thread(() -> {
            try {
                Guild guild = GuildHandler.getGuild(event.getServer().getId());
                if(guild.isLogging() && guild.getLoggingChannelId() != -1) {
                    discordApi.getTextChannelById(guild.getLoggingChannelId()).get()
                            .sendMessage(
                                    EmbedLogMessageCreator.createChangedNicknameEmbedLog(event)
                            );
                }
            }
            catch (Exception e) {
                System.out.println("Error handling user change nickname " + e.getMessage());
            }
        });
        thread.start();
    }

    public static void listenUserJoinEvent(DiscordApi api) {
        api.addServerMemberJoinListener(event -> {
            handleUserJoinEvent(api, event);
        });
    }

    private static void handleUserJoinEvent(DiscordApi discordApi, ServerMemberJoinEvent event) {
        Thread thread = new Thread(() -> {
            try {
                Guild guild = GuildHandler.getGuild(event.getServer().getId());
                if(guild.isLogging() && guild.getLoggingChannelId() != -1) {
                    discordApi.getTextChannelById(guild.getLoggingChannelId()).get()
                            .sendMessage(
                                    EmbedLogMessageCreator.createJoinedServerEmbedLog(event)
                            );
                }
            }
            catch (Exception e) {
                System.out.println("Error handling user join " + e.getMessage());
            }
        });
        thread.start();
    }

    public static void listenUserLeaveEvent(DiscordApi api) {
        api.addServerMemberLeaveListener(event -> {
            handleUserLeaveEvent(api, event);
        });
    }

    private static void handleUserLeaveEvent(DiscordApi discordApi, ServerMemberLeaveEvent event) {
        Thread thread = new Thread(() -> {
            try {
                Guild guild = GuildHandler.getGuild(event.getServer().getId());
                if(guild.isLogging() && guild.getLoggingChannelId() != -1) {
                    discordApi.getTextChannelById(guild.getLoggingChannelId()).get()
                            .sendMessage(
                                    EmbedLogMessageCreator.createLeaveServerEmbedLog(event)
                            );
                }
            }
            catch (Exception e) {
                System.out.println("Error handling user leave " + e.getMessage());
            }
        });
        thread.start();
    }
}
