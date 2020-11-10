import java.util.Queue;
import java.util.LinkedList;
class simpleArrayQueue{
    int[] a;
    int head = 0;
    int tail = 0;
    int size;
    simpleArrayQueue(int size){
        a = new int[size];
        this.size = size;
    }
    public synchronized void add(int i){
        if ((tail+1)%size == head) return;
        a[tail] = i;
        tail = (tail + 1) % size;
    }
    public synchronized int remove(){
        if( head == tail) return -1;
        int returned = a[head];
        head = (head+1) % size;
        return returned;
    }
    public synchronized int peek(){
        return a[head];
    }
}

class TestAndSetMutex implements Lock {
    TestAndSet lockFlag;
    public simpleArrayQueue a;
    TestAndSetMutex(){
        lockFlag = new TestAndSet();
        a = new simpleArrayQueue(3);
    }

    @Override
    public void requestCS(int i) {
        a.add(i);
        while (a.peek() != i ||lockFlag.testAndSet(1) == 1) ;
    }
    
    @Override
    public void releaseCS(int  i) {
        lockFlag.testAndSet(0);
        a.remove();
    }
} 