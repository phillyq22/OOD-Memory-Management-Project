package ProcessSource;

import Process.Process;

public interface ProcessSource 
{
    Process getNextProcess();
    boolean hasNext();
}