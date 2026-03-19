package seedu.duke;

import seedu.duke.recordtype.Record;

public class ShowCommand extends Command{
    int index;
    public ShowCommand(int index){
        this.index = index;
    }

    public static void listRecord(RecordList records){
        for (Record record : records){
            System.out.println(record);
        }
    }

    public static void printRecord(RecordList records, int index) {
        System.out.println(records.getRecord(index));
    }

    @Override
    public void execute(RecordList list) {
        System.out.println(list.getRecord(index));
    }
}
