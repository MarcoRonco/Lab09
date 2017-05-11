package it.polito.tdp.metrodeparis.model;

import java.util.ArrayList;
import java.util.List;
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

	private WeightedMultigraph<Fermata, Tratta> grafo = new WeightedMultigraph<>(Tratta.class);
	private MetroDAO dao = new MetroDAO();

	private List<Fermata> f = dao.getAllFermate();
	private List<Collegamento> c = dao.getAllCollegamenti();
	public Fermata getFermata(int i){
		for(Fermata x : f){
			if(x.getIdFermata()==i){
				return x;
			}
		}
		return null;
	}
	
	public Set<Fermata> createGraph() {
		
		for (Fermata x : f) {
			grafo.addVertex(x);
		}

		double tempo = 0;
		
		for(Collegamento y : c){
			if(grafo.containsVertex(y.getA()) && grafo.containsVertex(y.getB())){
				Tratta t = grafo.addEdge(y.getA(), y.getB());
				if(t!=null){
					System.out.println(t);
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
		return (d * v*3600);
	}
	
	public String camminoMinimo(Fermata a, Fermata b){
		
		if(a.equals(b))
			return "Fermata di partenza e di arrivo conicidenti";
		
		DijkstraShortestPath<Fermata, Tratta> shortest = new DijkstraShortestPath(grafo, a, b);
		String s = a.toString()+" -> ";
		double p = 0;
		int count = 0;
		
		for(Tratta x : shortest.getPathEdgeList()){
			s+=x.toString()+" -> ";
			p+=x.peso();
			count++;
		}
		
		p =(p+count*30);
		s+="\nTempo necessario per il cammino: "+p;
		return s;
	}
}
