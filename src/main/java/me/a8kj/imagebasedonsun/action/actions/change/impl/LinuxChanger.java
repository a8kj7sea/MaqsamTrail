package me.a8kj.imagebasedonsun.action.actions.change.impl;

import java.io.File;
import java.io.IOException;

import me.a8kj.imagebasedonsun.action.actions.change.BackgroundChanger;

public class LinuxChanger extends BackgroundChanger {

    public LinuxChanger(File backgroundImage) {
        super(backgroundImage);
    }

    @Override
    public String getOperationSystemName() {
        return "linux";
    }

    @Override
    public void change() {
        try {
            String filePath = this.getBackgroundImage().getAbsolutePath();
            String command = String.format("gsettings set org.gnome.desktop.background picture-uri 'file://%s'",
                    filePath);
            Process process = Runtime.getRuntime().exec(new String[] { "bash", "-c", command });
            process.waitFor();
            System.out.println("Background changed");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

        }
    }

    @Override
    public boolean canChange() {
        return System.getProperty("os.name").toLowerCase().contains("nux")
                || System.getProperty("os.name").toLowerCase().contains("nix");
    }

}
