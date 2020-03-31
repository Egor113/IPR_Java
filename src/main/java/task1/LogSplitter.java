package task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogSplitter {

    public static int getFileLength(String fileName){
        int fileSize = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String str;
            while ((str = reader.readLine()) != null ) {
                fileSize++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileSize;
    }

    public static void splitFile(String fileName, int fileSize) {
        int newFileSize = fileSize / 10;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            if (fileSize % 10 == 0) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < newFileSize; j++) {
                        System.out.println(reader.readLine());
                    }
                }
            }
            else {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < newFileSize; j++) {
                        System.out.println(reader.readLine());
                    }
                }
                for (int j = 0; j < newFileSize + fileSize % 10; j++) {
                    System.out.print(reader.readLine() + " ");
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        splitFile("main.log.2014-11-17",(getFileLength("main.log.2014-11-17")));
    }

}
