package seedu.duke;

public class Record {
    protected String description;

    public Record(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean containsKeyword(String keyword) {
        return description.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public String toString() {
        return description;
    }
}
    public void setDescription(String description) {
        this.description = description;
    }
}
