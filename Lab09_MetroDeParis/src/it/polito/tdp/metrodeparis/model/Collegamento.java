package it.polito.tdp.metrodeparis.model;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.*;

public class Collegamento{
	
	private int a;
	private int b;
	private int id;
	private Linea l;
	
	public Collegamento(int a, int b, int id, Linea l) {
		super();
		this.a = a;
		this.b = b;
		this.id = id;
		this.l = l;
	}

	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}

	public int getId() {
		return id;
	}

	public Linea getL() {
		return l;
	}
}
