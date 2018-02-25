package lesson_08_02_2018.philosophers;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mutex {
    private boolean isAllowed;
}
