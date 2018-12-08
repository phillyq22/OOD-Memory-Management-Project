public class AddEvent extends MemEvent {

    public AddEvent(int time, AllocedProcess process){
        this.time = time;
        this.process = process;
    }
    @Override
    public AllocedProcess getProcess() {
        return (AllocedProcess)super.getProcess();
    }
}
