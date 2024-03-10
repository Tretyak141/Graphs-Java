package Graph_package;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final int N = 25_000_000;

    private final int K = 10_000_000;
    private final int M = 2_000_000;
    private final int L = 30_000_000;
    Pool[] pools;

    public Graph()
    {
        Pool tmp;
        pools = new Pool[N];
        for (int i=0;i<N;i++)
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
    public boolean delete_connection(int num1,int num2)
    {
        if (!pools[num1].is_connected(num2))
            return false;
        pools[num1].delete_edge(num2);
        pools[num2].delete_edge(num1);
        return true;
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
        for (int i=0;i<N;i++)
        {
            if (!pools[i].distributed)
                distribute_component(i);
        }
        for (int i=0;i<N;i++)
            pools[i].distributed = false;
    }

    private void set_nulls(int num1) {
        pools[num1].passed = false;
        List<Integer> stack = new ArrayList<>();
        stack.add(num1);
        while(stack.size()>0)
        {
            int end_pos = stack.size() - 1;
            Integer current = stack.get(end_pos);
            stack.remove(end_pos);
            int counter_of_edges = pools[current].connected.size();

            for (int i=0;i<counter_of_edges;i++) {
                int tmp_number = pools[current].connected.get(i);
                if (pools[tmp_number].passed) {
                    pools[tmp_number].passed = false;
                    stack.add(tmp_number);
                }
            }
        }
    }


    private int count_connected(int num)
    {
        List<Integer> stack = new ArrayList<>();
        stack.add(num);
        int ans = 1;
        pools[num].passed = true;
        while (stack.size()>0) {
            int end_pos = stack.size() - 1;
            Integer current = stack.get(end_pos);
            stack.remove(current);
            int counter_of_edges = pools[current].connected.size();

            for (int i = 0; i < counter_of_edges; i++) {
                int tmp_number = pools[current].connected.get(i);
                if (!pools[tmp_number].passed) {
                    pools[tmp_number].passed = true;
                    ans++;
                    stack.add(tmp_number);
                }
            }
        }
        set_nulls(num);
        return ans;
    }

    private double average_volume(int num) {
        List<Integer> stack = new ArrayList<>();
        stack.add(num);
        double ans = pools[num].volume;
        pools[num].passed = true;
        while (stack.size() > 0) {
            int end_pos = stack.size() - 1;
            Integer current = stack.get(end_pos);
            stack.remove(current);
            int counter_of_edges = pools[current].connected.size();

            for (int i = 0; i < counter_of_edges; i++) {
                int tmp_number = pools[current].connected.get(i);
                if (!pools[tmp_number].passed) {
                    pools[tmp_number].passed = true;
                    ans += pools[tmp_number].volume;
                    stack.add(tmp_number);
                }
            }
        }

        set_nulls(num);
        return ans/count_connected(num);
    }
    private void distribute_component(int num)
    {
        List<Integer> stack = new ArrayList<>();
        stack.add(num);
        double av_volume = average_volume(num);
        pools[num].volume = average_volume(num);
        pools[num].passed = true;
        pools[num].distributed = true;
        while (stack.size() > 0) {
            int end_pos = stack.size() - 1;
            Integer current = stack.get(end_pos);
            stack.remove(current);
            int counter_of_edges = pools[current].connected.size();

            for (int i = 0; i < counter_of_edges; i++) {
                int tmp_number = pools[current].connected.get(i);
                if (!pools[tmp_number].passed) {
                    pools[tmp_number].passed = true;
                    pools[tmp_number].distributed = true;
                    pools[tmp_number].volume = av_volume;
                    stack.add(tmp_number);
                }
            }
        }

        set_nulls(num);
    }
}

