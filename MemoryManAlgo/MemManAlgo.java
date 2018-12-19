package MemoryManAlgo;
import Process.AllocedProcess;
import Process.Process;

public interface MemManAlgo 
{
    public AllocedProcess allocate(Process p);
    public AllocedProcess deallocate(AllocedProcess p);
}
