public class AllocEvent extends ProcessEvent {

    private Process process;

    public AllocEvent(int time, Process process){
        this.time = time;
        this.process = process;
    }

    public Process getProcess(){
        return process;
    }
}
