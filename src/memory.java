import java.util.ArrayList;

/**
 * @author Danushka
 */

/**
 *  This one is the main memory class that can be act as a virtual RAM memory
 *  Implementation is made with linked lists. Nodes are the segments
 *  Used Best Fit Algorithm for Memory Allocation
 *  Used Two arrays for Identify holes and process (to increase the performances of searching)
 *  This one has two classes,
 *      1) segment - use to implement a single segment of data in the RAM
 *          * methods are just getters and setters
 *      2) memory  - use to act as RAM
 *          * findBestFit() - used to find the best fit memory location for a process
 *          * findSegment() - used to find the segment in the ram
 *          * getEngagedIndex() - used to find the index of a memory from engaged arrayList
 *          * addSegment() - used to add a new memory segment to the RAM (add a new node to the linked list)
 *          * release() - used to release a memory segment from the RAM ( remove a node from the linked list)
 *          * printMemory() - used to print the memory segments in the ram in a better way :D
 *  For more details see the comments in the each function
 */


class segment{
    private char type; // hole or a process ('H','P')
    private int start_at,end_at; // memory location status
    private segment next; // points to the next node
    private int nodeSize;
    segment(char type, int start_at, int end_at){
        this.type = type;
        this.start_at = start_at;
        this.end_at = end_at;
        this.next = null;
        this.nodeSize = 14;
        /*
        type        (char)  - 2 bytes
        start_at    (int)   - 4 bytes
        end_at      (int)   - 4 bytes
        size        (int)   - 4 bytes
         */
    }
    //Setters
    void setNext(segment next) { this.next = next; }
    private void setStart_at(int start_at) { this.start_at = start_at; }
    void setEnd_at(int end_at) { this.end_at = end_at; }
    public void setType(char type) { this.type = type; }

    //Getters
    int getStart_at() { return start_at; }
    int getEnd_at() { return end_at; }
    char getType() { return type; }
    segment getNext() { return next; }

    void changeType(){
        if(this.type=='H') this.type='P';
        else this.type='H';
    }
}

class memory {
    // This is for identify free memory (type int array of [start_at,size])
    private ArrayList<int[]> free = new ArrayList<>();
    // This is for identify occupied memory (type int array of [start_at,size])
    private ArrayList<int[]> engage = new ArrayList<>();

    private static final int size = 25000; // the Size of the Virtual RAM can be defined here
    private segment RAM;
    memory(){
        RAM = new segment('H',0,size);
        int[] current = {0,25000};
        free.add(current);
    }

    /**
     * This function is for finding the best fit location in the memory
     * @param size : size of the memory block we need
     * @return : index of the memory block in the free ArrayList
     */

    private int findBestFit(int size){
        int index=-1;
        int found_best = Integer.MAX_VALUE;
        int count = 0;
        for(int[] i:this.free){ //loop for find the best place to stay :)
            if(i[1]>=size&&found_best>i[1]){
                found_best = i[1];
                index = count;
                count++;
            }
        }
        return index;
    }

    /**
     * This function is for find the segment object when the start_at location is given
     * @param start: start point of the memory block
     * @return : a segment object with the start at 'start'
     */
    segment findSegment(int start){
        segment next = this.RAM;
        while (next!=null){
            if(next.getStart_at()==start) return next;
            next = next.getNext();
        }
        return null;
    }

    /**
     * This function is for find the index of the memory block in the process array
     * @param start_at : start point of the memory block
     * @return : the index of the memory block in process Array which starts at 'start_at'
     */
    private int getEngagedIndex(int start_at){
        for(int i=0;i<this.engage.size();i++){
            if(this.engage.get(i)[0]==start_at){
                return i;
            }
        }
        return -1;
    }

    /**
     * This function is for add a new memory segment to the memory
     * @param size: size of the memory block we need to add in to the memory
     * @return : the base address (start_at) of the memory location that added
     */

    int addSegment(int size){
        size += 14; //Add the size of the node
        int freeArrayIndex = this.findBestFit(size);
        if(freeArrayIndex==-1){
            System.out.println("No space Available in the RAM!");
            return -1;
        }
        segment BFS = this.findSegment(this.free.get(freeArrayIndex)[0]);
        if(BFS == null){
            System.out.println("Something Went Wrong");
            return 0;
        }else{
            int segmentSize = BFS.getEnd_at()-BFS.getStart_at();
            if(segmentSize==size){
                this.engage.add(this.free.get(freeArrayIndex));
                this.free.remove(freeArrayIndex);
                BFS.changeType();
                return BFS.getStart_at();
            }
            else{
                this.free.get(freeArrayIndex)[1] = segmentSize - size;
                int[] newSize = {this.free.get(freeArrayIndex)[0]+segmentSize-size,size};
                this.engage.add(newSize);
                //Change Segment Node
                BFS.setEnd_at(BFS.getEnd_at()-size); //update size of the node
                segment newSegment = new segment('P',BFS.getEnd_at(),BFS.getEnd_at()+size);
                segment temp = BFS.getNext();
                BFS.setNext(newSegment);
                newSegment.setNext(temp);
//                System.out.println(ObjectSizeFetcher.getObjectSize(newSegment));
                return newSegment.getStart_at(); //returns the base address of the segment
            }
        }
    }

    /**
     * This function is for release a memory location for a hole
     * @param start: start memory location of the block to be released
     */
    void release(int start){
        segment foundSegment = this.findSegment(start);
        int index = this.getEngagedIndex(start);
        if(foundSegment==null){
            System.out.println("Something Went Wrong");
            return;
        }else{
            foundSegment.changeType();
        }
        if(index!=-1){
            this.free.add(this.engage.get(index));
            this.engage.remove(index);
        }else System.out.println("Engaged Array Index not Found");
    }

    /**
     * This function is for print the current memory in the console with a graphical way
     */
    void printMemory(){
        System.out.println("\t \t Printing Ram Memory");
        System.out.println("________________");
        segment current = RAM;
        while (current!=null){
            int size = (current.getEnd_at()-current.getStart_at())/1000;
            String startData="",endData="";
            for(int i=0;i<size;i++) {
                if(i==0) startData+="\tStart: "+current.getStart_at();
                else startData="";
                if(i==size-1) endData+="\tEnd: "+current.getEnd_at();
                else endData="";
                if (current.getType() == 'H') {
                    System.out.println("|\t\t\t\t|"+startData+endData);
                } else {
                    System.out.println("|||||||||||||||||"+startData+endData);
                }
            }
            current = current.getNext();
        }
        System.out.println("________________");
    }
}