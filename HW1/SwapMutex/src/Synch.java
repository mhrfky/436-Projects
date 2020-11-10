public class Synch {
    //swap two memory locations in one atomic step
    public static synchronized void swap (boolean[] arr, int index1,int index2){
        boolean temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;

    }
}
