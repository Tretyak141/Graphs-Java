package Graph_package;

import java.util.ArrayList;

public class Pool {
    protected double volume;
    protected boolean passed;
    protected boolean distributed;
    protected ArrayList<Integer> connected;


    Pool(){
        volume = 0;
        passed = false;
        distributed = false;
        connected = new ArrayList<>();
    }
    public void add_edge(int num)
    {
        this.connected.add(num);
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

