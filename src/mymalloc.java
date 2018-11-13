/**
 * @author Danushka
 */

/**
 * This is the main class that work as the RAM memory
 * Mainly two methods myalloc() and myfree() which are work as alloc() and free()
 * in c language. Same as C malloc this will return the base memory address of the pointer
 */

public class mymalloc {

    public static void main(String args[]){
        memory ram = new memory();
        int first=ram.addSegment(25);
        int second=ram.addSegment(100);
        int third =ram.addSegment(60);
        ram.printMemory();
        System.out.println(first);
        System.out.println(second);
        System.out.println(third);
        ram.release(first);
        ram.printMemory();
    }

}
