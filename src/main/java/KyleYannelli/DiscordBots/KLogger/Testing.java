package KyleYannelli.DiscordBots.KLogger;

import KyleYannelli.DiscordBots.KLogger.Models.Guild;
import KyleYannelli.DiscordBots.KLogger.Parsers.GuildsParser.GuildsParser;

import java.io.IOException;
import java.util.ArrayList;

public class Testing {
    public static void main(String[] args) throws IOException {
        ArrayList<Guild> guilds = GuildsParser.parseGuilds();
    }
}
