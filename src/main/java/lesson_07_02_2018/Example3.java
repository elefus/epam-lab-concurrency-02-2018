package lesson_07_02_2018;

public class Example3 {


    // |2|4|4|4|8|2|
    // | mark-word | class-pointer | from | to | current | coefficient | offset |
    // |00 - locked   | pointer to mark-word
    // |01 - unlocked | identity hash-code | age | 0
    // |10 - inflated
    // |11 - mark for gc


    // JOL - java object layout
    private static class Counter {

        private static float val = 100;
        private int from;                 // <- 4
        private int to;                   // <- 4
        private int current;              // <- 4
        private double coefficient;       // <- 8
        private String string;            // <- машинное слово
        private int[] arr = new int[10];  // <- машинное слово

        void methodA(){}
        void methodB(){}
        static void staticMethodA(){}
        static void staticMethodB(){}
    }

    public static void main(String[] args) {
        Counter counter = new Counter();

    }

    //                                       HEAP
    //           YOUNG_GEN                                     OLD_GEN
    //      eden         S1    S2
    // [ ..............|     |..    ][.                                                               ]
}
