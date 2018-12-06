public class Process
{
    int size;
    int start;
    int lifeTime;

    public Process(int size, int start, int lifeTime)
    {
        this.size = size;
        this.start = start;
        this.lifeTime = lifeTime;
    }

    public int getSize()
    {
        return size;
    }

    public int getStart()
    {
        return start;
    }

    public int getLifeTime()
    {
        return lifeTime;
    }
}
