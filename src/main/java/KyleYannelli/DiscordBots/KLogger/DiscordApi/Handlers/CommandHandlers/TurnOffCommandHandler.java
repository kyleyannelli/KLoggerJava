package KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.CommandHandlers;

import KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.ModelHandlers.GuildHandler;
import KyleYannelli.DiscordBots.KLogger.Models.Guild;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.callback.InteractionOriginalResponseUpdater;

import java.io.IOException;

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
                        // if user cannot manage server, do not allow them to turn off logging
                        if(!slashCommandCreateEvent
                                .getSlashCommandInteraction()
                                .getServer().get()
                                .hasPermission(slashCommandCreateEvent.getSlashCommandInteraction().getUser(), PermissionType.ADMINISTRATOR)) {
                            interactionAcceptance.setContent("You do not have permission to turn off logging.");
                        }
                        handleInteractionAcceptance(interactionAcceptance, slashCommandCreateEvent.getInteraction());
                    });
        }
    }

    @Override
    public void handleInteractionAcceptance(InteractionOriginalResponseUpdater interactionAcceptance, Interaction commandInteraction) {
        // run non-blocking
        Thread thread = new Thread(() -> {
            // notify user that bot is going to stop listening for interactions/changes on their guild
            interactionAcceptance.setContent("Turning off, please wait...").update();
            
            Guild guild = new Guild(commandInteraction.getServer().get().getId(), false);

            try {
                GuildHandler.updateGuild(guild);
                interactionAcceptance.setContent("Turned off for this guild.\n" +
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
        this.discordApi = null;
    }
}
