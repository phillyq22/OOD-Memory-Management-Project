import java.util.LinkedList;
import java.util.ListIterator;

public class BuddySystem implements MemManAlgo 
{
    private LinkedList<MemoryNode> memory;
 
    public BuddySystem(int memorySize) 
    {
        memory = new LinkedList<>();
        memory.add(new MemoryNode(memorySize, null));
    }

    public AllocedProcess allocate(Process p) 
    {
        ListIterator<MemoryNode> it = memory.listIterator();
        boolean foundLocation = false; //Used to find the bestNode again, unlike its use in FirstFit.
        int offset = 0;
        AllocedProcess ap = null;
        int pSize = p.getSize();
        while(it.hasNext() && !foundLocation) 
        {
            MemoryNode node = it.next();
            int size = node.getSize();
            if(node.getProcess() == null)//Only try to allocate a node that doesn't have a process
            {
            	if(pSize <= size && (size/2) < pSize)//Criteria for correct node.
                {
                	foundLocation = true;//stop searching
                	ap = new AllocedProcess(offset, p);
                	node.setProcess(ap);
                }
                else if(pSize <= size && (size/2) >= pSize)//If this memBlock is too big
                {
                	it.remove();//remove the big block
                	it.add(new MemoryNode(size/2,null));//replace with two nodes half the size
                	it.add(new MemoryNode(size/2,null));
                	foundLocation = true;//We don't want this while loop to run anymore because we recall the method next
                	ap = allocate(p);//Restart this same process, but from the beginning of the linked list.
                }
                else
                {
                	offset += size;//The node was too small, so skip over it. Keep track of offset
                }
            }
            else//Since this node already had a process, just skip over it while tracking the offset
            {
            	offset += size;
            }
        }

        for(MemoryNode node : memory) 
        {
            System.out.printf("Node:\n\tSize: %4d   Process: %s\n\n", node.getSize(), node.getProcess());
        }

        return ap;
    }

    public AllocedProcess deallocate(AllocedProcess p) 
    {
        //remove process p

        ListIterator<MemoryNode> it = memory.listIterator();
        int offset = 0;
        int processOffset = p.getOffset();
        MemoryNode currentNode = null;

        //find node with the allocated process
        while(offset < processOffset && it.hasNext()) 
        {
            currentNode = it.next();
            offset += currentNode.getSize();
        }
        currentNode = it.next(); //move on to the correct node when you find the right offset

        //remove the process
        if(offset == processOffset) 
        {
            currentNode.setProcess(null);
        }

        //combine consecutive empty cells into one empty cell
        fixEmptyCells();

        for(MemoryNode node : memory) 
        {
            System.out.printf("Node:\n\tSize: %4d   Process: %s\n\n", node.getSize(), node.getProcess());
        }

        return p;
    }

    private void fixEmptyCells() 
    {
        ListIterator<MemoryNode> it = memory.listIterator();
        MemoryNode curr = null;
        MemoryNode next = null;
        boolean complete = false;
        curr = it.next();
        while(it.hasNext() && !complete) 
        {
            int size1 = curr.getSize();
            if(it.hasNext())
            {
            	 next = it.next();
                 int size2 = next.getSize();
                 if(curr.getProcess() == null && next.getProcess() == null) //If both of these nodes can be merged
                 {
     	            if((size1 + size2) == (size1*2))//Make sure merging them is valid
     	            {
     	                it.remove();//Remove one of the nodes
     	                curr.setSize(size1*2);//Resize the other node appropriately
     	                complete = true;//You're done with this loop
     	                fixEmptyCells();//Repeat the process from the start of the linked list.
     	            }
                    else 
                    {
                    	curr = next;
                    }
                 }
            }
        }
    }
}
