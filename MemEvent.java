public abstract class MemEvent {
    protected int time;
    protected Process process;

    public int getTime(){
        return time;
    }

    public Process getProcess() {
        return process;
    }
}
