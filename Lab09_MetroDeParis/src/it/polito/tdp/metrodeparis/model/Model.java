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

	private DirectedWeightedMultigraph<FermataLinea, Tratta> grafo;
	private MetroDAO dao;
	private Map<String, FermataLinea> fl;
	private Map<Integer, Fermata> f;
	private Map<Integer, Collegamento> c;
	private Map<Double, Linea> l;
	
	public Model() {
		super();
		this.dao= new MetroDAO();
		this.fl = dao.getAllFermateLinee();
		this.f = dao.getAllFermate();
		this.c = dao.getAllCollegamenti();
		this.l = dao.getAllLinee();
	}

	public List<Fermata> createGraph() {
		
		grafo = new DirectedWeightedMultigraph<>(Tratta.class);
		
		for (FermataLinea x : fl.values()) {
			grafo.addVertex(x);
		}
		
		double tempo = 0;
		for(Collegamento y : c.values()){
			if(!f.get(y.getA()).equals(f.get(y.getB()))){
				FermataLinea a = fl.get(y.getA()+","+y.getL());
				FermataLinea b = fl.get(y.getB()+","+y.getL());
				Tratta t = grafo.addEdge(a, b);
				if(t!=null){
				tempo = this.tempo(grafo.getEdgeSource(t), grafo.getEdgeTarget(t), l.get(y.getL()));
				grafo.setEdgeWeight(t, tempo);
				}
			}
		}
		
		for (FermataLinea x : grafo.vertexSet()) {
			for(FermataLinea y : grafo.vertexSet()){
				if(y.getIdFermata()==x.getIdFermata() && y.getIdLinea()!=x.getIdLinea()){
						 Tratta t = grafo.addEdge(x, y);
						if(t!=null){
							grafo.setEdgeWeight(t, l.get(y.getIdLinea()).getIntervallo());
					 	}
				    
				}
			}
		}
		List<Fermata> b = new ArrayList<>(f.values());
		return b;
	}

	public double tempo(FermataLinea a, FermataLinea b, Linea l) {
		double d = LatLngTool.distance(a.getCoords(), b.getCoords(), LengthUnit.KILOMETER);
		int v = l.getVelocita();
		return ((d/v)*60);
	}
	
	public String camminoMinimo(Fermata a, Fermata b){
		
		if(a.getIdFermata()==b.getIdFermata())
			return "Fermata di partenza e di arrivo conicidenti";
		
		FermataLinea x = null;
		FermataLinea y = null;
		
		for(FermataLinea z : fl.values()){
			if(x!=null && y!=null)
				break;
			
			if(z.getIdFermata()==a.getIdFermata()){ x = z; }
			if(z.getIdFermata()==b.getIdFermata()){ y = z; }
		}
		
		DijkstraShortestPath<FermataLinea, Tratta> shortest = new DijkstraShortestPath(grafo, x, y);
		
		String s = a.toString();
		double p = 0;
		int count = 0;
		for(Tratta q : shortest.getPathEdgeList()){
			count++;
			if(grafo.getEdgeSource(q).getIdLinea()!=grafo.getEdgeTarget(q).getIdLinea()){
				
				if(count==shortest.getPathEdgeList().size()-1)
					break;
					
				s+="\n";
				s+="\nPrendo la Linea "+l.get(grafo.getEdgeTarget(q).getIdLinea()).getNome()+" :\n";
				s+=q.toString();
				
				if(count!=0)
					p+=q.peso();
				
			} else {
				s+=" -> "+q.toString();
				p+=q.peso()+0.5;				
			}
		}
		s+="\n";
		s+="\nTempo necessario per il cammino: "+p+" minuti";
		return s;
	}
}
