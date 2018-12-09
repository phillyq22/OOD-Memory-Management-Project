import java.util.Observable;

public class OperatingSystem extends Observable {
    //SINGLETON PATTERN
    private static final OperatingSystem OS = new OperatingSystem();
    private MemoryManager memoryManager;
    private Scheduler scheduler;

    private OperatingSystem() {
        System.out.println("Operating System created.");
        //add view to observers
    }

    public void setMemoryManager(MemoryManager memoryManager) {
        this.memoryManager = memoryManager;
        System.out.println("Operating System memory manager has been set.");
    }

    public void setScheduler(Scheduler scheduler) {
        addObserver(scheduler);
        this.scheduler = scheduler;
        System.out.println("Operating System scheduler has been set.");
    }

    public void run() {
        System.out.println("Running an event");

        //Initialize processEvent
        ProcessEvent processEvent = scheduler.getNextEvent();

        while(processEvent != null) {
            MemEvent event = memoryManager.doEvent(processEvent);
            setChanged();
            notifyObservers(event);
            //Update processEvent
            processEvent = scheduler.getNextEvent();
        }

        System.out.println("Finished running");
    }

    public static OperatingSystem getInstance() {
        return OS;
    }

}