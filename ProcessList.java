import java.util.LinkedList;

public class ProcessList implements ProcessSource
{

    private LinkedList<Process> processList;

    public ProcessList()
    {
        processList = new LinkedList<>();
        processList.add(new Process(100, 20));
        processList.add(new Process(200, 10));
        processList.add(new Process(50, 30));
        processList.add(new Process(25, 20));
        processList.add(new Process(80, 15));
        processList.add(new Process(20, 35));
        processList.add(new Process(80, 50));
        processList.add(new Process(1000, 30));
    }

    public Process getNextProcess()
    {
        return processList.pop();
    }

    public boolean hasNext()
    {
        return !processList.isEmpty();
    }
}
