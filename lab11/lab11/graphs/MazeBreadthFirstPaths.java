package lab11.graphs;

import edu.princeton.cs.algs4.Queue;

public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze maze;
    private static final int INFINITY = Integer.MAX_VALUE;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(int v) {
        Queue<Integer> q = new Queue<Integer>();
        for (int i = 0; i < maze.V(); i++) {
            distTo[i] = INFINITY;
        }
        distTo[v] = 0;
        edgeTo[v] = v;
        marked[v] = true;
        q.enqueue(v);
        announce();
        if (v == t) {
            return;
        }

        while (!q.isEmpty()) {
            int n = q.dequeue();
            for (int w : maze.adj(n)) {
                if (!marked[w]) {
                    edgeTo[w] = n;
                    distTo[w] = distTo[n] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                    announce();
                    if (w == t) {
                        return;
                    }
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs(s);
    }
}

