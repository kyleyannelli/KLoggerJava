package KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.CommandHandlers;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.callback.InteractionOriginalResponseUpdater;

public class SetLogChannelCommandHandler implements CommandHandler {
    private final String COMMAND_NAME = "set-log-channel";
    private DiscordApi discordApi;


    @Override
    public void handle(DiscordApi discordApi) {
        discordApi.addSlashCommandCreateListener(event -> {
            // print out event slash command name
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
            // notify user that bot is going to stop listening for interactions/changes on their guild
            interactionAcceptance.setContent("Setting log channel, please wait...").update();
            for(SlashCommandInteractionOption option : commandInteraction.asSlashCommandInteraction().get().getArguments()) {
                if(option.getName().equals("channel")) {
                    handleChannelSetting(interactionAcceptance, option);
                }
            }
            dispose();
        });
        thread.start();
    }

    private void handleChannelSetting(InteractionOriginalResponseUpdater interactionAcceptance, SlashCommandInteractionOption option) {
        if(isTextChannel(option.getChannelValue().get())) {

        }
        else {
            interactionAcceptance.setContent("The channel you specified is not a text channel.").update();
        }
    }

    public boolean isTextChannel(ServerChannel channel) {
        return channel.getType().equals(ChannelType.SERVER_TEXT_CHANNEL);
    }

    @Override
    public void dispose() {
        this.discordApi = null;
    }
}
