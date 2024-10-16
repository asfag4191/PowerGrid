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

            for (V neighbor : g.neighbours(node)) {
                ////O(m * degree(node)) = O(n) fordi tre har n-1 kanter. 
                if (!parent.containsKey(neighbor)) { //O(1)
                    toSearch.push(neighbor); //O(1)
                    parent.put(neighbor, node); //O(1)
                }
            }
        }
    }

	@Override
	public <V> Edge<V> addRedundant(Graph<V> g, V root) {
		Map<V, Integer> subTree = SubTreeSize(g, root);
	
		V firstNode = null, secondNode = null;
		int maxSubTreeSize1 = 0, maxSubTreeSize2 = 0;
	
		for (V child : g.neighbours(root)) {
			int childSize = subTree.get(child);
			if (childSize > maxSubTreeSize1) {
				maxSubTreeSize2 = maxSubTreeSize1;
				secondNode = firstNode;
	
				maxSubTreeSize1 = childSize;
				firstNode = child;
			} else if (childSize > maxSubTreeSize2) {
				maxSubTreeSize2 = childSize;
				secondNode = child;
			}
		}
	
		V deepestFirstNode = deepestNodeWithMostChildren(g, subTree, firstNode);
		V deepestSecondNode = deepestNodeWithMostChildren(g, subTree, secondNode);
	
		return new Edge<>(deepestFirstNode, deepestSecondNode);
	}
	
	
    // Metode som mapper dybden og størrelsen til treet for hver node

    private <V> Map<V, Integer> SubTreeSize(Graph<V> g, V root) {
        HashMap<V, Integer> subTree = new HashMap<>();
        Set<V> found = new HashSet<>();  // Set for å spore besøkte noder
        Calculatedepth(g, root, found, subTree);  // Riktig parametere
        return subTree;
    }

// Beregner dybden og størrelsen til treet ved DFS for hver node
    private <V> void Calculatedepth(Graph<V> g, V node, Set<V> found, Map<V, Integer> subTree) {
        Stack<V> toSearch = new Stack<>();  // Holder styr på nodene som skal søkes
        Stack<V> reversed = new Stack<>();  // Må telle fra bunn og opp for å finne størrelsen på deltrærne

        // Start DFS fra roten eller startnoden
        toSearch.push(node);
        found.add(node);

        // DFS-besøk av alle noder
        while (!toSearch.isEmpty()) {
            V currentNode = toSearch.pop();
            reversed.push(currentNode);  // Legger til i post-order

            // Gå gjennom naboene til noden
            for (V neighbour : g.neighbours(currentNode)) {
                if (!found.contains(neighbour)) {
                    toSearch.push(neighbour);  // Legger til naboene i stakken
                    found.add(neighbour);      // Legger til naboene i "funnet"
                }
            }
        }

        // Reversed behandling
        while (!reversed.isEmpty()) {
            V nodeFromReversed = reversed.pop();  // Henter den siste noden (bladnode eller fått alle barn behandlet)
            int size = 1;  // Starter å telle med seg selv

            // Gå gjennom naboene for å finne størrelsen på deltrærne
            for (V neighbour : g.neighbours(nodeFromReversed)) {
                if (subTree.containsKey(neighbour)) {
                    size += subTree.get(neighbour);  // Legger til størrelsen på barnenodenes subtre
                }
            }

            subTree.put(nodeFromReversed, size);  // Legger til størrelsen på deltrærne i kartet
            System.out.println(".");  // For debugging (kan fjernes)
        }
    }

	//Finne den dypeste noden med det største sub treet. 
	private <V> V deepestNodeWithMostChildren(Graph<V> g, Map<V, Integer> subTree, V firstNode) {
		Integer currentScore = subTree.get(firstNode); // Hent størrelsen på subtreet for noden
		if (currentScore == 1) return firstNode;  // Hvis noden er en bladnode, returner den
		
		Integer bestScore = 0;
		V bestNode = firstNode;
	
		// Gå gjennom naboene for å finne barnet med det største subtreet
		for (V child : g.neighbours(firstNode)) {
			Integer childScore = subTree.get(child);  // Størrelsen på barnets subtre
			if (childScore > currentScore) continue;  // Ikke gå tilbake til foreldre-noder
			else if (childScore > bestScore) {
				bestScore = childScore;
				bestNode = child;  // Velg barnet med det største subtreet
			}
		}
	
		// Rekursivt gå nedover til du finner den dypeste noden med det største subtreet
		return deepestNodeWithMostChildren(g, subTree, bestNode);
	}
}

    

    //dypeste node med flest barn 
