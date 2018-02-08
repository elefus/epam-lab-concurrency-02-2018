package lesson_07_02_2018;

import lombok.SneakyThrows;
import lombok.Synchronized;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Tmp {

    public static void main(String[] args) {
        A a = new A();
        a.method();

        B b = new B();
        b.method();
    }

}


class A {

    @Synchronized
    @SneakyThrows
    public void method() {
        System.out.println("In A");
        Object $lock = A.class.getDeclaredField("$lock").get(this);
        System.out.println(Thread.holdsLock($lock));
    }


}

class B extends A {

    @Override
    @SneakyThrows
    @Synchronized
    public void method() {
        System.out.println("In B");
        Field $lockField = A.class.getDeclaredField("$lock");
        $lockField.setAccessible(true);
        Object $lock = $lockField.get(this);
        System.out.println(Thread.holdsLock($lock));

        Arrays.stream(B.class.getDeclaredFields()).forEachOrdered(System.out::println);
    }
}
