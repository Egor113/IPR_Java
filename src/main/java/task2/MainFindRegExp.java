package task2;

public class MainFindRegExp {
    public static void main(String[] args){
        LogParser parser = new LogParser(args[0],args[2]);
        parser.findRegExp(args[1]);
    }
}
