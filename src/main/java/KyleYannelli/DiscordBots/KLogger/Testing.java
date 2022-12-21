package KyleYannelli.DiscordBots.KLogger;

import KyleYannelli.DiscordBots.KLogger.Models.Guild;
import KyleYannelli.DiscordBots.KLogger.Models.Model;

import java.io.IOException;

public class Testing {
    public static void main(String[] args) throws IOException {
        Model model = new Model("BaseModel");
        model.save();
        Guild guild = new Guild(123456789, true);
        guild.save();
    }
}
