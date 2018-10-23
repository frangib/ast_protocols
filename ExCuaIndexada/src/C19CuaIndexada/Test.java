package C19CuaIndexada;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        CuaIndexada<Byte> cua = new CuaIndexada<>(25);
        //Dades que s'enviaran... és la seq de bytes: 0,1,2,3,4,5,6....
        Byte[][] dades = new Byte[10][10];
        
        for (byte i = 0; i < 10; i++) {
            for (byte j = 0; j < 10; j++) {
                dades[i][j] = (byte) (i * 10 + j);
            }
        }
        Byte[] rebut = new Byte[10];
        int rebuts = 0;
        int i = 0;
        while (rebuts < 100) {
            if (Math.random() > 0.25) {
                int posats = cua.put(dades[i], 0, 10, i * 10);
                System.out.println("cua::"+cua.toString());
            }
            i = (i + 1) % 10;
            if (Math.random() > 0.25) {
                int len = cua.get(rebut, 0, 10);
                rebuts = rebuts + len;
                if (len > 0) {
                    Byte[] mostrar = new Byte[len];
                    System.arraycopy(rebut, 0, mostrar, 0, len);
                    System.out.println("rebuts::"+Arrays.toString(mostrar));
                }
            }
        }

    }

}
