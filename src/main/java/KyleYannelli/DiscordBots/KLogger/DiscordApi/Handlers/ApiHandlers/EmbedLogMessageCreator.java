package KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.ApiHandlers;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import java.awt.*;

public class EmbedLogMessageCreator {
    public static EmbedBuilder createDeletedMessageEmbedLog(Message deletedMessage, MessageAuthor messageAuthor, User actionUser) {
        return deletedMessage != null ? new EmbedBuilder()
                .setTitle("Deleted A Message")
                .addField("Channel", deletedMessage.getServerTextChannel().get().getName())
                .addField("Author", messageAuthor.getDiscriminatedName())
                .addField("Message", deletedMessage.getContent())
                .setAuthor(actionUser.getDiscriminatedName(), null, "https://" + actionUser.getAvatar().getUrl().getHost() + actionUser.getAvatar().getUrl().getPath())
                .setColor(Color.RED)
                :
                new EmbedBuilder()
                        .setTitle("Deleted A Message")
                        .addField("Unknown", "Message was created before the bot was running. Deleted messages from before the bot was running are not in the cache, therefore the content is not available.")
                        .setAuthor(actionUser.getDiscriminatedName(), null, "https://" + actionUser.getAvatar().getUrl().getHost() + actionUser.getAvatar().getUrl().getPath())
                        .setColor(Color.RED);
    }
}
