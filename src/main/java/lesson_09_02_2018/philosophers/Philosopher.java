package lesson_09_02_2018.philosophers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Philosopher {

    private final String name;
    private Runnable behaviour;
    private int sectionIndex;

    public void setSectionIndex(int sectionIndex) {
        this.sectionIndex = sectionIndex;
    }

    public void inviteTo(Table table) {
        new Thread(() -> {
            table.sit(this);
            behaviour.run();
        }, name).start();
    }
}
