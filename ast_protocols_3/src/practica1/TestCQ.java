package practica1;

public class TestCQ {

    public static void main(String[] args) {
        int N = 2;
        CircularQueue<Integer> cq = new CircularQueue<>(N);
        System.out.println("Test amb una cua circular de capacitat: " + N);
        for (int i = 0; !cq.full(); i++) {
            cq.put(i);
            System.out.println("put(" + i + "), hasFree 1?: " + cq.hasFree(1));
        }
        System.out.println("size: " + cq.size());
        System.out.println("hasFree amb paràmetre negatiu:");
        try {
            cq.hasFree(-1);
        } catch (Exception e) {
            System.out.println("Genera una excepció..." + e.getMessage());
        }
        System.out.println("Put amb cua plena:");
        try {
            cq.put(N);
        } catch (Exception e) {
            System.out.println("Genera una excepció..." + e.getMessage());
        }
        System.out.println("Last: " + cq.peekLast());
        for (int i = 0; !cq.hasFree(N); i++) {
            System.out.println("First: " + cq.peekFirst() + ", get:" + cq.get());
        }
        System.out.println("First: " + cq.peekFirst());
        if (!cq.empty()) {
            System.out.println("get:" + cq.get());
        }
        System.out.println("Get amb cua buida:");
        try {
            cq.get();
        } catch (Exception e) {
            System.out.println("Genera una excepció..." + e.getMessage());
        }

    }
}
