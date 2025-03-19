package me.a8kj.imagebasedonsun;

import me.a8kj.imagebasedonsun.app.AppFacade;
import me.a8kj.imagebasedonsun.app.ImageBasedOnSunApp;

// btw am too weak in dsa 
public class Main {

    private static AppFacade appFacade;

    public static void main(String[] args) {
        appFacade = new ImageBasedOnSunApp();
        try {
            appFacade.onEnable(args);
        } catch (Exception e) {
            e.printStackTrace();
            appFacade.onDisable(args);
        }
    }
}
