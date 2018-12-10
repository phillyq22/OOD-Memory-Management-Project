import java.util.LinkedList;
import java.util.ListIterator;

public class WorstFit implements MemManAlgo{
    private LinkedList<MemoryNode> memory;

    public WorstFit(int memorySize) {
        memory = new LinkedList<>();
        memory.add(new MemoryNode(memorySize, null));
    }

    public AllocedProcess allocate(Process p) {

        ListIterator<MemoryNode> it = memory.listIterator();
        boolean foundLocation = false; //Used to find the worstNode again, unlike its use in FirstFit.
        int offset = 0;
        AllocedProcess ap = null;
        MemoryNode worstNode = null;

        while(it.hasNext()) {
            MemoryNode node = it.next();

            if (node.getProcess() == null && node.getSize() >= p.getSize()) {
                if (worstNode == null) {
                    worstNode = node;
                } else if (worstNode.getSize() < node.getSize()) {
                    worstNode = node;
                }
            }
        }

        if (worstNode != null) {
            // Allocate the process. Iterate until bestNode is found again.
            it = memory.listIterator();
            while (it.hasNext() && !foundLocation) {
                MemoryNode node = it.next();

                //If there is no process and shares the same size as bestNode, the best node has been found.
                if(node.getProcess() == null && node.getSize() == worstNode.getSize()) {
                    foundLocation = true;

                    int oldSize = node.getSize();
                    ap = new AllocedProcess(offset, p);

                    //Actual allocation is done here.
                    node.setSize(p.getSize());
                    node.setProcess(ap);

                    it.add(new MemoryNode(oldSize - p.getSize(), null));
                } else {
                    offset += node.getSize();
                }
            }
        }

        for(MemoryNode node : memory) {
            System.out.printf("Node:\n\tSize: %4d   Process: %s\n\n", node.getSize(), node.getProcess());
        }

        return ap;
    }

    public AllocedProcess deallocate(AllocedProcess p) {
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
