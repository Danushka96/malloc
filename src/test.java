/**
 * @author Danushka
 */
public class test {
    public static void main(String args[]){

        //Using direct Call size
        int first = mymalloc.MyMalloc(1000);
        int second = mymalloc.MyMalloc(3500);
        int third = mymalloc.MyMalloc(5800);
        int fourth = mymalloc.MyMalloc(2800);
        mymalloc.printMemory();
        mymalloc.MyFree(first);
        mymalloc.MyFree(third);
        mymalloc.printMemory();

        //Using Size of Function
        //Under Construction :D
    }
}
