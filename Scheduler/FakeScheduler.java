package Scheduler;
import java.util.Observable;
import java.util.PriorityQueue;

import MemEvent.AddEvent;
import MemEvent.OOMEvent;
import MemEvent.RemoveEvent;
import ProcessEvent.AllocEvent;
import ProcessEvent.DeallocEvent;
import ProcessEvent.ProcessEvent;
import ProcessSource.ProcessSource;

public class FakeScheduler implements Scheduler
{
    private PriorityQueue<ProcessEvent> queue = new PriorityQueue<>();
    private ProcessSource source;
    private int time;

    public FakeScheduler(ProcessSource source)
    {
        this.source = source;
        time = 0;
    }

    public void getEvents()
    {
        while (source.hasNext())
        {
            addEvent(new AllocEvent(++time, source.getNextProcess()));
        }
    }

    public ProcessEvent getNextEvent()
    {
        return queue.poll();
    }

    @Override
    public void addEvent(ProcessEvent event) 
    {
        queue.add(event);
    }

    @Override
    public void update(Observable o, Object arg) 
    {
        switch (arg.getClass().getCanonicalName())
        {
            case "AddEvent":
                handleAdd((AddEvent)arg);
                break;
            case "RemoveEvent":
                handleRemove((RemoveEvent)arg);
                break;
            case "OOMEvent":
                handleOOM((OOMEvent)arg);
        }
    }

    public void handleAdd(AddEvent add)
    {
        System.out.println("Scheduler got an add event");
        queue.add(new DeallocEvent(add.getTime() + add.getProcess().getLifeTime(), add.getProcess()));
        time = add.getTime();
        System.out.println("Scheduler added a new dealloc event based on the add event from MemManager");
    }

    public void handleRemove(RemoveEvent remove)
    {
        System.out.println("I got a remove event, but I don't care");
        time = remove.getTime();
    }

    public void handleOOM(OOMEvent oom)
    {
        System.out.println("I got an OOM Event");
        queue.add(new AllocEvent(queue.peek().getTime() + 1, oom.getProcess()));
        time = oom.getTime();
        System.out.println("Scheduler has placed process that Memory Manager could not allocate behind" +
                " next process event");
    }
}
