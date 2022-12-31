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
                .addField("Message", deletedMessage.getContent().substring(0, 1000) + "...")
                .setAuthor(actionUser.getDiscriminatedName(), null, "https://" + actionUser.getAvatar().getUrl().getHost() + actionUser.getAvatar().getUrl().getPath())
                .setColor(Color.RED)
                :
                new EmbedBuilder()
                        .setTitle("Deleted A Message")
                        .addField("Unknown", "Message was not in cache, so the bot was unable to retrieve any data.")
                        .setAuthor(actionUser.getDiscriminatedName(), null, "https://" + actionUser.getAvatar().getUrl().getHost() + actionUser.getAvatar().getUrl().getPath())
                        .setColor(Color.RED);
    }

    public static EmbedBuilder createEditedMessageEmbedLog(Message oldMessage, Message newMessage, MessageAuthor messageAuthor, User actionUser) {
        // if old message && new message are not null
        if(oldMessage != null && newMessage != null) {
            return new EmbedBuilder()
                    .setTitle("Edited A Message")
                    .addField("Channel", oldMessage.getServerTextChannel().get().getName())
                    .addField("Author", messageAuthor.getDiscriminatedName())
                    .addField("Old Message", oldMessage.getContent().substring(0, 1000) + "...")
                    .addField("New Message", newMessage.getContent().substring(0, 1000) + "...")
                    .setAuthor(actionUser.getDiscriminatedName(), null, "https://" + actionUser.getAvatar().getUrl().getHost() + actionUser.getAvatar().getUrl().getPath())
                    .setColor(Color.YELLOW);
        }
        // if old message is null
        else if(oldMessage == null) {
            return new EmbedBuilder()
                    .setTitle("Edited A Message")
                    .addField("Channel", newMessage.getServerTextChannel().get().getName())
                    .addField("Author", messageAuthor.getDiscriminatedName())
                    .addField("Old Message", "Message was not in cache, so the bot was unable to retrieve any data.")
                    .addField("New Message", newMessage.getContent().substring(0, 1000) + "...")
                    .setAuthor(actionUser.getDiscriminatedName(), null, "https://" + actionUser.getAvatar().getUrl().getHost() + actionUser.getAvatar().getUrl().getPath())
                    .setColor(Color.YELLOW);
        }
        // if new message is null
        else if(newMessage == null) {
            return new EmbedBuilder()
                    .setTitle("Edited A Message")
                    .addField("Channel", oldMessage.getServerTextChannel().get().getName())
                    .addField("Author", messageAuthor.getDiscriminatedName())
                    .addField("Old Message", oldMessage.getContent().substring(0, 1000) + "...")
                    .addField("New Message", "Message was not in cache, so the bot was unable to retrieve any data.")
                    .setAuthor(actionUser.getDiscriminatedName(), null, "https://" + actionUser.getAvatar().getUrl().getHost() + actionUser.getAvatar().getUrl().getPath())
                    .setColor(Color.YELLOW);
        }
        else {
            return new EmbedBuilder()
                    .setTitle("Edited A Message")
                    .addField("Unknown", "Message was not in cache, so the bot was unable to retrieve any data.")
                    .setAuthor(actionUser.getDiscriminatedName(), null, "https://" + actionUser.getAvatar().getUrl().getHost() + actionUser.getAvatar().getUrl().getPath())
                    .setColor(Color.YELLOW);
        }
    }
}
