package lesson_08_02_2018;

import lombok.Synchronized;

public class Example3 {

    private static class A {

        @Synchronized
        public void method() {

        }

        @Synchronized
        public void method2() {

        }
    }

    private static class B extends A {

        @Synchronized
        public void method() {
            super.method();
        }
    }

}
