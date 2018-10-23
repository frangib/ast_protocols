package E03SolucioZonaCritica;

public class MyTestAndSet {

    private boolean l;

    public boolean isL() {
        return l;
    }

    public void setL(boolean l) {
        this.l = l;
    }

    public boolean testAndSet(boolean b) {
        boolean tmp = l;
        l = b;
        return tmp;
    }

}
