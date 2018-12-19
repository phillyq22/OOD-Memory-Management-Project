import java.util.Observable;

public class OperatingSystem extends Observable
{
    private MemoryManager memoryManager;
    private Scheduler scheduler;

    public OperatingSystem() 
    {
        System.out.println("Operating System created.");
        //add view to observers
    }

    public void setMemoryManager(MemoryManager memoryManager)
    {
        this.memoryManager = memoryManager;
        System.out.println("Operating System memory manager has been set.");
    }

    public void setScheduler(Scheduler scheduler)
    {
        addObserver(scheduler);
        this.scheduler = scheduler;
        System.out.println("Operating System scheduler has been set.");
    }

    public void run()
    {
        System.out.println("Running an event");

        //Initialize processEvent
            scheduler.getEvents();
            ProcessEvent processEvent = scheduler.getNextEvent();


            while (processEvent != null)
            {
                try 
                {
                    Thread.sleep(1000);
                } 
                catch (Exception e)
                {

                }
                scheduler.getEvents();
                MemEvent event = memoryManager.doEvent(processEvent);
                setChanged();
                notifyObservers(event);
                //Update processEvent
                processEvent = scheduler.getNextEvent();
            }

        System.out.println("Finished running");
    }
}