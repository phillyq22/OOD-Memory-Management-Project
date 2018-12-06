import java.util.Observer;

public interface Scheduler extends Observer
{
    ProcessEvent getProcessEvent();
}
