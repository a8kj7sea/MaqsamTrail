package me.a8kj.imagebasedonsun.action.actions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.imagebasedonsun.action.Action;

@RequiredArgsConstructor
@Getter
public class PrintResultAction implements Action {

    private final String result;

    @Override
    public void perform() {
        if (result == null || result.isEmpty()) {
            System.out.println("No result to print.");
            return;
        }

        System.out.println(result);
    }
}
