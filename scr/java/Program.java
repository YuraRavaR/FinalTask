import java.io.*;

import java.util.*;

public class Program {
    static List<LogLine> dataList = new ArrayList<>();

    public static void main(String[] args) {
        readUsingFileReader("data.log");
//        List<LogLine> logLines = readUsingFileReader("data.log");
//        for (LogLine log : logLines) {
//            System.out.println(log);
//        }
        Map<LogLine, Integer> map = new HashMap<>();
        for (LogLine data : dataList) {
            if (map.containsKey(data)) {
                int count = map.get(data);
                map.put(data, count + 1);
            } else {
                map.put(data, 1);
            }
        }

        try {
            checkData(map);
        } catch (AssertionError e) {
            System.out.println("Error message: " + e.getMessage());
        }

    }

    private static List<LogLine> readUsingFileReader(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("File not found: " + filePath);
                return null;
            }
            FileReader reader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(reader);
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
            bufferedReader.close();
        } catch (Exception e) {
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




