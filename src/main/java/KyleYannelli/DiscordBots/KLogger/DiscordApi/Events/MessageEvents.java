package KyleYannelli.DiscordBots.KLogger.DiscordApi.Events;

import KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.ApiHandlers.EmbedLogMessageCreator;
import KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.ModelHandlers.GuildHandler;
import KyleYannelli.DiscordBots.KLogger.Models.Guild;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageDeleteEvent;

public class MessageEvents {
    public static void listenMessageDeletionEvent(DiscordApi api) {
        api.addMessageDeleteListener(event -> {
            handleMessageDeletionEvent(api, event);
        });
    }

    private static void handleMessageDeletionEvent(DiscordApi api, MessageDeleteEvent event) {
        Thread thread = new Thread(() -> {
            try {
                User actionUser = api
                        .getServerById(
                                event.getServer().get()
                                        .getId()
                        ).get()
                        .getAuditLog(1).get()
                        .getEntries().get(0)
                        .getUser().get();
                Message deletedMessage = event.getMessage().orElse(null);
                Guild guild = GuildHandler.getGuild(event.getServer().get().getId());
                EmbedBuilder embedBuilder = EmbedLogMessageCreator
                        .createDeletedMessageEmbedLog(
                                deletedMessage, deletedMessage != null ? deletedMessage.getAuthor() : null, actionUser
                        );
                if(guild.isLogging() && guild.getLoggingChannelId() != -1) {
                    api.getTextChannelById(guild.getLoggingChannelId()).get()
                            .sendMessage(embedBuilder);
                }
            }
            catch (Exception e) {
                System.out.println("Error handling message delete " + e.getMessage());
            }
        });
        thread.start();
    }
}
