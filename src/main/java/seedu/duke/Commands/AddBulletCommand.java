package seedu.duke.Commands;

import seedu.duke.RecordList;
import seedu.duke.recordtype.Record;
import seedu.duke.Ui;

public class AddBulletCommand extends Command{
    private final int index;
    private final String bullet;
    private final Ui ui;

    public AddBulletCommand(int index, String bullet) {
        this.index = index;
        this.bullet = bullet;
        this.ui = new Ui();
    }

    public int getIndex() {
        return index;
    }

    public String getBullet() {
        return bullet;
    }

    public void execute(RecordList list) {
        Record record = list.getRecord(index);
        record.addBullet(bullet);
        ui.showMessage("Added bullet to: " + record.getTitle());
    }
}
