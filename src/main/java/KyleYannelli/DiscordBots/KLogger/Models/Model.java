package KyleYannelli.DiscordBots.KLogger.Models;

import KyleYannelli.DiscordBots.KLogger.LocalStorage.LocalStorage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
public class Model {
    private final String modelName;
    private final long id;

    public Model(String modelName, long id) {
        this.modelName = modelName;
        this.id = id;
    }

    /**
     * @Warning
     * Using this method outside GuildHandler may result in unexpected behavior.
     *  Consider using GuildHandler.updateGuild(Guild guild) instead if you are unsure.
     * @throws IOException
     */
    public void save() throws IOException {
        // create JSONArray
        JSONArray jsonArray = new JSONArray();
        // foreach attribute, add to jsonArray
        HashMap<String, Object> declaredFields = this.getDeclaredFields();
        for(String key : getDeclaredFields().keySet()) {
            JSONObject keyValueJSONObject = new JSONObject();
            keyValueJSONObject.put(key, declaredFields.get(key));
            jsonArray.put(keyValueJSONObject);
        }
        // write JSONArray to file
        LocalStorage.overwriteJSONArrayToFile(jsonArray, id + "." + modelName.toLowerCase() + ".json");
    }

    public HashMap<String, Object> getDeclaredFields() {
        HashMap<String, Object> declaredFields = new HashMap<>();
        declaredFields.put("ModelName", modelName);
        return declaredFields;
    }
}
