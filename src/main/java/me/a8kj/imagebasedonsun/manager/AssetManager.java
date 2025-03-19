package me.a8kj.imagebasedonsun.manager;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {

    private final static Map<String, File> assets = new HashMap<>();

    public void loadAssets(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("Provided folder path is not valid");
        }

        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            throw new IllegalStateException("No files found in the provided folder");
        }

        for (File file : files) {
            if (file.isFile()) {
                assets.put(file.getName(), file);
            }
        }
    }

    public Map<String, File> getAssets() {
        return Collections.unmodifiableMap(assets);
    }

    public File getAsset(String fileName) {
        return assets.get(fileName);
    }
}
