import MVC.OperatingSystem;
import MVC.View;
import MemoryManAlgo.BestFit;
import MemoryManAlgo.BuddySystem;
import MemoryManAlgo.FirstFit;
import MemoryManAlgo.WorstFit;
import MemoryManager.MemoryManager;
import ProcessSource.ProcessList;
import Scheduler.FakeScheduler;
public class Driver {
    public static void main(String[] args)
    {

        //Initialize Operating system
        OperatingSystem os = new OperatingSystem();
        os.setScheduler(new FakeScheduler(new ProcessList()));
        os.setMemoryManager(new MemoryManager(new FirstFit(1024)));
        View view = new View();
        os.addObserver(view);

        //Start running the Operating system
        os.run();
    }
}