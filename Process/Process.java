package Process;
public class Process
{
    private int size;
    private int lifeTime;

    public Process(int size, int lifeTime)
    {
        this.size = size;
        this.lifeTime = lifeTime;
    }

    public int getSize()
    {
        return size;
    }

    public int getLifeTime()
    {
        return lifeTime;
    }
}
