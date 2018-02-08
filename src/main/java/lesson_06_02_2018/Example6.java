package lesson_06_02_2018;


import java.util.List;

public class Example6 {

    private static volatile int value;

    public static void main(String[] args) throws InterruptedException {
        Thread inc = new Thread(() -> {
            for (int i = 0; i < 1_000_000; ++i) {
                ++value;
            }
        });

        Thread dec = new Thread(() -> {
            for (int i = 0; i < 1_000_000; ++i) {
                --value;
            }
        });


        inc.start();
        dec.start();

        inc.join();
        dec.join();

        System.out.println(value);
    }

    public void removeIf(List<Integer> list) {
        if (!list.isEmpty()) {
            // <-
            // code removeIF
        }
    }
}

//                    ++value
//                    value = value + 1
//
//                    LOAD  value
//                    INC   value
//                    STORE value
//
//       T1(+)           value             T2(-)
//        0     LOAD       0     LOAD       0
//        1     INC        0     DEC       -1
//        1     STORE      1               -1
//        1               -1     STORE     -1
//       -1     LOAD      -1               -1
//        0     INC       -1     LOAD      -1
//        0               -1     DEC       -2
//        0     STORE      0               -2
//        0               -2     STORE     -2
//        0               -2               -2
//
//
//
//       T1(+)           value             T2(-)
//        0             <- 0                0
//        0     LOAD       0                0
//        1     INC        0                0
//        1     STORE      1                0
//        1 ->             1                0
//        1                1 ->             0
//        1                1     LOAD       1
//        0                1     DEC        0
//        0                0     STORE      0
//        0                0             <- 0
//        0             <- 0                0
//        0     LOAD       0                0
//        1     INC        0                0
//        1     STORE      1                0
//        1 ->             1                0