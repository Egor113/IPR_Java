package task2;

import java.io.IOException;

public class MainSplitFile {
    public static void main(String[] args) throws IOException {
        LogParser parser = new LogParser(args[1]);
        parser.openFileOrDir(args[0],OpenFIleMode.SPLIT_FILE);
    }
}
