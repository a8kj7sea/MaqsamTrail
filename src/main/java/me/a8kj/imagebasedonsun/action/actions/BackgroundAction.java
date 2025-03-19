package me.a8kj.imagebasedonsun.action.actions;

import java.util.logging.Logger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.imagebasedonsun.action.Action;
import me.a8kj.imagebasedonsun.action.actions.change.BackgroundChanger;

@RequiredArgsConstructor
@Getter
@Deprecated
public class BackgroundAction implements Action {

    private static final Logger logger = Logger.getLogger(BackgroundAction.class.getName());

    private final BackgroundChanger backgroundChanger;
    private final boolean debugging;

    @Override
    public void perform() {
        logger.info("Performing background action.");

        if (!backgroundChanger.canChange()) {
            logger.severe("Background changer cannot perform the change. Operation is unsupported.");
            throw new UnsupportedOperationException("Background change is not supported.");
        }

        if (!backgroundChanger.isValidImageFile()) {
            logger.severe("Invalid background image, cannot set it!");
            throw new IllegalArgumentException("Invalid Background image");
        }

        try {
            backgroundChanger.change();

            if (debugging) {
                logger.info("Background change attempt completed.");
            }

        } catch (Exception e) {
            logger.severe("An error occurred while changing the background: " + e.getMessage());
            logger.severe("Error details: ");
            e.printStackTrace();
            throw new RuntimeException("Error changing the background", e);
        }
    }
}
