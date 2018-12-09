import java.util.LinkedList;
import java.util.ListIterator;

public class FirstFit implements MemManAlgo {
    private LinkedList<MemoryNode> memory;

    public FirstFit(int memorySize) {
        memory = new LinkedList<>();
        memory.add(new MemoryNode(memorySize, null)); //initiate empty memory
    }

    public AllocedProcess allocate(Process p){
        //insert Process into first available slot

        ListIterator<MemoryNode> it = memory.listIterator();
        boolean foundLocation = false;
        int offset = 0;
        AllocedProcess allocedProc = null;

        while(it.hasNext() && !foundLocation) {
            MemoryNode node = it.next();
            if(node.getProcess() == null && node.getSize() >= p.getSize()){
                foundLocation = true;

                int oldSize = node.getSize();
                allocedProc = new AllocedProcess(offset, p);

                // allocate process
                node.setSize(p.getSize());
                node.setProcess(allocedProc);

                //set rest of unused memory to new node
                it.add(new MemoryNode(oldSize - p.getSize(), null));
            } else {
                offset += node.getSize();
            }
        }

        for(MemoryNode node : memory) {
            System.out.printf("Node:\n\tSize: %4d   Process: %s\n\n", node.getSize(), node.getProcess());
        }

        return allocedProc;
        // if it did allocate process, return the allocated process
        // if it did not allocate process because it did not fit, return null

    }

    public AllocedProcess deallocate(AllocedProcess p){
        //remove process p

        ListIterator<MemoryNode> it = memory.listIterator();
        boolean foundLocation = false;
        int offset = 0;
        int processOffset = p.getOffset();
        MemoryNode currentNode = null;

        //find node with the allocated process
        while(offset < processOffset && it.hasNext()) {
            currentNode = it.next();
            offset += currentNode.getSize();
        }
        currentNode = it.next(); //move on to the correct node when you find the right offset

        //remove the process
        if(offset == processOffset) {
            currentNode.setProcess(null);
        }

        //combine consecutive empty cells into one empty cell
        fixEmptyCells();

        for(MemoryNode node : memory) {
            System.out.printf("Node:\n\tSize: %4d   Process: %s\n\n", node.getSize(), node.getProcess());
        }

        return p;
    }

    private void fixEmptyCells() {
        ListIterator<MemoryNode> it = memory.listIterator();

        while(it.hasNext()) {
            MemoryNode currentNode = it.next();
            if(currentNode.getProcess() == null && it.hasNext()) {
                //check if the node after it is empty
                MemoryNode nextNode = it.next();
                if(nextNode.getProcess() == null) {
                    int size = nextNode.getSize();
                    it.remove();

                    currentNode.setSize(currentNode.getSize() + size);
                    it.previous();
                }
            }
        }
    }
}
