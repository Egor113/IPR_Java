package task2;

import java.io.IOException;

public class MainFindRegExp {
    public static void main(String[] args) throws IOException {
        LogParser parser = new LogParser(args[0],args[2]);
        parser.openFileOrDir(args[1],OpenFIleMode.FIND_REG_EXP);
    }
}
