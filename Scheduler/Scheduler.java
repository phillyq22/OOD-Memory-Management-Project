package Scheduler;
import java.util.Observer;

import ProcessEvent.ProcessEvent;

public interface Scheduler extends Observer
{
    ProcessEvent getNextEvent();
    void addEvent(ProcessEvent event);
    void getEvents();
}
