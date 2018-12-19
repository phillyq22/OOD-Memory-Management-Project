public class OOMEvent extends MemEvent 
{

    public OOMEvent(int time, Process process)
    {
        this.time = time;
        this.process = process;
    }
    @Override
    public Process getProcess() 
    {
        return (Process)super.getProcess();
    }
}
