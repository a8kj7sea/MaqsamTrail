package me.a8kj.imagebasedonsun.action.actions.change;

import java.io.File;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class BackgroundChanger {

    private final File backgroundImage;

    public abstract String getOperationSystemName();

    public abstract void change();

    public abstract boolean canChange();

    public boolean isValidImageFile() {
        return backgroundImage.isFile() && this.backgroundImage != null;
    }

}
