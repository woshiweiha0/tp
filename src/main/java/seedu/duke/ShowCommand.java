package seedu.duke;

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

    @Override
    public void execute(RecordList list) {
        System.out.println(list.getRecord(index));
    }
}
