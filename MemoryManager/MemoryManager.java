package MemoryManager;
import MemEvent.AddEvent;
import MemEvent.MemEvent;
import MemEvent.OOMEvent;
import MemEvent.RemoveEvent;
import MemoryManAlgo.MemManAlgo;
import Process.AllocedProcess;
import Process.Process;
import ProcessEvent.AllocEvent;
import ProcessEvent.DeallocEvent;
import ProcessEvent.ProcessEvent;

public class MemoryManager 
{
    private MemManAlgo algorithm;

    public MemoryManager(MemManAlgo algorithm) 
    {
        this.algorithm = algorithm;
    }

    public MemEvent doEvent(ProcessEvent pe) 
    {
        MemEvent event = null;

        if (pe instanceof  AllocEvent) 
        {
            //allocate the process
            AllocedProcess proc = alloc(pe.getProcess());
            if(proc != null)//if the process does get added 
            { 
                event = new AddEvent(pe.getTime(), proc);
            } 
            else //if the process does not get added
            { 
                event = new OOMEvent(pe.getTime(), pe.getProcess());
            }
        } 
        else if (pe instanceof DeallocEvent) 
        {
            //deallocate and create the MemEvent
            event = new RemoveEvent(pe.getTime(), dealloc((AllocedProcess)pe.getProcess()));
        }
        return event;
    }

    private AllocedProcess alloc(Process p) 
    {
        return algorithm.allocate(p);
    }

    private AllocedProcess dealloc(AllocedProcess p)
    {
        return algorithm.deallocate(p);
    }
}