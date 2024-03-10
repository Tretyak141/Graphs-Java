import Graph_package.Graph;

import javax.imageio.ImageTranscoder;

public class Main {
    public static void main(String[] args) {
        final long start_time = System.currentTimeMillis();
        final int N = 25_000_000;
        final int L = 30_000_000;
        final int K = 10_000_000;
        final int M = 2_000_000;
        final int VMAX = 500;
        int[][] connections = new int[K][2];
        Graph pools = new Graph();

        //Second Step
        for (int i = 0; i < N; i++) {
            int vol = (int) (1 + Math.random() * VMAX);
            pools.add_water(i, vol);
        }

        //Third step
        for (int i = 0; i < K; i++) {
            boolean flag = false;
            int num1 = -1;
            int num2 = -1;
            while (flag) {
                num1 = (int) (Math.random() * N);
                num2 = (int) (Math.random() * N);
                flag = pools.create_connection(num1, num2);
            }
            connections[i][0] = num1;
            connections[i][1] = num2;
        }
        pools.distribute_water();


        //Fourth step
        for (int i = 0; i < N; i++)
            pools.show_water(i);

        //Fifth step

        for (int i = 0; i < L; i++) {
            int volume = (int) (Math.random() * VMAX + 1);
            int num_of_added_ch = (int) (Math.random() * N);
            pools.add_water(num_of_added_ch, volume);
        }
        pools.distribute_water();


        //Sixth step
        for (int i = 0; i < N; i++) {
            pools.show_water(i);
        }

        //Seventh step
        for (int i = 0; i < M; i++) {
            int num_of_connection = (int) (Math.random() * K);
            if (connections[num_of_connection][0] != -1) {
                int pool1 = connections[num_of_connection][0];
                int pool2 = connections[num_of_connection][1];
                pools.delete_connection(pool1, pool2);
            }
        }

        //Eigth step
        for (int i = 0; i < L; i++) {
            int volume = (int) (Math.random() * VMAX + 1);
            int num_of_added_ch = (int) (Math.random() * N);
            pools.add_water(num_of_added_ch, volume);
        }
        pools.distribute_water();

        //Ninth step
        for (int i=0;i<N;i++)
        {
            pools.show_water(i);
        }
        System.out.printf("Program time is: %f\n",(double)(System.currentTimeMillis() - start_time)/1000);
    }
}