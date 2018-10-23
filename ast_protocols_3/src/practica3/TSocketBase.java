
package practica3;

import practica1.Channel;


public class TSocketBase {
    public static Log log = LogFactory.getLog(TSocketBase.class);

    protected Lock lk;
    protected Condition appCV;
    protected Channel channel;

        protected TSocketBase(Channel ch) {
        lk = new ReentrantLock();
        appCV = lk.newCondition();
        channel = ch;
    }
}

