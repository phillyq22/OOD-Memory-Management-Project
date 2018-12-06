import java.util.*;

 class FakeScheduler implements Scheduler
{
    PriorityQueue<ProcessEvent> queue = new PriorityQueue<ProcessEvent>();

    public FakeScheduler()
    {
        queue.add(new ProcessEvent(10, new Process(100, 0, 20)));
        queue.add(new ProcessEvent(20, new Process(200, 5, 10)));
        queue.add(new ProcessEvent(30, new Process(50, 0, 30)));
        queue.add(new ProcessEvent(25, new Process(25, 7, 20)));
        queue.add(new ProcessEvent(15, new Process(80, 12, 15)));
    }

    public ProcessEvent getProcessEvent()
    {
        ProcessEvent ret = queue.poll();
        return ret;
    }
}
