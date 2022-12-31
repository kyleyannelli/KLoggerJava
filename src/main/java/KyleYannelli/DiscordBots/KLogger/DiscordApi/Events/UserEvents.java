package KyleYannelli.DiscordBots.KLogger.DiscordApi.Events;

import KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.ApiHandlers.EmbedLogMessageCreator;
import KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.ModelHandlers.GuildHandler;
import KyleYannelli.DiscordBots.KLogger.Models.Guild;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.user.User;
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
}
