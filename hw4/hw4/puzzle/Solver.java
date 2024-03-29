package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import java.util.*;

public class Solver {
    private List<WorldState> solution = new ArrayList<>();
    private int searchCnt = 1;

    public int getSearchCnt() {
        return searchCnt;
    }

    private class SearchNode {
        private WorldState state;
        private int moves;
        private SearchNode prev;
        private int edtg;

        private SearchNode(WorldState state, int moves, SearchNode prev) {
            this.state = state;
            this.moves = moves;
            this.prev = prev;
            edtg = state.estimatedDistanceToGoal();
        }
    }

    private class NodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode n1, SearchNode n2) {
            int priority1 = n1.moves + n1.edtg;
            int priority2 = n2.moves + n2.edtg;
            return priority1 - priority2;
        }
    }

    /**
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        MinPQ<SearchNode> pq = new MinPQ<>(new NodeComparator());
        SearchNode currentNode = new SearchNode(initial, 0, null);
        pq.insert(currentNode);

        while (!pq.isEmpty()) {
            currentNode = pq.delMin();
            if (currentNode.state.isGoal()) {
                break;
            }
            for (WorldState nextState : currentNode.state.neighbors()) {
                if (currentNode.prev == null || !nextState.equals(currentNode.prev.state)) {
                    // BUG: should compare disTo first, but no big difference here. See lab11 MazeAStarPath.
                    SearchNode nextNode = new SearchNode(nextState, currentNode.moves + 1, currentNode);
                    pq.insert(nextNode);
                    searchCnt += 1;
                }
            }
        }

        Stack<WorldState> path = new Stack<>();
        for (SearchNode n = currentNode; n != null; n = n.prev) {
            path.push(n.state);
        }
        while (!path.isEmpty()) {
            solution.add(path.pop());
        }
    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     */
    public int moves() {
        return solution.size() - 1;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState to the solution.
     */
    public Iterable<WorldState> solution() {
        return solution;
    }
}
