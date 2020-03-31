package task1;

import java.io.*;

public class LogSplitter {

    static int fileSize = 0;

    public static void getFileLength(String fileName){
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String str;
            while ((str = reader.readLine()) != null ) {
                fileSize++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void splitFile(String fileName, String fileNameTemplate) {
        int newFileSize = fileSize / 10;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            if (fileSize % 10 == 0) {
                for (int i = 0; i < 10; i++) {
                    int num = i + 1;
                    writeIntoFile(fileNameTemplate + "_" + num + ".log", newFileSize, reader,true);
                }
            }
            else {
                for (int i = 0; i < 9; i++) {
                    int num = i + 1;
                    writeIntoFile(fileNameTemplate + "_" + num + ".log", newFileSize, reader,true);
                }
                writeIntoFile(fileNameTemplate + "_10.log", newFileSize, reader,false);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeIntoFile(String currentFileName, int newFileSize, BufferedReader reader, boolean isDividedBy10){
        if (isDividedBy10){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFileName))){
                for (int j = 0; j < newFileSize; j++) {
                    writer.write(reader.readLine() + "\n");
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFileName))){
                for (int j = 0; j < newFileSize + fileSize % 10; j++) {
                    writer.write(reader.readLine() + "\n");
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        getFileLength(args[0]);
        splitFile(args[0], args[1]);
    }

}
