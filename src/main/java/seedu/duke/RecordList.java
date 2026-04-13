package seedu.duke;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import seedu.duke.recordtype.Record;

public class RecordList implements Iterable<Record> {
    private final ArrayList<Record> list;
    private int size;

    public RecordList() {
        this.list = new ArrayList<>();
        this.size = 0;
    }

    public void add(Record record) {
        if (record == null) {
            return;
        }

        list.add(record);

        String tech = record.getTech();
        if (tech != null && !tech.trim().isEmpty()) {
            User.getInstance().addSkills(tech);
        }

        size++;
    }

    public Record getRecord(int index) {
        return list.get(index);
    }

    public Iterator<Record> iterator() {
        return list.iterator();
    }

    public int getSize() {
        return size;
    }

    public void removeIndex(int index) {
        Record record = list.get(index);

        String tech = record.getTech();
        if (tech != null && !tech.trim().isEmpty()) {
            User user = User.getInstance();
            user.removeSkills(tech);
        }

        list.remove(index);
        size--;
    }

    public void sort(Comparator<Record> comparator) {
        list.sort(comparator);
    }

    public boolean contains(Record record) {
        return list.contains(record);
    }
}
