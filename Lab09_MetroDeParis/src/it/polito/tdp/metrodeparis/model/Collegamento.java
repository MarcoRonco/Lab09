package it.polito.tdp.metrodeparis.model;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.*;

public class Collegamento{
	
	private Fermata a;
	private Fermata b;
	private int id;
	private Linea l;
	
	public Collegamento(Fermata a, Fermata b, int id, Linea l) {
		super();
		this.a = a;
		this.b = b;
		this.id = id;
		this.l = l;
	}

	public Fermata getA() {
		return a;
	}

	public Fermata getB() {
		return b;
	}

	public int getId() {
		return id;
	}

	public Linea getL() {
		return l;
	}
}
