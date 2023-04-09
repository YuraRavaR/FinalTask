import java.util.Objects;

public class LogLine {
    private final String data;
    private final String id;
    private final String type;
    private final String status;


    public LogLine(String data, String id, String type, String status) {
        this.data = data;
        this.id = id;
        this.type = type;
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Data=" + data +
                ", Id=" + id +
                ", Type=" + type +
                ", Status=" + status + ";";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogLine logLine = (LogLine) o;
        return Objects.equals(id, logLine.id) && Objects.equals(status, logLine.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }
}