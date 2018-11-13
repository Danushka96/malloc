import java.util.ArrayList;

/**
 * @author Danushka
 */
class segment{
    private char type;
    private int start_at,end_at;
    private segment next;
    segment(char type, int start_at, int end_at){
        this.type = type;
        this.start_at = start_at;
        this.end_at = end_at;
        this.next = null;
    }
    //Setters
    public void setNext(segment next) { this.next = next; }
    public void setStart_at(int start_at) { this.start_at = start_at; }
    public void setEnd_at(int end_at) { this.end_at = end_at; }
    public void setType(char type) { this.type = type; }

    //Getters
    public int getStart_at() { return start_at; }
    public int getEnd_at() { return end_at; }
    public char getType() { return type; }
    public segment getNext() { return next; }

    public void changeType(){
        if(this.type=='H') this.type='P';
        else this.type='H';
    }
}

public class memory {
    // This is for identify free memory (type int array of [start_at,size])
    private ArrayList<int[]> free = new ArrayList<>();
    // This is for identify occupied memory (type int array of [start_at,size])
    private ArrayList<int[]> engage = new ArrayList<>();

    private static final int size = 25000;
    private segment RAM;
    memory(){
        RAM = new segment('H',0,size);
        int[] current = {0,25000};
        free.add(current);
    }
    int findBestFit(int size){
        int index=-1;
        int found_best = Integer.MAX_VALUE;
        int count = 0;
        for(int[] i:this.free){
            if(i[1]>=size&&found_best>i[1]){
                found_best = i[1];
                index = count;
                count++;
            }
        }
        return index;
    }
    segment findSegment(int start){
        segment next = this.RAM;
        while (next!=null){
            if(next.getStart_at()==start) return next;
            next = next.getNext();
        }
        return null;
    }
    int getEngagedIndex(int start_at){
        for(int i=0;i<this.engage.size();i++){
            if(this.engage.get(i)[0]==start_at){
                return i;
            }
        }
        return -1;
    }

    int addSegment(int size){
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
                return newSegment.getStart_at(); //returns the base address of the segment
            }
        }
    }
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
            this.engage.remove(index);
        }else System.out.println("Engaged Array Index not Found");
    }
    void printMemory(){
        System.out.println("\t \t Printing Ram Memory");
        segment current = RAM;
        while (current!=null){
            System.out.print("Type: "+current.getType()+"\t");
            System.out.print("start: "+current.getStart_at()+"\t");
            System.out.print("end: "+current.getEnd_at()+"\n");
            current = current.getNext();
        }
    }
}