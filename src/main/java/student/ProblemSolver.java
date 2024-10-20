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
    public <V, E extends Comparable<E>> ArrayList<Edge<V>> mst(WeightedGraph<V, E> g) { //O(mlog(m)), spørs om n eller m er størst
        HashSet<V> found = new HashSet<>(); //O(1)
        PriorityQueue<Edge<V>> toSearch = new PriorityQueue<>(g); //O(m)
        ArrayList<Edge<V>> mst = new ArrayList<>(); //O(1)

        V vertex = g.getFirstNode(); //O(1)
        found.add(vertex); //O(1)

        for (Edge<V> edge : g.adjacentEdges(vertex)) { //O(degree(v)  
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

                for (Edge<V> newEdge : g.adjacentEdges(edge.b)) { //O(degree(b))
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

            for (V neighbor : g.neighbours(node)) { //O(m * degree(node)) = O(n), tre har n-1 kanter. 
                if (!parent.containsKey(neighbor)) { //O(1)
                    toSearch.push(neighbor); //O(1)
                    parent.put(neighbor, node); //O(1)
                }
            }
        }
    }

    @Override
    public <V> Edge<V> addRedundant(Graph<V> g, V root) {
        Map<V, Integer> subTree = SubTreeSize(g, root); //O(n)
        V firstNode = root; //O(1)
        V secondNode = root; //O(1)		
        Integer firstSubTree = 0; //O(1)
        Integer secondSubTree = 0; //O(1)

        if (g.degree(root) == 1) {
            secondNode = deepestNodeWithMostChildren(g, subTree, root); //O(n)
        } else {
            for (V node : g.neighbours(root)) { //O(degree)
                Integer nodeSize = subTree.get(node); //O(1)
                if (nodeSize > firstSubTree) { //O(1)
                    secondSubTree = firstSubTree; //O(1)
                    secondNode = firstNode; //O(1)

                    firstSubTree = nodeSize; //O(1)
                    firstNode = node; //O(1)
                } else if (nodeSize > secondSubTree) { //O(1)
                    secondSubTree = nodeSize; //O(1)
                    secondNode = node; //O(1)
                }
            }
            firstNode = deepestNodeWithMostChildren(g, subTree, firstNode); //O(n)
            secondNode = deepestNodeWithMostChildren(g, subTree, secondNode); //O(n)
        }

        return new Edge<V>(firstNode, secondNode);
    }
	

	//Beregner størrelsen til alle noder i treet
    private <V> Map<V, Integer> SubTreeSize(Graph<V> g, V root) { //O(n)
        HashMap<V, Integer> subTree = new HashMap<>(); //O(1)
        Set<V> found = new HashSet<>();  ///O(1)
        Calculatedepth(g, root, found, subTree); //O(n)
        return subTree; //O(1)
    }

	//Beregner dybden og størrelsen til alle noder i treet
    private <V> void Calculatedepth(Graph<V> g, V node, Set<V> found, Map<V, Integer> subTree) {
        Stack<V> toSearch = new Stack<>();  // O(1)
        Stack<V> reversed = new Stack<>();  //O(1)

        toSearch.push(node); //O(1)
        found.add(node); //O(1)

        while (!toSearch.isEmpty()) { //O(n)
            V currentNode = toSearch.pop(); //O(1)
            reversed.push(currentNode);  //O(1)

            for (V neighbour : g.neighbours(currentNode)) { //O(degree(currentNode))
                if (!found.contains(neighbour)) {
                    toSearch.push(neighbour); 
                    found.add(neighbour);   
                }
            }
        }

        while (!reversed.isEmpty()) { //O(n)
            V nodeFromReversed = reversed.pop();
            int size = 1; 

            for (V neighbour : g.neighbours(nodeFromReversed)) { //O(degree*n)=O(n)
                if (subTree.containsKey(neighbour)) {
                    size += subTree.get(neighbour);  
                }
            }

            subTree.put(nodeFromReversed, size); //O(1)
    }
	}
	//Finner den noden som er lengst unna roten og har flest barn
    private <V> V deepestNodeWithMostChildren(Graph<V> g, Map<V, Integer> subTree, V currentNode) { //O(n)
        Integer currentScore = subTree.get(currentNode); //O(1)
        if (currentScore == 1) {
            return currentNode;  
        }
        Integer bestScore = 0;
        V bestNode = currentNode;

        for (V child : g.neighbours(currentNode)) { //O(degree)
            Integer childScore = subTree.get(child); 
            if (childScore > currentScore) {
                continue; 
             }else if (childScore > bestScore) {
                bestScore = childScore;
                bestNode = child;
            }
        }
        return deepestNodeWithMostChildren(g, subTree, bestNode);
    }
}
