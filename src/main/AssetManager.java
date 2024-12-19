package main;

import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.io.File;
import java.util.HashMap;

public class AssetManager {

    public static final HashMap<String, PImage> images = new HashMap<>();

    public static void load() {
        File[] image_files = Main.applet.listFiles("assets/images");
        for(File file : image_files) {
            images.put(file.getName().substring(0, file.getName().indexOf('.')), Main.applet.loadImage(file.getPath()));
        }
        JSONArray subimages = Main.applet.loadJSONArray("assets/subimages.json");
        for(int i = 0; i < subimages.size(); i++) {
            JSONObject subimage = subimages.getJSONObject(i);
            images.put(subimage.getString("name"), images.get(subimage.getString("source")).get(subimage.getInt("x"), subimage.getInt("y"), subimage.getInt("w"), subimage.getInt("h")));
        }
    }

}
