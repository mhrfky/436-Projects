public class SwapLock implements Lock  {
    boolean[] turn = new boolean[3];
    static boolean lock = true;

    SwapLock(){
        turn[0] = false;
        turn[1] = false;
        turn[2] = true;

    }
    @Override
    public void requestCS(int pid) {
        while(!turn[pid])
        {
            Synch.swap(turn,pid,2);
            //System.out.println((lock));
        }
    }

    @Override
    public void releaseCS(int pid) {
        Synch.swap(turn,pid,2);
    }
}
