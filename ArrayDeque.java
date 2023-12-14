import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class ArrayDeque <T> implements Iterable<T>{
    private T []arr;
    private int arrSize;
    private int arrEnd;
    private int size;
    private int arrBegin;
    private static final int firstArrSize=8;
    private int minSize=firstArrSize;
    private static final double indexBeginAT=0.385;
    private static final double RESIZE_I=1.5;
    private static final double RESIZE_D=.5;
    private static final double RESIZE_D_AT=0.25;
    //private static final double RESIZE_B_AT= Math.abs(1-Math.pow(RESIZE_I,-1))*indexBeginAT;




    public ArrayDeque  (){
        arr=(T[])new Object[firstArrSize];
        arrBegin=(int)(indexBeginAT*firstArrSize);
        arrEnd=arrBegin;
        arrSize=firstArrSize;
        size=0;
    }

    public ArrayDequeIterator iterator(){
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        int pos=arrBegin;
        public ArrayDequeIterator(){}
        public boolean hasNext(){
            return pos<arrEnd;
        }

        public T next(){
            return arr[pos++];
        }
    }

    public void add(int i, T o){
        addLast(arr[arrEnd-1]);
        for (int c=arrEnd-3;c>=i+arrBegin;c--){
            arr[c+1]=arr[c];
        }
        arr[i+arrBegin]=o;
    }
    public void addLast(T x){
        if (size==arrSize){
            resize((int)(arrSize*RESIZE_I));
        }
        if (arrEnd>=arrSize){
            for (int i=arrBegin;i<arrSize;i++){
                arr[i-1]=arr[i];
            }
            arr[arrSize-1]=null;
            arrEnd--;
            arrBegin--;
        }

        arr[arrEnd]=x;
        arrEnd++;
        size++;

    }

    public T getLast(){
        return arr[arrEnd-1];
    }

    public T removeLast(){
        if (isEmpty()){
            return null;
        }
        T r= arr[arrEnd-1];
        arr[arrEnd-1]=null;
        arrEnd--;
        size--;
        if (arrSize*RESIZE_D_AT>=arrEnd&&minSize<arrSize){
            resize((int)(arrSize*RESIZE_D));
        }
        return r;
    }

    public void addFirst(T x){
        if (size==arrSize) {
            resize((int) (arrSize * RESIZE_I));
        }
        if (arrBegin-1<0){
            for (int i=arrEnd;i>=0;i--){
                arr[i+1]=arr[i];
            }
            arr[0]=null;
            arrBegin++;
            arrEnd++;
        }
        arr[arrBegin-1]=x;
        arrBegin--;
        size++;
    }

    public T removeFirst(){
        if (arrSize*RESIZE_D_AT>=size&&minSize<arrSize){
            resize((int)(arrSize*RESIZE_D));
        }
        if (isEmpty()){
            return null;
        }
        T r=arr[arrBegin];
        arr[arrBegin]=null;
        size--;
        arrBegin++;
        if (arrSize*RESIZE_D_AT>=size&&minSize<arrSize){
            resize((int)(arrSize*RESIZE_D));
        }
        return r;
    }

    public boolean removeObject(T o) {
        int i = arrBegin;
        while (!arr[i].equals(o) && i < arrEnd) {
            i++;
        }
        if (i == arrEnd) {
            return false;
        }
        remove(i);
        return true;
    }

    public T remove(int i){
        T r= arr[i];
        while (i<arrEnd-1){
            arr[i]=arr[i+1];
            i++;
        }
        arr[arrEnd-1]=null;
        arrEnd--;
        size--;
        if (arrSize*RESIZE_D_AT>=size&&minSize<arrSize){
            resize((int)(arrSize*RESIZE_D));
        }
        return r;
    }

    public void addRange (int i,ArrayDeque<T> a){
        int j=0;
        while (j<a.size){
            add(i,a.get(j));
            j++;
            i++;
        }
    }

    public ArrayDeque<T> removeRange(int fromIndex, int toIndex){
        ArrayDeque<T>r=new ArrayDeque<>();
        for (int i=toIndex;i<fromIndex;i++){
            r.addLast(remove(i));
        }
        return r;
    }

    public boolean contains(T o){
        for (int i=arrBegin;i<arrEnd;i++){
            if (arr[i].equals(o)){
                return true;
            }
        }
        return false;
    }


    public int indexOf(T o){

        for (int i=arrBegin;i<arrEnd;i++){
            if (arr[i].equals(o)){
                return i;
            }
        }
        return -1;
    }
    public int lastIndexOf(T o){
        int i;
        for (i=arrEnd-1;i<arrBegin;i--){
            if (arr[i].equals(o)){
                break;
            }
        }
        return i;
    }


    public void ensureCapacity(int minCapacity){
        if (minCapacity>arrSize) {
            resize(minCapacity);
        }
        minSize=minCapacity;
    }



    public void trimToSize(){
        resize(size);
        minSize=size;
    }
    private void resize(int x){
        T[]r=(T[])new Object[x];
        int c=(int)((x-size)*indexBeginAT);
        int i=arrBegin;
        arrBegin=c;
        while (i<arrEnd&&c<x){
            r[c]=arr[i];
            i++;
            c++;
        }
        arr=r;
        arrSize+=x-arrSize;
        arrEnd=c;
    }

    public T getFirst(){
        return arr[arrBegin];
    }//
    public T get(int i){

        if (i+arrBegin>arrBegin-1&&i<arrEnd){
            return arr[arrBegin+i];
        }
        throw new IndexOutOfBoundsException("out of bounce error");//
    }

    public T set(int i, T o){
        T r=arr[i+arrBegin];
        arr[i+arrBegin]=o;
        return r;
    }
    public int size(){
        return size;
    }



    public void printDequeNulls(){
        for (T a:arr){
            System.out.print(a+" ");
        }
        System.out.println();
    }

    public void printDeque(){
        for (int i=arrBegin;i<arrEnd;i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }

    public ArrayDeque clear(){
        ArrayDeque r =clone();
        arr=(T[])new Object[firstArrSize];

        arrSize=firstArrSize;
        size=0;
        arrBegin= (int)(indexBeginAT*firstArrSize);
        arrEnd=arrBegin;
        return r;
    }

    public ArrayDeque<T> clone(){

        ArrayDeque<T> r= new ArrayDeque<>();
        for (int i=arrBegin;i<arrEnd;i++){
            r.addLast(arr[i]);
        }
        return r;
    }

    public ArrayDeque<T> subList(int fromIndex, int toIndex){
        ArrayDeque<T>r=new ArrayDeque<>();
        for (int i=fromIndex;i<toIndex;i++){
            r.addLast(arr[i+arrBegin]);
        }
        return r;
    }

@Override
public String toString (){
    StringBuilder r=new StringBuilder("{");
    for (T i: this){
        r.append(i);
        r.append(", ");
    }
    r.replace(r.length()-2,r.length(),"}");
    return r.toString();
}


    @Override
    public boolean equals (Object obj){
        if (obj instanceof ArrayDeque o) {
            if (size() != o.size()) {
                return false;
            }
            for (T item : this) {
                if (!o.contains(item)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isEmpty(){
        return arr[arrBegin]==null;
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> test=new ArrayDeque<>();
        for (int i=0;i<50;i++){
            test.addLast(i);
        }
        System.out.println(test.isEmpty());
        System.out.println(test.get (5));
        test.addFirst(-1);
        System.out.println(test.getFirst());

        test.printDeque();
        test.removeFirst();
        System.out.println(test.contains(1));
        System.out.println(test.contains(176));
        test.printDequeNulls();
        ArrayDeque<Integer> sub= test.subList(2,7);
        sub.printDeque();
        ArrayDeque<Integer> subc=sub.clone();
        subc.printDeque();
        subc.clear();
        subc.addFirst(-23);
        subc.printDeque();
        System.out.println(test.size());
        test.set(0,55);
        test.printDeque();
        test.removeRange(10,20);
        test.add(15,7);
        test.add(15,15);
        System.out.println(test.indexOf(25));
        System.out.println(test.lastIndexOf(15));
        test.remove(15);
        test.removeObject(47);
        test.addRange(33,subc);
        test.trimToSize();
        test.ensureCapacity(100);
        test.clear();
        Comparator<String>sc= (o1, o2) -> o1.compareTo(o2);
        MaxArrayDeque<String> t1=new MaxArrayDeque<>(sc);
        t1.addLast("hi");
        t1.addLast("my");
        t1.addLast("name");
        t1.addLast("is");
        t1.addLast("Jonas");
        System.out.println(t1.max());
        System.out.println(t1.max(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.length()-o1.length();
            }
        }));
        System.out.println(t1);
        MaxArrayDeque<String> t2=new MaxArrayDeque<>(sc);
        t2.addLast("my");
        t2.addLast("Jonas");
        t2.addLast("name");
        t2.addLast("hi");
        t2.addLast("is");
        System.out.println(t1.equals(t2));
        MaxArrayDeque<String>t3=new MaxArrayDeque<>();
        t3.addLast("my");
        t3.addLast("Jonas");
        t3.addLast("name");
        System.out.println(t1.equals(t3));





    }

}
