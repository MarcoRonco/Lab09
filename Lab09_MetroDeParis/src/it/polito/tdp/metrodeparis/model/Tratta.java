package it.polito.tdp.metrodeparis.model;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Tratta extends DefaultWeightedEdge{
	
	public Linea l;
	
	public Tratta(){}

	public double peso(){
		return this.getWeight();
	}
	
	@Override
	public String toString() {
		return this.getTarget().toString();
	}
}
