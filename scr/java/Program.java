import java.io.*;

import java.util.*;

public class Program {
    private static final List<LogLine> dataList = new ArrayList<>();

    public static void main(String[] args) {
        readUsingFileReader("data.log");
        printFile("data.log");
        try {
            Map<LogLine, Integer> map = getLogLineCounts();
            checkData(map);
        } catch (AssertionError e) {
            System.out.println("Error checking log data: " + e.getMessage());
        }
    }

    private static Map<LogLine, Integer> getLogLineCounts() {
        Map<LogLine, Integer> map = new HashMap<>();
        for (LogLine data : dataList) {
            if (map.containsKey(data)) {
                int count = map.get(data);
                map.put(data, count + 1);
            } else {
                map.put(data, 1);
            }
        }
        return map;
    }

    private static void printFile(String filepath) {
        List<LogLine> logLines = readUsingFileReader(filepath);
        for (LogLine log : logLines) {
            System.out.println(log);
        }
    }

    private static List<LogLine> readUsingFileReader(String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("TMobClient3")) {
                    String[] splitStr = line.split("\\s+");
                    String data = splitStr[0] + " " + splitStr[1];
                    String id = "";
                    String type = "";
                    String status = "";
                    if (line.contains("Send:")) {
                        type = "Send";
                        String[] idArr = line.split("\"id\":")[1].split(",");
                        id = idArr[0];
                    } else if (line.contains("Http Code: 200")) {
                        type = "Response";
                        status = "Http Code: 200";
                        String[] idArr = line.split("\"id\":")[1].split("}");
                        id = idArr[0];
                    }
                    LogLine logLine = new LogLine(data, id, type, status);
                    dataList.add(logLine);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public static void checkData(Map<LogLine, Integer> data) {
        for (Map.Entry<LogLine, Integer> entry : data.entrySet()) {
            if (entry.getValue() == 2) throw new AssertionError(entry.getKey() + "Repeated value!!");
        }
    }
}



