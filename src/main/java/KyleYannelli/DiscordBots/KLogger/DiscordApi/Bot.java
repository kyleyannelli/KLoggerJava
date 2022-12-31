package KyleYannelli.DiscordBots.KLogger.DiscordApi;

import KyleYannelli.DiscordBots.KLogger.DiscordApi.Events.GuildEvents;
import KyleYannelli.DiscordBots.KLogger.DiscordApi.Events.MessageEvents;
import KyleYannelli.DiscordBots.KLogger.DiscordApi.Events.UserEvents;
import KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.CommandHandlers.SetLogChannelCommandHandler;
import KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.CommandHandlers.TurnOffCommandHandler;
import KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.CommandHandlers.TurnOnCommandHandler;
import io.github.cdimascio.dotenv.Dotenv;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.interaction.SlashCommandBuilder;

import java.util.ArrayList;
import java.util.HashSet;

public class Bot {
    private final DiscordApi discordApi;
    public Bot() {
        // get DISCORD_BOT_TOKEN from dotenv (.env located in root of project)
        String token = Dotenv.load().get("DISCORD_BOT_TOKEN");

        this.discordApi = new DiscordApiBuilder()
                .setToken(token)
                // since we are logging all activity we are requiring all intents
                .setAllIntents()
                .login().join();

        // add event listeners
        addListenersAndEventHandlers();
    }

    private void addListenersAndEventHandlers() {
        // handle commands
        TurnOffCommandHandler turnOffCommandHandler = new TurnOffCommandHandler();
        turnOffCommandHandler.handle(discordApi);

        TurnOnCommandHandler turnOnCommandHandler = new TurnOnCommandHandler();
        turnOnCommandHandler.handle(discordApi);

        SetLogChannelCommandHandler setLogChannelCommandHandler = new SetLogChannelCommandHandler();
        setLogChannelCommandHandler.handle(discordApi);

        /**
         * Handle Events
         */
        // guilds
        GuildEvents.handleJoinGuildEvent(discordApi);
        GuildEvents.handleLeaveGuildEvent(discordApi);
        GuildEvents.handleBotStartUp(discordApi);

        // messages
        MessageEvents.listenMessageDeletionEvent(discordApi);
        MessageEvents.listenMessageEditEvent(discordApi);

        // users
        UserEvents.listenUserChangeNicknameEvent(discordApi);
    }

    public boolean deleteOldCommandsAndAddNew(ArrayList<SlashCommandBuilder> slashCommandArrayList) {
        try {
            // bulk overwrite all commands by passing empty set
            this.discordApi.bulkOverwriteGlobalApplicationCommands(new HashSet<>()).join();

            // add new commands
            for(SlashCommandBuilder slashCommandBuilder : slashCommandArrayList) {
                slashCommandBuilder.createGlobal(this.discordApi).join();
            }

            return true;
        } catch (Exception e) {
            System.out.println("Error adding commands: " + e);
            return false;
        }
    }

    public DiscordApi getDiscordApi() {
        return this.discordApi;
    }
}
