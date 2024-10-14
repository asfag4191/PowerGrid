package student;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

import graph.Edge;
import graph.Graph;
import graph.WeightedGraph;

public class ProblemSolver implements IProblem {

    @Override
    public <V, E extends Comparable<E>> ArrayList<Edge<V>> mst(WeightedGraph<V, E> g) { //O(mlog(m))
        HashSet<V> found = new HashSet<>(); //O(1)
        PriorityQueue<Edge<V>> toSearch = new PriorityQueue<>(g); //O(m)
        ArrayList<Edge<V>> mst = new ArrayList<>(); //O(1)

        V vertex = g.getFirstNode(); //O(1)
        found.add(vertex); //O(1)

        for (Edge<V> edge : g.adjacentEdges(vertex)) { //en node: O(degree(v)), hele: O(2m)
            toSearch.add(edge); //O(log(m))
        }
        while (!toSearch.isEmpty()) { //O(m)
            Edge<V> edge = toSearch.poll(); //O(log(m))

            if (found.contains(edge.a) && found.contains(edge.b)) { //O(1)
                continue; //O(1)
            }

            if (!found.contains(edge.b)) { //O(1)
                mst.add(edge); //O(1)
                found.add(edge.b); //O(1)

                for (Edge<V> newEdge : g.adjacentEdges(edge.b)) { //en node: O(degree(b)), hele: O(2m)
                    toSearch.add(newEdge); //O(log(m))
                }
            }
        }
        return mst; //O(1)
    }

    @Override
    public <V> V lca(Graph<V> g, V root, V u, V v) {
        // Implement me :)
        return null;
    }

    @Override
    public <V> Edge<V> addRedundant(Graph<V> g, V root) {
        // Implement me :)
        return null;
    }

}
