package me.a8kj.imagebasedonsun.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GeneralUtils {

    public static String getFinalFileName(Object object) {
        String filePath = object.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        return filePath.substring(filePath.lastIndexOf("/") + 1);
    }

}
