package KyleYannelli.DiscordBots.KLogger.Models;

import KyleYannelli.DiscordBots.KLogger.LocalStorage.LocalStorage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
public class Model {
    private final String modelName;

    public Model(String modelName) {
        this.modelName = modelName;
    }

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
        LocalStorage.appendJSONArrayToFile(jsonArray, modelName + ".json");
    }

    public HashMap<String, Object> getDeclaredFields() {
        HashMap<String, Object> declaredFields = new HashMap<>();
        declaredFields.put("ModelName", modelName);
        return declaredFields;
    }
}
