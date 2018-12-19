public class DeallocEvent extends ProcessEvent 
{
    private AllocedProcess process;

    public DeallocEvent(int time, AllocedProcess process)
    {
        this.time = time;
        this.process = process;
    }

    @Override
    public AllocedProcess getProcess() 
    {
        return process;
    }
}
