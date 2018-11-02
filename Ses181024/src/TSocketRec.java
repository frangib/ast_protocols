
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TSocketRec {
    CuaCirc<Integer> rcvQueue = new CuaCirc<>(10);
    ReentrantLock l = new ReentrantLock();
    Condition dades = l.newCondition();
    
    public int receiveData(int[] buf, int offset, int length){
       //<-- Receiver 
        l.lock();
        try{
           while(rcvQueue.empty()){
               dades.awaitUninterruptibly();
           } 
           //...
        }finally{
            l.unlock();
        }
    }
    
    //consumeSegment(...)
    
    public void processReceivedSegment(Integer s){
       //<-- ReceiverTask
        l.lock();
        try{
           if(!rcvQueue.full()){
               rcvQueue.put(s);
               dades.signal();
           } 
        }finally{
            l.unlock();
        }
    }
    
    /* class ReceiverTask implements Runnable{..} */
}
