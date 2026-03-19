package seedu.duke;

import seedu.duke.recordtype.Record;

public class ListCommand extends Command{
    public ListCommand(){
    }


    @Override
    public void execute(RecordList list) {
        System.out.println("Here is a list of all your records.");
        for (Record record : list){
            System.out.println(record);
        }
    }
}
