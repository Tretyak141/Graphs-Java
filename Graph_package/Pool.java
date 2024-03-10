package Graph_package;

import java.util.ArrayList;

public class Pool {
    protected double volume;
    protected boolean passed;
    protected boolean distributed;
    protected ArrayList<Integer> connected;


    public double show_volume(){
        return this.volume;
    }

    Pool(){
        volume = 0;
        passed = false;
        distributed = false;
        connected = new ArrayList<>();
    }
    private int cmp(int num1,int num2)
    {
        if (num1 > num2) return 1;
        if (num2 > num1) return -1;
        return 0;
    }
    public void add_edge(int num)
    {
        this.connected.add(num);
        this.connected.sort(this::cmp);
    }
    public void delete_edge(Integer num) {
        this.connected.remove(num);
    }
    public boolean is_connected(Integer num1){
        if (this.connected.indexOf(num1) == -1)
            return false;
        return true;
    }

}

