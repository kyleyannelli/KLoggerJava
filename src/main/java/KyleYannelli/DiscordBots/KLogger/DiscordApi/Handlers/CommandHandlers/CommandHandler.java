package KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.CommandHandlers;

import org.javacord.api.DiscordApi;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.callback.InteractionOriginalResponseUpdater;

public interface CommandHandler {
    void handle(DiscordApi discordApi);
    void handleSlashCommandCreateEvent(SlashCommandCreateEvent slashCommandCreateEvent);
    void handleInteractionAcceptance(InteractionOriginalResponseUpdater interactionAcceptance);
    void dispose();
}
