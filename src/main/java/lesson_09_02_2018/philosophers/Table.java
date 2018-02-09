package lesson_09_02_2018.philosophers;

import lombok.Synchronized;
import lombok.Value;

import java.util.LinkedList;
import java.util.List;

public class Table {

    private final List<Sector> sectors = new LinkedList<>();

    @Synchronized
    public void sit(Philosopher philosopher) {
        int lastSectionIndex = sectors.size();
        sectors.add(new Sector(lastSectionIndex, new Stick()));

        int philosopherIndex = lastSectionIndex + 1;
        sectors.add(new Sector(philosopherIndex, philosopher));
        philosopher.setSectionIndex(philosopherIndex);
    }

    public Stick getLeftStick(Philosopher philosopher) {
        return (Stick) sectors.get(philosopher.getSectionIndex() - 1).getObject();
    }

    public Stick getRightStick(Philosopher philosopher) {
        int indexStick = (philosopher.getSectionIndex() + 1) % sectors.size();
        return (Stick) sectors.get(indexStick).getObject();
    }

    public Stick getStickWithLowestIndex(Philosopher philosopher) {
        int indexStick = Math.min(philosopher.getSectionIndex() - 1, (philosopher.getSectionIndex() + 1) % sectors.size());
        return (Stick) sectors.get(indexStick).getObject();
    }

    public Stick getStickWithHighestIndex(Philosopher philosopher) {
        int indexStick = Math.max(philosopher.getSectionIndex() - 1, (philosopher.getSectionIndex() + 1) % sectors.size());
        return (Stick) sectors.get(indexStick).getObject();
    }

    @Value
    private class Sector {

        private int index;
        private Object object;
    }
}
