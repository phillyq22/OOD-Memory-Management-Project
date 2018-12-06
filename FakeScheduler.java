import com.sun.org.apache.regexp.internal.RE;

import java.util.*;

 class FakeScheduler implements Scheduler
{
    private PriorityQueue<ProcessEvent> queue = new PriorityQueue<>();

    public FakeScheduler()
    {
        queue.add(new AllocEvent(1, new Process(100, 20)));
        queue.add(new AllocEvent(2, new Process(200, 10)));
        queue.add(new AllocEvent(3, new Process(50, 30)));
        queue.add(new AllocEvent(4, new Process(25, 20)));
        queue.add(new AllocEvent(5, new Process(80, 15)));
    }

    public ProcessEvent getProcessEvent()
    {
        return queue.poll();
    }

    @Override
    public void update(Observable o, Object arg) {
        switch (arg.getClass().getCanonicalName()){
            case "AddEvent":
                handleAdd((AddEvent)arg);
                break;
            case "RemoveEvent":
                handleRemove((RemoveEvent)arg);
            case "OOMEvent":
                handleOOM((OOMEvent)arg);
        }
    }

    public void handleAdd(AddEvent add){
        System.out.println("I got an add event");
        queue.add(new DeallocEvent(add.getTime() + add.getProcess().getLifeTime(), add.getProcess()));
        System.out.println("Scheduler added a new dealloc event based on the add event from MemManager");
    }

    public void handleRemove(RemoveEvent remove){
        System.out.println("I got a remove event, but I don't care");
    }

    public void handleOOM(OOMEvent oom){
        System.out.println("I got an OOM Event");
        queue.add(new AllocEvent(queue.peek().getTime() + 1, oom.getProcess()));
        System.out.println("Scheduler has placed process that Memory Manager could not allocate behind" +
                " next process event");
    }
}
