package KyleYannelli.DiscordBots.KLogger.LocalStorage;

import org.json.JSONArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LocalStorage {
    static final String DEFAULT_FILE_PATH = "src/main/resources/";
    public static boolean fileExists(String filePathName) {
        // check if src/main/resources/Guilds.json exists
        File f = new File(filePathName);
        return (f.exists() && !f.isDirectory());
    }

    public static boolean directoryExists(String directoryPath) {
        // check if src/main/resources exists
        File f = new File(directoryPath);
        return (f.exists() && f.isDirectory());
    }

    public static boolean guildsFileExists() {
        // check if src/main/resources/Guilds.json exists
        return fileExists(DEFAULT_FILE_PATH + "Guilds.json");
    }

    public static void appendJSONArrayToFile(JSONArray jsonArray, String fileNameWithExtension) throws IOException {
        if(fileExists(DEFAULT_FILE_PATH + fileNameWithExtension)) {
            String jsonString = loadFileToString(DEFAULT_FILE_PATH + fileNameWithExtension);
            JSONArray exisitingJSONArray = new JSONArray(jsonString);
            JSONArray mergedJSONArray = exisitingJSONArray.put(jsonArray);
            writeJSONArrayToFile(mergedJSONArray, fileNameWithExtension);
        }
        else {
            JSONArray fakerJSONArray = new JSONArray();
            fakerJSONArray.put(jsonArray);
            writeJSONArrayToFile(fakerJSONArray, fileNameWithExtension);
        }
    }

    public static String loadFileToString(String completeFilePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(completeFilePath)));
    }

    private static void writeJSONArrayToFile(JSONArray jsonArray, String fileNameWithExtension) {
        // write JSONArray to file
        try (FileWriter file = new FileWriter(DEFAULT_FILE_PATH + fileNameWithExtension)) {
            file.write(jsonArray.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
