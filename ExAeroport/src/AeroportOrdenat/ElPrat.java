package AeroportOrdenat;

public class ElPrat {

    private final int N;
    private String ordreArr = "";
    private String ordreEnl = "";
    //...

    public ElPrat(int N) {
        this.N = N;
    }

    public void permisEnlairar(int numPista) {
        //...
        ordreArr = ordreArr + numPista;
        //...
        ordreEnl = ordreEnl + numPista;
    }

    public void fiEnlairar(int numPista) {
        //...
        ordreEnl = ordreEnl + "*";
        //...
    }

    public void mostrar() {
        System.out.println(ordreArr);
        String ordreEnlNet = ordreEnl.replace("*", "");
        System.out.println(ordreEnlNet);
        System.out.println(ordreEnl);
        if (ordreArr.equals(ordreEnlNet)) {
            System.out.println("S'han enlairat per ordre de petició");
        } else {
            System.out.println("NO s'han enlairat per ordre de petició");
        }
    }

}
