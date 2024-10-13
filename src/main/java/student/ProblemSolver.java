package student;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

import graph.Edge;
import graph.Graph;
import graph.WeightedGraph;

public class ProblemSolver implements IProblem {

	//prims algorithm for å finne minimum spanning tree

	//sykelsjekken er indirekte siden jeg sjekker hvilke noder som allerede er lagt til i mst. 
	//hvis en kant har begge nodene i mst, vil den ikke bli lagt til i mst.
	@Override
	public <V, E extends Comparable<E>> ArrayList<Edge<V>> mst(WeightedGraph<V, E> g) {
		HashSet<V> found = new HashSet<>(); //nodene som er funnet
		PriorityQueue<Edge<V>> toSearch = new PriorityQueue<Edge<V>>(g);	 //kantene som skal sjekkes
		ArrayList<Edge<V>> mst = new ArrayList<>(); //listen med kantene som skal returneres

		V vertex=g.getFirstNode(); //finner første node
		found.add(vertex); //legger til første node i found, denne har da ikke noen kanter

		//Legger til alle kanter fra første node i toSearch, bruker adjace
		for (Edge<V> edge: g.adjacentEdges(vertex)){
			toSearch.add(edge);
		}
		while(!toSearch.isEmpty()){
			Edge<V> edge=toSearch.poll(); //fjerner minste kant i toSearch


			//hvis begge nodene allerede er en del av treet, gå til neste kant
			//vil det å legge til denne kanten skape en sykel. 
			if(found.contains(edge.a) && found.contains(edge.b)){
				continue; //hvis begge nodene er funnet fra før, gå til neste kant
			}

			//en av nodene er ny og kan legge til i mst
			//sjekker da b som er den noden vi går til
			
			//hvis kanten ikke er funnet fra før, legg til i mst og legg til noden i found
			if(!found.contains(edge.b)){
				mst.add(edge); //legger til kanten i mst
				found.add(edge.b); //legger til noden i found

				//legger til alle kanter fra noden i toSearch
				for (Edge<V> newEdge: g.adjacentEdges(edge.b)){
					toSearch.add(newEdge);
				}
			} //b er ende noden, ser dette i Graph, 
		}

		return mst; //returnerer mst
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
