package it.polito.tdp.metrodeparis.dao;

import java.util.List;

import it.polito.tdp.metrodeparis.model.Fermata;

public class TestDAO {

	public static void main(String[] args) {
		
		MetroDAO metroDAO = new MetroDAO();
		
		System.out.println(metroDAO.getAllFermate());
		System.out.println("-----------------------------------");
		System.out.println(metroDAO.getAllCollegamenti());
		System.out.println("-----------------------------------");
		System.out.println(metroDAO.getAllLinee());
	}

}
