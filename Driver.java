public class Driver {
    public static void main(String[] args){

        //Initialize Operating system
        OperatingSystem OS = OperatingSystem.getInstance();
        OS.setScheduler(new FakeScheduler());
        OS.setMemoryManager(new MemoryManager(new FirstFit(500)));

        //Start running the Operating system
        OS.run();
    }
}