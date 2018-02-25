package lesson_08_02_2018.philosophers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

@Getter
@Setter
@RequiredArgsConstructor
public class Philosopher {
    private final String name;

    public void getToTable(Table table) {
        table.addGuest(this);
        Stick leftStick = table.getLeftStick(this);
        Stick rightStick = table.getRightStick(this);
        Runnable eat = () -> {
            try {
                synchronized (leftStick) {
                    System.out.println(name + " took left stick");
                    TimeUnit.SECONDS.sleep(1);
                    synchronized (rightStick) {
                        System.out.println(name + " took right stick");
                        System.out.println(name + " Started eating");
                        TimeUnit.SECONDS.sleep(10);
                        System.out.println(name + " Finished eating");
                    }
                }
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        };
        new Thread(eat, name).start();
    }

}
