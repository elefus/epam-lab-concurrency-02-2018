package lesson_09_02_2018.exhibition;


import lombok.SneakyThrows;

public class Storage {

    private String value = "DEFAULT";
    private boolean allowRead = true;
    private int readersCounter;

    @SneakyThrows
    String read() {
        synchronized (value) {
            while (!allowRead) {
                value.wait();
            }
            try {
                readersCounter++;
                return value;
            } finally {
                readersCounter--;
                if (readersCounter == 0) {
                    value.notifyAll();
                }
            }
        }
    }

    @SneakyThrows
    void write(String value) {
        synchronized (this.value) {
            allowRead = false;
            while (readersCounter > 0) {
                this.value.wait();
            }
            this.value = value;
            allowRead = true;
        }
    }
}
