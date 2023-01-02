package KyleYannelli.DiscordBots.KLogger.Parsers;

import KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.ModelHandlers.GuildHandler;
import KyleYannelli.DiscordBots.KLogger.LocalStorage.LocalStorage;
import KyleYannelli.DiscordBots.KLogger.Models.Channel;
import KyleYannelli.DiscordBots.KLogger.Models.Guild;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

public class GuildsParser {
    private static final String folderPath = "src/main/resources/";
    private static final String guildJsonPath = folderPath + "Guild.json";
    public static ArrayList<Guild> parseGuilds() throws IOException {
        JSONArray guildsJsonArray = new JSONArray(LocalStorage.loadFileToString(guildJsonPath));
        ArrayList<Guild> guilds = new ArrayList<>();
        for(int i = 0; i < guildsJsonArray.length(); i++){
            Long currentGuildId = guildsJsonArray.getJSONArray(i).getJSONObject(0).getLong("GuildDiscordId");
            Boolean currentGuildIsLogging = guildsJsonArray.getJSONArray(i).getJSONObject(1).getBoolean("IsLogging");
            Channel currentGuildLoggingChannel = new Channel(guildsJsonArray.getJSONArray(i).getJSONObject(2).getLong("LoggingChannelDiscordId"));
            Guild currentGuild = new Guild(currentGuildId, currentGuildIsLogging, currentGuildLoggingChannel);
            guilds.add(currentGuild);
        }
        return guilds;
    }

    public static Guild parseGuild(long guildId) throws IOException, InterruptedException {
        String filePath = folderPath + guildId + ".guild.json";

        while(!GuildHandler.canUseGuild(guildId)); // wait until guild is available


        JSONArray guildJsonArray = new JSONArray(LocalStorage.loadFileToString(filePath));
        Long currentGuildId = guildJsonArray.getJSONObject(0).getLong("GuildDiscordId");
        Boolean currentGuildIsLogging = guildJsonArray.getJSONObject(2).getBoolean("IsLogging");
        Channel currentGuildLoggingChannel = new Channel(guildJsonArray.getJSONObject(1).getLong("LoggingChannelDiscordId"));
        return new Guild(currentGuildId, currentGuildIsLogging, currentGuildLoggingChannel);
    }
}
