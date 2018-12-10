public class Driver {
    public static void main(String[] args){

        //Initialize Operating system
        OperatingSystem os = new OperatingSystem();
        os.setScheduler(new FakeScheduler(new ProcessList()));
        os.setMemoryManager(new MemoryManager(new WorstFit(1024)));
        View view = new View();
        os.addObserver(view);

        //Start running the Operating system
        os.run();
    }
}