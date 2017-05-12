package it.polito.tdp.metrodeparis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.graph.WeightedMultigraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {

	private WeightedMultigraph<Fermata, Tratta> grafo;
	private MetroDAO dao;
	private Map<Integer, Fermata> f;
	private List<Collegamento> c;
	
	public Model() {
		super();
		this.dao= new MetroDAO();
		this.f = dao.getAllFermate();
		this.c = dao.getAllCollegamenti();
	}

	public Set<Fermata> createGraph() {
		
		grafo = new WeightedMultigraph<>(Tratta.class);
		
		for (Fermata x : f.values()) {
			grafo.addVertex(x);
		}

		double tempo = 0;
		
		for(Collegamento y : c){
			if(f.containsKey(y.getA()) && f.containsKey(y.getB())){
				Tratta t = grafo.addEdge(f.get(y.getA()), f.get(y.getB()));
				if(t!=null){
					tempo = this.tempo(grafo.getEdgeSource(t), grafo.getEdgeTarget(t), y.getL());
					grafo.setEdgeWeight(t, tempo);
				}
			}
		}
		return grafo.vertexSet();
	}

	public double tempo(Fermata a, Fermata b, Linea l) {
		double d = LatLngTool.distance(a.getCoords(), b.getCoords(), LengthUnit.KILOMETER);
		int v = l.getVelocita();
		return ((d/v)*60);
	}
	
	public String camminoMinimo(Fermata a, Fermata b){
		
		if(a.equals(b))
			return "Fermata di partenza e di arrivo conicidenti";
		
		DijkstraShortestPath<Fermata, Tratta> shortest = new DijkstraShortestPath(grafo, a, b);
		String s = a.toString()+" -> ";
		double p = 0;
		int count = 0;
		
		for(Tratta x : shortest.getPathEdgeList()){
			if(count==shortest.getPathEdgeList().size()-1)
				break;
			
			s+=x.toString()+" -> ";
			p+=x.peso();
			count++;
		}
		s+=b.toString();
		s+="\nTempo necessario per il cammino: "+p+" minuti";
		return s;
	}
}
