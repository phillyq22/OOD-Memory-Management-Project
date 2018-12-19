public class RemoveEvent extends MemEvent
{

    public RemoveEvent(int time, AllocedProcess process)
    {
        this.time = time;
        this.process = process;
    }
    @Override
    public AllocedProcess getProcess()
    {
        return (AllocedProcess)super.getProcess();
    }
}
