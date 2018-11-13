/**
 * @author Danushka
 */

/**
 * This is the main class that work as the RAM memory
 * Mainly two methods myalloc() and myfree() which is work as alloc() and free()
 * in c language. Same as C malloc this will return the base memory address of the pointer
 */

class mymalloc {
    private static memory ram; // See the memory.java for more details of this data type

    /**
     *  This function works as alloc in C, for more see the memory->addSegment()
     * @param size: size of the memory we need to allocate
     * @return : the base address of the memory location
     */
    static  int MyMalloc(int size){
        if(ram==null) ram=new memory();
        return ram.addSegment(size);
    }

    /**
     * This function is work as free() function in the c Language. For more details see memory->release()
     * @param start_at : the start address of the memory need to be released.
     */
    static void MyFree(int start_at){
        if(ram==null){ System.out.println("Memory is not initialized"); return;}
        else{
            ram.release(start_at);
        }
    }

    /**
     * This function will not work for any kind of array structures or objects. Just basic :/ (under construction)
     * @param dataType: data type of the object we need to find the sizeof
     * @return : the size of the object we given
     */
    static int MySize(Class dataType){
        if(dataType==null) throw new NullPointerException();
        if(dataType==byte.class||dataType==Byte.class) {
            return Byte.SIZE;
        }
        if (dataType == short.class || dataType == Short.class) {
            return Short.SIZE;
        }
        if (dataType == char.class || dataType == Character.class) {
            return Character.SIZE;
        }
        if (dataType == int.class || dataType == Integer.class) {
            return Integer.SIZE;
        }
        if (dataType == long.class || dataType == Long.class) {
            return Long.SIZE;
        }
        if (dataType == float.class || dataType == Float.class) {
            return Float.SIZE;
        }
        if (dataType == double.class || dataType == Double.class) {
            return Double.SIZE;
        }
        return 4; // default for 32-bit memory pointer
    }

    static void printMemory(){
        if(ram==null) {
            System.out.println("Memory is not initialized");
            return;
        }
        else{
            ram.printMemory();
        }
    }

}
