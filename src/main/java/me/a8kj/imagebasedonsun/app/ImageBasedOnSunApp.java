package me.a8kj.imagebasedonsun.app;

import java.io.*;
import java.util.stream.Collectors;

import me.a8kj.imagebasedonsun.object.GeoPosition;
import me.a8kj.imagebasedonsun.request.APIRequest;
import me.a8kj.imagebasedonsun.request.exception.APIRequestException;
import me.a8kj.imagebasedonsun.request.exception.InvalidAPIParameters;
import me.a8kj.imagebasedonsun.request.impl.TimeZoneAPIRequest;
import me.a8kj.imagebasedonsun.util.GeneralUtils;
import me.a8kj.json.A8kjJson;
import me.a8kj.json.impl.SimpleJsonObject;
import me.a8kj.json.object.JsonObject;
import me.a8kj.json.values.JsonBoolean;
import me.a8kj.json.values.JsonString;

public class ImageBasedOnSunApp implements AppFacade {

    private static JsonObject config;

    @Override
    public void onEnable(String[] args) {
        try {
            if (args.length != 2) {
                System.err.println(
                        "Incorrect usage. Please use: java -jar %name% <latitude> <longitude>".replace("%name%",
                                GeneralUtils.getFinalFileName(this)));
                return;
            }

            config = loadConfig();

            double latitude = parseDouble(args[0], "latitude");
            double longitude = parseDouble(args[1], "longitude");

            if (latitude < -90 || latitude > 90) {
                System.err.println("Invalid latitude value: " + latitude + ". Latitude must be between -90 and 90.");
                return;
            }

            if (longitude < -180 || longitude > 180) {
                System.err
                        .println("Invalid longitude value: " + longitude + ". Longitude must be between -180 and 180.");
                return;
            }

            APIRequest timezoneRequest = new TimeZoneAPIRequest(new GeoPosition(latitude, longitude), getApiKey());

            try {
                timezoneRequest.sendRequest("https://api.ipgeolocation.io");
            } catch (APIRequestException | InvalidAPIParameters e) {
                e.printStackTrace();
            }

            createImagesFolder();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable(String[] args) {
        System.out.println("Disabling ImageBasedOnSunApp.");
    }

    private static JsonObject loadConfig() throws IOException {
        File configFile = new File("settings.json");

        if (!configFile.exists()) {
            if (configFile.createNewFile()) {
                JsonObject defaultConfig = new SimpleJsonObject();
                defaultConfig.put("action", new JsonString("mix"));
                defaultConfig.put("apiKey", new JsonString("fc873fadfa8347e7bf777e51c8783ce8"));
                defaultConfig.put("debugging-mode", new JsonBoolean(false));
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
                    writer.write(defaultConfig.asString());
                }
            } else {
                throw new IOException("Failed to create settings.json.");
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String jsonContent = reader.lines().collect(Collectors.joining());
            return A8kjJson.parse(jsonContent)
                    .orElseThrow(() -> new IOException("Failed to parse JSON."));
        }
    }

    private void createImagesFolder() {
        File imagesDir = new File(System.getProperty("user.dir"), "images");

        if (!imagesDir.exists() && !imagesDir.mkdirs()) {
            throw new IllegalStateException("Failed to create images directory.");
        }

        if (imagesDir.listFiles() == null || imagesDir.list().length == 0) {
            System.out.println("No images found in the 'images' directory.");
        } else {
            System.out.println("Images found in the 'images' directory.");
        }
    }

    public static File getImageFileByName(String imageName) {
        File imagesDir = new File(System.getProperty("user.dir"), "images");
        File imageFile = new File(imagesDir, imageName);

        if (!imageFile.exists()) {
            System.out.println("Image not found: " + imageName);
            return null;
        }

        return imageFile;
    }

    private double parseDouble(String value, String paramName) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid " + paramName + " value: " + value);
        }
    }

    public static String getAction() {
        return config != null && config.get("action") != null ? ((JsonString) config.get("action")).getValue()
                : "ERROR YA NOOB?";
    }

    public static String getApiKey() {
        return config != null && config.get("apiKey") != null ? ((JsonString) config.get("apiKey")).getValue()
                : "ERROR YA NOOB?";
    }

    public static boolean isDebuggingModeEnabled() {
        return config != null && config.get("debugging-mode") != null
                ? ((JsonBoolean) config.get("debugging-mode")).asBoolean()
                : false;
    }
}
