package it.polito.tdp.metrodeparis.model;

import com.javadocmd.simplelatlng.LatLng;

public class FermataLinea{

	private int idFermata;
	private String nome;
	private LatLng coords;
	private double idLinea;

	public FermataLinea(int idFermata, String nome, LatLng coords, double idLinea) {
		this.idFermata = idFermata;
		this.nome = nome;
		this.coords = coords;
		this.idLinea = idLinea;
	}

	public int getIdFermata() {
		return idFermata;
	}

	public String getNome() {
		return nome;
	}

	public LatLng getCoords() {
		return coords;
	}

	public double getIdLinea() {
		return idLinea;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idFermata;
		long temp;
		temp = Double.doubleToLongBits(idLinea);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FermataLinea other = (FermataLinea) obj;
		if (idFermata != other.idFermata)
			return false;
		if (Double.doubleToLongBits(idLinea) != Double.doubleToLongBits(other.idLinea))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}
}
