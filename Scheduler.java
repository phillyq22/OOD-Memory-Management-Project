import java.util.Observer;

public interface Scheduler extends Observer
{
    ProcessEvent getNextEvent();
    void addEvent(ProcessEvent event);
}
