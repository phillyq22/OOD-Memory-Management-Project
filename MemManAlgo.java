public interface MemManAlgo {
    public AllocedProcess allocate(Process p);
    public AllocedProcess deallocate(AllocedProcess p);


    //public ?? getMemory();
}