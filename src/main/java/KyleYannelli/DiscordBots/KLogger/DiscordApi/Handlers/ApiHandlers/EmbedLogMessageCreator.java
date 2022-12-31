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
                .addField("Message", deletedMessage.getContent().length() > 1000 ? deletedMessage.getContent().substring(0, 1000) + "..." : deletedMessage.getContent())
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
                    // old message substring if over 1000 chars
                    .addField("Old Message", oldMessage.getContent().length() > 1000 ? oldMessage.getContent().substring(0, 1000) + "..." : oldMessage.getContent())
                    // new message substring if over 1000 chars
                    .addField("New Message", newMessage.getContent().length() > 1000 ? newMessage.getContent().substring(0, 1000) + "..." : newMessage.getContent())
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
                    // new message substring if over 1000 chars
                    .addField("New Message", newMessage.getContent().length() > 1000 ? newMessage.getContent().substring(0, 1000) + "..." : newMessage.getContent())
                    .setAuthor(actionUser.getDiscriminatedName(), null, "https://" + actionUser.getAvatar().getUrl().getHost() + actionUser.getAvatar().getUrl().getPath())
                    .setColor(Color.YELLOW);
        }
        // if new message is null
        else if(newMessage == null) {
            return new EmbedBuilder()
                    .setTitle("Edited A Message")
                    .addField("Channel", oldMessage.getServerTextChannel().get().getName())
                    .addField("Author", messageAuthor.getDiscriminatedName())
                    // old message substring if over 1000 chars
                    .addField("Old Message", oldMessage.getContent().length() > 1000 ? oldMessage.getContent().substring(0, 1000) + "..." : oldMessage.getContent())
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
