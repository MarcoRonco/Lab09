package it.polito.tdp.metrodeparis.model;

public class Linea {

	private double id;
	private String nome;
	private int velocita;
	private int intervallo;
	private String colore;
	
	public Linea(double double1) {
		this.id=double1;
	}

	public Linea(double id, String nome, int velocita, int intervallo, String colore) {
		super();
		this.id = id;
		this.nome = nome;
		this.velocita = velocita;
		this.intervallo=intervallo;
		this.colore = colore;
	}

	public double getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public int getVelocita() {
		return velocita;
	}

	public String getColore() {
		return colore;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(id);
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
		Linea other = (Linea) obj;
		if (Double.doubleToLongBits(id) != Double.doubleToLongBits(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Linea [nome=" + nome + "]";
	}
}
