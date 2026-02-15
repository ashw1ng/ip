import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Deadline extends Task {
    protected LocalDateTime by;

    public Deadline(String description, String by) {
        super(description);
        // Expects format: yyyy-MM-dd HHmm (e.g., 2019-12-02 1800)
        this.by = LocalDateTime.parse(by, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    @Override
    public String toString() {
        // Display as: Dec 2 2019 6:00PM
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM d yyyy h:mma")) + ")";
    }
}