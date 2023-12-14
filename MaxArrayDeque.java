import java.util.ArrayList;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    Comparator<T> c;
    public MaxArrayDeque(Comparator<T> c){
        this.c=c;
    }
    public MaxArrayDeque(){}

    public T max (){
        T max =get(0);
        for (int i=1; i<size();i++){
            if (c.compare(get(i),max)>0){
                max =get(i);
            }
        }
        return max;
    }

    public T max (Comparator<T> c){
        T max =get(0);
        for (int i=1; i<size();i++){
            if (c.compare(get(i),max)>0){
                max =get(i);
            }
        }
        return max;
    }
}
