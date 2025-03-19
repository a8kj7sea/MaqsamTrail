package me.a8kj.imagebasedonsun.action.actions.change.impl;

import java.io.File;
import java.io.IOException;

import me.a8kj.imagebasedonsun.action.actions.change.BackgroundChanger;

public class WindowsChanger extends BackgroundChanger {

    public WindowsChanger(File backgroundImage) {
        super(backgroundImage);
    }

    @Override
    public String getOperationSystemName() {
        return "windows 10";
    }

    @Override
    public void change() {
        try {
            String filePath = this.getBackgroundImage().getAbsolutePath();
            String command = String
                    .format("REG ADD \"HKCU\\Control Panel\\Desktop\" /V Wallpaper /T REG_SZ /F /D \"%s\"", filePath);
            Process process = Runtime.getRuntime().exec("cmd /c " + command);
            process.waitFor();
            command = "REG ADD \"HKCU\\Control Panel\\Desktop\" /V WallpaperStyle /T REG_SZ /F /D 2";
            process = Runtime.getRuntime().exec("cmd /c " + command);
            process.waitFor();
            command = "RUNDLL32.EXE user32.dll,UpdatePerUserSystemParameters";
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
            System.out.println("Background changed");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

        }
    }

    @Override
    public boolean canChange() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

}
