package KyleYannelli.DiscordBots.KLogger.Parsers.GuildsParser;

import KyleYannelli.DiscordBots.KLogger.LocalStorage.LocalStorage;
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
            Guild currentGuild = new Guild(currentGuildId, currentGuildIsLogging);
            guilds.add(currentGuild);
        }
        return guilds;
    }

    public static Guild parseGuild(long guildId) throws IOException {
        String filePath = folderPath + guildId + ".guild.json";
        JSONArray guildJsonArray = new JSONArray(LocalStorage.loadFileToString(filePath));
        Long currentGuildId = guildJsonArray.getJSONArray(0).getJSONObject(0).getLong("GuildDiscordId");
        Boolean currentGuildIsLogging = guildJsonArray.getJSONArray(0).getJSONObject(1).getBoolean("IsLogging");
        return new Guild(currentGuildId, currentGuildIsLogging);
    }
}
