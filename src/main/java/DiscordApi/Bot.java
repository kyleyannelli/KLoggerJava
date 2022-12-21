package DiscordApi;

import io.github.cdimascio.dotenv.Dotenv;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.interaction.SlashCommandBuilder;

import java.util.ArrayList;

public class Bot {
    private final DiscordApi discordApi;
    public Bot() {
        // get DISCORD_BOT_TOKEN from dotenv (.env located in root of project)
        String token = Dotenv.load().get("DISCORD_BOT_TOKEN");

        // create a new DiscordApi instance
        this.discordApi = new DiscordApiBuilder()
                .setToken(token)
                // since we are logging all activity we are requiring all intents
                .setAllIntents()
                .login().join();
    }

    public boolean deleteOldCommandsAndAddNew(ArrayList<SlashCommandBuilder> slashCommandArrayList) {
        try {
            // bulk overwrite all commands by passing empty set
            this.discordApi.bulkOverwriteGlobalApplicationCommands(null).join();

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
}
