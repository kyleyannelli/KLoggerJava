package KyleYannelli.DiscordBots.KLogger.Models;

import java.util.HashMap;

public class Channel extends Model {
    private final long discordId;

    public Channel(long discordId) {
        super("Channel", discordId);
        this.discordId = discordId;
    }

    public long getId() {
        return discordId;
    }

    @Override
    public HashMap<String, Object> getDeclaredFields() {
        HashMap<String, Object> declaredFields = new HashMap<>();
        declaredFields.put("ChannelDiscordId", this.discordId);
        return declaredFields;
    }
}
