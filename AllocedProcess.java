public class AllocedProcess extends Process {

    private int offset;

    public AllocedProcess(int offset, Process process){
        super(process.getSize(), process.getLifeTime());
        this.offset = offset;
    }

    public String toString() {
        return super.toString() + " offset=" + this.offset;
    }
}
