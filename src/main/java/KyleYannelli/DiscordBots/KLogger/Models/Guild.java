package KyleYannelli.DiscordBots.KLogger.Models;

import java.util.HashMap;

public class Guild extends Model {
    private final long guildId;
    private final boolean isLogging;
    private Channel loggingChannel;

    public Guild(long id, boolean isLogging) {
        super("Guild", id);
        this.guildId = id;
        this.isLogging = isLogging;
        this.loggingChannel = null;
    }

    public Guild(long id, boolean isLogging, Channel loggingChannel) {
        super("Guild", id);
        this.guildId = id;
        this.isLogging = isLogging;
        this.loggingChannel = loggingChannel;
    }

    public long getId() {
        return guildId;
    }

    public boolean isLogging() {
        return isLogging;
    }

    public void setLoggingChannel(Channel loggingChannel) {
        this.loggingChannel = loggingChannel;
    }

    @Override
    public HashMap<String, Object> getDeclaredFields() {
        HashMap<String, Object> declaredFields = new HashMap<>();
        declaredFields.put("GuildDiscordId", this.guildId);
        declaredFields.put("IsLogging", this.isLogging);
        declaredFields.put("LoggingChannelDiscordId", this.loggingChannel == null ? "-1" : this.loggingChannel.getId());
        return declaredFields;
    }
}
