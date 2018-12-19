public class MemoryNode 
{
    private int size;
    private AllocedProcess process;

    public MemoryNode(int size, AllocedProcess process) 
    {
        this.size = size;
        this.process = process;
    }

    public int getSize() 
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public AllocedProcess getProcess() 
    {
        return process;
    }

    public void setProcess(AllocedProcess process)
    {
        this.process = process;
    }
}