import java.util.Queue;
import java.util.LinkedList;

class RunnableDemo implements Runnable {
    private Thread t;
    private int pid;
    private String threadName;
    SwapLock lock;
    RunnableDemo( String name, SwapLock lock,int pid) {
        threadName = name;
        System.out.println("Creating " +  threadName );
        this.lock = lock;
        this.pid = pid;
    }

    public void run() {
        System.out.println("Running " +  threadName );

        for(int i = 0; i < 50; i++) { //checking the mutual exclusion and starvation
            lock.requestCS(pid);
            System.out.println(pid + " threaded " + i);
            // Let the thread sleep for a while.
            lock.releaseCS(pid);
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(pid == 1){ // checking the progress
            for(int i = 50; i < 100; i++) {
                lock.requestCS(pid);
                System.out.println(pid + " threaded " + i);
                // Let the thread sleep for a while.
                lock.releaseCS(pid);
            }
        }
        System.out.println("Thread " +  threadName + " exiting.");
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}

class Main{
    public static void main(String[] args) {
        SwapLock lock= new SwapLock();
        RunnableDemo R1 = new RunnableDemo( "Thread-1",lock,0);
        R1.start();

        RunnableDemo R2 = new RunnableDemo( "Thread-2",lock,1);
        R2.start();
        Queue<Integer> q = new LinkedList<Integer>();
        System.out.println( q.peek());
    }
}
