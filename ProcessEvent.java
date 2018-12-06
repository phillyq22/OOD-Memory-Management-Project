public class ProcessEvent implements Comparable<ProcessEvent> {
    int time;
    Process process;

    public ProcessEvent(int time, Process process)
    {
        this.time = time;
        this.process = process;
    }

    public int getTime()
    {
        return time;
    }

    public Process getProcess()
    {
        return process;
    }

    public int compareTo(ProcessEvent pe)
    {
        if(time == pe.getTime())
            return 0;
        else if(time > pe.getTime())
            return 1;
        else
            return -1;
    }
}
