package KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.CommandHandlers;

import KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.ModelHandlers.GuildHandler;
import KyleYannelli.DiscordBots.KLogger.Models.Guild;
import org.javacord.api.DiscordApi;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.callback.InteractionOriginalResponseUpdater;

import java.io.IOException;

public class TurnOnCommandHandler implements CommandHandler {
    private final String COMMAND_NAME = "turn-on";
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
                        handleInteractionAcceptance(interactionAcceptance, slashCommandCreateEvent.getInteraction());
                    });
        }
    }

    @Override
    public void handleInteractionAcceptance(InteractionOriginalResponseUpdater interactionAcceptance, Interaction commandInteraction) {
        // run non-blocking
        Thread thread = new Thread(() -> {
            // notify user that bot is going to start listening for interactions/changes on their guild
            interactionAcceptance.setContent("Turning on, please wait...").update();

            Guild guild = new Guild(commandInteraction.getServer().get().getId(), true);

            try {
                GuildHandler.updateGuild(guild);
                interactionAcceptance.setContent("Turned on for this guild.\n" +
                        "Reminder: k_logger does not store any information.\n" +
                        "if you would like to delete the \"logs\", then use /nuke or delete the logs channel :)").update();
            } catch (IOException e) {
                interactionAcceptance.setContent("There was an error reading or updating the data for this guild.\n" +
                        "Please contact the developer if this occurs again. In the meantime try the command again.").update();
            } catch (InterruptedException e) {
                interactionAcceptance.setContent("There was an error with a thread.\n" +
                        "Please contact the developer if this occurs again. In the meantime try the command again.").update();
            }

            this.dispose();
        });
        thread.start();
    }

    @Override
    public void dispose() {

    }
}
