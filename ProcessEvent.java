public abstract class ProcessEvent implements Comparable<ProcessEvent> {
    protected int time;


    public int getTime()
    {
        return time;
    }

    public abstract Process getProcess();

    public int compareTo(ProcessEvent pe)
    {
        return Integer.compare(time, pe.getTime());
    }
}
