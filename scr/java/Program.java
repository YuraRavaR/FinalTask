import java.io.*;

import java.util.ArrayList;
import java.util.List;

public class Program {
    static List<LogLine> dataList = new ArrayList<>();

    public static void main(String[] args) {
        List<LogLine> logLines = readUsingFileReader("data.log");
        for (LogLine log : logLines) {
            System.out.println(log);
        }
    }

    private static List<LogLine> readUsingFileReader(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return null;
        }
        try {
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
                        status = "";
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dataList;
    }
}




