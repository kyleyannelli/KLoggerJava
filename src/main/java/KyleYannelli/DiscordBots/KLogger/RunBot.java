package KyleYannelli.DiscordBots.KLogger;

import KyleYannelli.DiscordBots.KLogger.DiscordApi.Bot;
import KyleYannelli.DiscordBots.KLogger.Parsers.JSONCommandParser;

import java.io.IOException;

public class RunBot {
    public static void main(String[] args) throws IOException {
        Bot bot = new Bot();
//        JSONCommandParser jsonCommandParser = new JSONCommandParser();
//        if(!bot.deleteOldCommandsAndAddNew(jsonCommandParser.parse()))
//            throw new IOException("Error adding commands. This is likely an issue with the JSON file. Please check the JSON file and try again.\n" +
//                    "If you are sure the JSON file is correct, please contact the developer.");
    }
}
