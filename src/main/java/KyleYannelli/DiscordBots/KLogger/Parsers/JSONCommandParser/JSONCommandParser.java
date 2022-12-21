package KyleYannelli.DiscordBots.KLogger.Parsers.JSONCommandParser;

import KyleYannelli.DiscordBots.KLogger.DiscordApi.Command;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import org.json.JSONArray;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JSONCommandParser {
    private final String pathWithFileName;
    public JSONCommandParser() {
        this.pathWithFileName = "src/main/resources/DefaultCommands.json";
    }

    public JSONCommandParser(String pathWithFileName) {
        this.pathWithFileName = pathWithFileName;
    }

    public ArrayList<SlashCommandBuilder> parse() throws IOException {
        // load json from file path with org.json
        String jsonString = new String(Files.readAllBytes(Paths.get(pathWithFileName)));
        JSONArray commandsJsonArray = new JSONArray(jsonString);

        // create array list of slash command builders
        ArrayList<SlashCommandBuilder> slashCommandBuilders = new ArrayList<>();

        // loop through each command in json array
        for(int i = 0; i < commandsJsonArray.length(); i++) {
            // parse first three, this is required, so not going to do error handling because that would mean json is invalid
            String commandName = commandsJsonArray.getJSONObject(i).getString("name");
            String commandDescription = commandsJsonArray.getJSONObject(i).getString("description");
            boolean forAdmins = commandsJsonArray.getJSONObject(i).getBoolean("forAdmins");

            // parse options
            JSONArray optionsJsonArray = commandsJsonArray.getJSONObject(i).getJSONArray("options");
            // javacord options are set via list, lets use arraylist
            ArrayList<SlashCommandOption> options = new ArrayList<>();
            for(int j = 0; j < optionsJsonArray.length(); j++) {
                // parse option
                String optionName = optionsJsonArray.getJSONObject(j).getString("name");
                String optionDescription = optionsJsonArray.getJSONObject(j).getString("description");
                String optionType = optionsJsonArray.getJSONObject(j).getString("dataType");
                boolean optionRequired = optionsJsonArray.getJSONObject(j).getBoolean("required");
                // create option
                SlashCommandOption option = SlashCommandOption.create(
                        parseOptionTypeFromString(optionType),
                        optionName,
                        optionDescription,
                        optionRequired
                );
                // add option to list
                options.add(option);
            }
            // create command via the wrapper class
            Command command = new Command(commandName, commandDescription, options, forAdmins);
            slashCommandBuilders.add(command.getSlashCommandBuilder());
        }

        // return array list of slash command builders
        return slashCommandBuilders;
    }

    private SlashCommandOptionType parseOptionTypeFromString(String typeString) {
        switch (typeString.toUpperCase()) {
            case "STRING" -> {
                return SlashCommandOptionType.STRING;
            }
            case "LONG" -> {
                return SlashCommandOptionType.LONG;
            }
            case "BOOLEAN" -> {
                return SlashCommandOptionType.BOOLEAN;
            }
            case "DECIMAL" -> {
                return SlashCommandOptionType.DECIMAL;
            }
            case "USER" -> {
                return SlashCommandOptionType.USER;
            }
            case "CHANNEL" -> {
                return SlashCommandOptionType.CHANNEL;
            }
            case "ROLE" -> {
                return SlashCommandOptionType.ROLE;
            }
        }
        throw new IllegalArgumentException("Invalid option type in JSON file. Please check your formatting with the documentation.");
    }
}
