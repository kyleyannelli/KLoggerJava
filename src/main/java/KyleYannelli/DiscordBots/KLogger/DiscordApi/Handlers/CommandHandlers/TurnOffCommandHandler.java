package KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.CommandHandlers;

import org.javacord.api.DiscordApi;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.callback.InteractionOriginalResponseUpdater;

public class TurnOffCommandHandler implements CommandHandler {
    private final String COMMAND_NAME = "turn-off";
    private DiscordApi discordApi;
    @Override
    public void handle(DiscordApi discordApi) {
        discordApi.addSlashCommandCreateListener(event -> {
            this.discordApi = discordApi;
            handleSlashCommandCreateEvent(event);
        });
    }

    @Override
    public void handleSlashCommandCreateEvent(SlashCommandCreateEvent slashCommandCreateEvent) {
        if(slashCommandCreateEvent.getSlashCommandInteraction().getCommandName().equals(COMMAND_NAME)) {
            slashCommandCreateEvent.getInteraction()
                    // behavior of responses are always private
                    .respondLater(true)
                    .thenAccept(interactionAcceptance -> {
                        handleInteractionAcceptance(interactionAcceptance);
                    });
        }
    }

    @Override
    public void handleInteractionAcceptance(InteractionOriginalResponseUpdater interactionAcceptance) {

    }

    @Override
    public void dispose() {
        this.discordApi = null;
    }
}
