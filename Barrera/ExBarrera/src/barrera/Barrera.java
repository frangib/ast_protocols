package barrera;

import java.util.stream.IntStream;

public class Barrera {

    private final int B;
    //...
    private volatile int numEsperant;
    private final Mutex m;
    private String str;
    private int j;

    public Barrera(int B) {
        this.B = B;
        numEsperant = 0;
        m = new Mutex();
        str = "grup: ";
        j = 0;
    }

    public void entrar(int id) {
        //...
        //Construcció await
        //Cada cop que un procés arriba a la barrera hem d'incrementar el nombre
        //de processos esperant:
        //Obtenim l'exclusió mútua
        m.entrar_zc();
        numEsperant++;
        while (numEsperant < B) {
            m.sortir_zc();
            m.entrar_zc();
        }
        //En aquest punt el nombre de processos que han arribat a la barrera és 
        //B. Restem b al nombre de processos que esperen i imprimim les ids.
        //Codi
        j++;
        //Imprimir les comes correctament (l'última id no ha d'anar seguida de 
        //coma).
        if (j != B) {
            str += id + ",";
        } else {
            str += id;
        }
        //Si han arribat B processos llavors imprimeix l'String, resta B al
        //nombre de processos esperant, posa el comptador j = 0 i escriu "grup"
        //a l'String per les ids dels propers 5.
        if (j == B) {
            System.out.println(str);
            numEsperant -= B;
            j = 0;
            str = "grup: ";
        }

        m.sortir_zc();
    }
}
