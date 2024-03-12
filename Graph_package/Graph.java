package Graph_package;

import java.util.ArrayList;
import java.util.Stack;

public class Graph {
    private final Pool[] pools;

    public Graph(int n)
    {
        pools = new Pool[n];
        for (int i=0;i<n;i++)
            pools[i] = new Pool();
    }
    public boolean create_connection(int num1,int num2)
    {
        if (pools[num1].is_connected(num2))
            return false;
        pools[num1].add_edge(num2);
        pools[num2].add_edge(num1);
        return true;
    }
    public void delete_connection(int num1,int num2)
    {
        pools[num1].delete_edge(num2);
        pools[num2].delete_edge(num1);
    }
    public void add_water(int num, int water)
    {
        pools[num].volume += water;
    }

    public double show_water(int num)
    {
        return pools[num].volume;
    }

    public void distribute_water()
    {
        for (int i=0;i<pools.length;i++)
        {
            if (!pools[i].distributed)
                distribute_component(i);
        }
        for (int i=0;i<pools.length;i++)
            pools[i].distributed = false;
    }

    private void set_nulls(int num1) {
        pools[num1].passed = false;
        Stack<Integer> stack = new Stack<>();
        stack.push(num1);
        while(!stack.isEmpty())
        {
            Integer current = stack.pop();
            int counter_of_edges = pools[current].connected.size();

            for (int i=0;i<counter_of_edges;i++) {
                int tmp_number = pools[current].connected.get(i);
                if (pools[tmp_number].passed) {
                    pools[tmp_number].passed = false;
                    stack.push(tmp_number);
                }
            }
        }
    }


    private int count_connected(int num)
    {
        Stack<Integer> stack = new Stack<>();
        stack.add(num);
        int ans = 1;
        pools[num].passed = true;
        while (!stack.isEmpty()) {
            Integer current = stack.pop();
            int counter_of_edges = pools[current].connected.size();
            for (int i = 0; i < counter_of_edges; i++) {
                int tmp_number = pools[current].connected.get(i);
                if (!pools[tmp_number].passed) {
                    pools[tmp_number].passed = true;
                    ans++;
                    stack.push(tmp_number);
                }
            }
        }
        set_nulls(num);
        return ans;
    }

    private double average_volume(int num) {
        Stack<Integer> stack = new Stack<>();
        stack.push(num);
        double ans = pools[num].volume;
        pools[num].passed = true;
        while (!stack.isEmpty()) {
            Integer current = stack.pop();
            int counter_of_edges = pools[current].connected.size();

            for (int i = 0; i < counter_of_edges; i++) {
                int tmp_number = pools[current].connected.get(i);
                if (!pools[tmp_number].passed) {
                    pools[tmp_number].passed = true;
                    ans += pools[tmp_number].volume;
                    stack.push(tmp_number);
                }
            }
        }

        set_nulls(num);
        return ans/count_connected(num);
    }
    private void distribute_component(int num)
    {
        Stack<Integer> stack = new Stack<>();
        stack.push(num);
        double av_volume = average_volume(num);
        pools[num].volume = average_volume(num);
        pools[num].passed = true;
        pools[num].distributed = true;
        while (!stack.isEmpty()) {
            Integer current = stack.pop();
            int counter_of_edges = pools[current].connected.size();

            for (int i = 0; i < counter_of_edges; i++) {
                int tmp_number = pools[current].connected.get(i);
                if (!pools[tmp_number].passed) {
                    pools[tmp_number].passed = true;
                    pools[tmp_number].distributed = true;
                    pools[tmp_number].volume = av_volume;
                    stack.push(tmp_number);
                }
            }
        }

        set_nulls(num);
    }
}

