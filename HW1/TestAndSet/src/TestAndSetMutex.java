import java.util.Queue;
import java.util.LinkedList;
/* class simpleArrayQueue{
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
*/
class TestAndSetMutex implements Lock {
    TestAndSet lockFlag;
    int next = -1;
    TestAndSetMutex(){
        lockFlag = new TestAndSet();
    }

    @Override
    public void requestCS(int i) {

        while (next ==1- i||lockFlag.testAndSet(1) == 1) next = i;
    }
    
    @Override
    public void releaseCS(int  i) {
        if(next == i) next = -1;
        lockFlag.testAndSet(0);

    }
} 