package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;

/**
 *  @author Josh Hug, Beiqian
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private int[] parent;
    private static final int INFINITY = Integer.MAX_VALUE;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        for (int i = 0; i < maze.V(); i++) {
            distTo[i] = INFINITY;
        }
        distTo[s] = 0;
        edgeTo[s] = s;
        parent = new int[maze.V()];
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int sourceX = maze.toX(v);
        int sourceY = maze.toY(v);
        int targetX = maze.toX(t);
        int targetY = maze.toY(t);
        return Math.abs(sourceX - targetX) + Math.abs(sourceY - targetY);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        MinPQ<Integer> pq = new MinPQ<>(new vComparator());
        pq.insert(s);

        while (!pq.isEmpty()) {
            int currV = pq.delMin();
            marked[currV] = true;
            announce();
            if (currV == t) {
                break;
            }
            for (int nextV : maze.adj(currV)) {
                if (nextV != parent[currV]) {
                    if (distTo[currV] + 1 + h(nextV) < distTo[nextV]) {
                        parent[nextV] = currV;
                        edgeTo[nextV] = currV;
                        distTo[nextV] = distTo[currV] + 1;
                        announce();
                        pq.insert(nextV);
                    }
                }
            }
        }
    }
    private class vComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer v1, Integer v2) {
            return h(v1) - h(v2);
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

