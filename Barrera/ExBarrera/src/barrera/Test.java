package barrera;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        int B = 5;
        int N = 50;
        //...
        Barrera bar = new Barrera(B);
        //Crear i arrancar els N fils amb id ={0,1,2,3,4,5,6,...,48,49,50}
        Fil[] fils = new Fil[N];
        for (int i = 0; i < N; i++) {
            fils[i] = new Fil(bar,i);
            fils[i].start();
            //fils[i].join();
        }        
        for (int i = 0; i < N; i++) {
            fils[i].join();
        }
        //Dubte: Si faig fils[i].join() al primer for el programa no funciona.
        //Quina diferència hi ha en crear un fil i fer-ne join respecte de
        //crearlos tots i fer join de tots?
        System.out.println("Fi simulació");
    }
}

/*
FUNCIONA CORRECTAMENT
*/
