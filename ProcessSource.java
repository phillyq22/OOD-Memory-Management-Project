public interface ProcessSource 
{
    Process getNextProcess();
    boolean hasNext();
}