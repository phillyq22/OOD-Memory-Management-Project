import java.util.LinkedList;

public class FirstFit implements MemManAlgo {
    private LinkedList<MemoryNode> memory;

    public FirstFit() {
        memory = new LinkedList<MemoryNode>();
    }

    public AllocedProcess allocate(Process p){
        //insert Process into first available slot


        return new AllocedProcess(0, p);
    }

    public AllocedProcess deallocate(AllocedProcess p){
        //remove process p


        return p;
    }
}
