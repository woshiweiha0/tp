package seedu.duke;
import java.util.ArrayList;
import java.util.Iterator;

import seedu.duke.recordtype.Record;

public class RecordList implements Iterable<Record> {
    private final ArrayList<Record> list;
    private int size;

    public RecordList() {
        this.list = new ArrayList<>();
        this.size = 0;
    }

    public void add(Record record){
        list.add(record);
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
        list.remove(index);
        this.size--;
    }
}
