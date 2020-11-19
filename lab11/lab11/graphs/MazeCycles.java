package lab11.graphs;

public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int[] parent;
    private boolean foundCircle = false;

    public MazeCycles(Maze m) {
        super(m);
        parent = new int[maze.V()];
    }

    @Override
    public void solve() {
        dfs(0);
    }

    private void dfs(int v) {
        marked[v] = true;
        announce();

        for (int w : maze.adj(v)) {
            if (foundCircle) {
                return;
            }
            if (w != parent[v]) {
                if (marked[w]) {
                    edgeTo[w] = v;
                    announce();
                    while (v != w) {
                        int n = parent[v];
                        edgeTo[v] = n;
                        announce();
                        v = n;
                    }
                    foundCircle = true;
                } else {
                    parent[w] = v;
                    announce();
                    dfs(w);
                }
            }
        }
    }
}

