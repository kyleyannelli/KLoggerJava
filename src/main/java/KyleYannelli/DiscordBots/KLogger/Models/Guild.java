package KyleYannelli.DiscordBots.KLogger.Models;

import java.util.HashMap;

public class Guild extends Model {
    private final long id;
    private final boolean isLogging;

    public Guild(long id, boolean isLogging) {
        super("Guild");
        this.id = id;
        this.isLogging = isLogging;
    }

    public long getId() {
        return id;
    }

    public boolean isLogging() {
        return isLogging;
    }

    @Override
    public HashMap<String, Object> getDeclaredFields() {
        HashMap<String, Object> declaredFields = new HashMap<>();
        declaredFields.put("GuildDiscordId", this.id);
        declaredFields.put("IsLogging", this.isLogging);
        return declaredFields;
    }
}
