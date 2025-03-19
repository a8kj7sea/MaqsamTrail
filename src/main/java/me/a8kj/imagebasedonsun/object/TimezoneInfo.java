package me.a8kj.imagebasedonsun.object;

import lombok.Data;

@Data
public class TimezoneInfo {

    private boolean dstExist;
    private boolean dstEnabled;

    private double offset;
    private String timezone;

    public boolean hasDST() {
        return dstExist;
    }
}
