package task2;

public class MainSplitFile {
    public static void main(String[] args){
        LogParser parser = new LogParser(args[1]);
        parser.splitFile(args[0]);
    }
}
