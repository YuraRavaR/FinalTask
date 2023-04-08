public class LogLine {
    private String Data;
    private String Id;
    private String Type;
    private String Status;


    public LogLine(String data, String id, String type, String status) {
        Data = data;
        Id = id;
        Type = type;
        Status = status;
    }

    @Override
    public String toString() {
        return "Data=" + Data +
                ", Id=" + Id +
                ", Type=" + Type +
                ", Status=" + Status + ";";
    }
}