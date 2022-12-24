package KyleYannelli.DiscordBots.KLogger.DiscordApi.Handlers.ApiHandlers;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.PermissionsBuilder;
import org.javacord.api.entity.permission.Role;

import java.util.concurrent.atomic.AtomicLong;

public class ChannelApiHandler {
    public static long createLogChannel(DiscordApi api, long guildId, String channelName){
        Role everyoneRole = api.getServerById(guildId).get().getEveryoneRole();

        AtomicLong createdLoggingChannelId = new AtomicLong();
        api.getServerById(guildId).get()
                .createTextChannelBuilder()
                .setName(channelName)
                .setAuditLogReason("k_logger: Creating logging channel")
                .addPermissionOverwrite(everyoneRole, new PermissionsBuilder().setDenied(PermissionType.SEND_MESSAGES, PermissionType.SEND_MESSAGES_IN_THREADS, PermissionType.CREATE_PUBLIC_THREADS, PermissionType.CREATE_PRIVATE_THREADS).build())
                .addPermissionOverwrite(api.getYourself(), new PermissionsBuilder().setAllowed(PermissionType.SEND_MESSAGES).build())
                .create()
                .thenAccept(channel -> {
                    System.out.println("Created channel " + channelName + " with id " + channel.getId());
                    createdLoggingChannelId.set(channel.getId());
                });
        return createdLoggingChannelId.get();
    }
}
