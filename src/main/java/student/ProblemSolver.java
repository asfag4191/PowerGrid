package student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

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

        for (Edge<V> edge : g.adjacentEdges(vertex)) { //O(degree(v))/2m
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

                for (Edge<V> newEdge : g.adjacentEdges(edge.b)) { //O(degree(b))/2m
                    toSearch.add(newEdge); //O(log(m))
                }
            }
        }
        return mst; //O(1)
    }

    @Override
    public <V> V lca(Graph<V> g, V root, V u, V v) { //O(n)
        Map<V, V> parent = new HashMap<>(); //O(1)
        dfs(g, root, parent); //O(n)

        Set<V> ancestors = new HashSet<>(); //O(1)
        while (u != null) { //O(n)
            ancestors.add(u); //O(1)
            u = parent.get(u); //O(1)
        }
        while (!ancestors.contains(v)) { //O(n)
            v = parent.get(v); //O(1)
        }

        return v; //O(1)
    }

    private <V> void dfs(Graph<V> g, V root, Map<V, V> parent) { //O(n)
        Stack<V> toSearch = new Stack<>(); //O(1)
        toSearch.push(root); //O(1)
        parent.put(root, null); //O(1)

        while (!toSearch.isEmpty()) { //O(n)
            V node = toSearch.pop(); //O(1)

            for (V neighbor : g.neighbours(node)) { ////O(m * degree(node)) = O(n) fordi tre har n-1 kanter. 
                if (!parent.containsKey(neighbor)) { //O(1)
                    toSearch.push(neighbor); //O(1)
                    parent.put(neighbor, node); //O(1)
                }
            }
        }
    }

    @Override
    public <V> Edge<V> addRedundant(Graph<V> g, V root) {
        // Implement me :)
        return null;
    }

}
