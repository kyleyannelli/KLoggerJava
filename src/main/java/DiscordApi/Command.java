package DiscordApi;

import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandOption;

import java.util.ArrayList;

public class Command {
    private final String name;
    private final String description;
    private final ArrayList<SlashCommandOption> options;
    private final boolean forAdmins;

    public Command(String name, String description, ArrayList<SlashCommandOption> options, boolean forAdmins) {
        this.name = name;
        this.description = description;
        this.options = options;
        this.forAdmins = forAdmins;
    }

    public SlashCommandBuilder getSlashCommandBuilder() {
        return forAdmins ?
                new SlashCommandBuilder()
                        .setDefaultEnabledForPermissions(PermissionType.ADMINISTRATOR, PermissionType.BAN_MEMBERS)
                        .setName(this.name)
                        .setDescription(this.description)
                        .setOptions(this.options)
                :
                new SlashCommandBuilder()
                        .setName(this.name)
                        .setDescription(this.description)
                        .setOptions(this.options);
    }
}
