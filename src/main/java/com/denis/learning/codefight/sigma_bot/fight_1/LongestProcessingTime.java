package com.denis.learning.codefight.sigma_bot.fight_1;

import com.google.common.base.MoreObjects;

import java.util.Arrays;
import java.util.PriorityQueue;

public class LongestProcessingTime {
    public static void main(String[] args) {
//        int[] jobs = {15, 30, 15, 5, 10};
//        int servers = 3;
        /*[[1],[0, 4],[2, 3]]*/
        int[] jobs = {15, 2, 14, 14, 14, 258};
        int servers = 8;
        /*[[5], [0], [4], [3], [2], [1], [], []]*/

//        int[] jobs = {8, 7, 15, 15, 13, 6, 18, 4, 16, 1, 2, 19, 2, 15, 18, 6, 20, 16, 10,
//                7, 3, 7, 9, 7, 12, 1, 16, 15, 7, 12, 20, 17, 17, 4, 20, 15, 20, 6, 15, 3, 5, 17, 5, 5, 19, 17, 4, 15, 2, 7};
//        int servers = 9;
/*       [[16,26,22,0,40,10],
         [30,2,35,49,39],
         [34,3,38,5,33],
         [36,13,47,15,46],
         [11,8,4,23,7,48],
         [44,17,24,19,42,9],
         [6,41,29,21,43,25],
         [14,45,18,1,37,12],
         [31,32,27,28,20]] */
        int[][] ints = serverFarm(jobs, servers);
        System.out.println("ints = " + Arrays.deepToString(ints));
    }

    //[[1],[0, 4],[2, 3]]
    static int[][] serverFarm(int[] jobs, int servers) {
        PriorityQueue<Server> serverFarm = new PriorityQueue<>(servers);

        Job[] pairs = new Job[jobs.length];
        for (int i = 0; i < jobs.length; i++) {
            Job job = new Job();
            job.id = i;
            job.workingTime = jobs[i];
            pairs[i] = job;
        }
        Arrays.sort(pairs);


        distribute(serverFarm, pairs, servers);
        Server s;
        while ((s = serverFarm.poll()) != null) {
            System.out.println(s);
        }


        int[][] result = new int[servers][1];
        if(jobs.length <= servers) {
            int i = 0;
            for (; i < pairs.length; i++) {
                result[i][0] = pairs[i].id;
            }

            for (; i < servers; i++) {
                result[i] = new int[0];
            }

            return result;
        }

        Server[] serversFarm = new Server[servers];
        distributeWork(serversFarm, pairs, 0);
        Arrays.sort(serversFarm);

        //System.out.println("serversFarm = " + Arrays.toString(serversFarm));

        for (int i = 0, j = servers - 1; i < servers; i++, j--) {
            result[i] = serversFarm[j].ids;
        }

        return result;
    }

    private static void distribute(PriorityQueue<Server> serverFarm, Job[] pairs, int servers) {
        for (Job job : pairs) {
            if(serverFarm.size() < servers) {
                Server server = new Server();
                server.addJob(job);
                serverFarm.add(server);
            }
            else {
                Server leastLoadedServer = serverFarm.poll();
                leastLoadedServer.addJob(job);
                serverFarm.add(leastLoadedServer);
            }
        }
    }

    private static void distributeWork(Server[] servers, Job[] jobs, int nextJobIndex) {
        if(nextJobIndex == 0) {
            for (int i = 0; i < servers.length; i++) {
                if(nextJobIndex >= jobs.length) return;
                servers[i] = new Server();
                Job job = jobs[nextJobIndex++];
                servers[i].addJob(job);
            }
        }
        else {
            for (Server server : servers) {
                if(nextJobIndex >= jobs.length) {
                    Arrays.sort(servers);
                    return;
                }
                Job job = jobs[nextJobIndex++];
                server.addJob(job);
            }
        }

        Arrays.sort(servers);
        distributeWork(servers, jobs, nextJobIndex);
    }

    public static class Server implements Comparable<Server>{
        int[] ids;
        int workLoad;
        int size = 0;

        Server() {
            ids = new int[1];
        }

        void addJob(Job job) {
            if(size >= ids.length) ids = Arrays.copyOf(ids, size + 1);
            ids[size++] = job.id;
            workLoad += job.workingTime;
        }

        @Override
        public int compareTo(Server o) {
            return Integer.compare(this.workLoad, o.workLoad);
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).add("ids", Arrays.toString(ids)).toString();
        }
    }



    public static class Job implements Comparable<Job>{
        int id;
        int workingTime;

        @Override
        public int compareTo(Job o) {
            int result = Integer.compare(o.workingTime, this.workingTime);
            if(result == 0) result = Integer.compare(o.id, this.id);
            return result;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("id", id)
                    .add("workingTime", workingTime)
                    .toString();
        }
    }
}

