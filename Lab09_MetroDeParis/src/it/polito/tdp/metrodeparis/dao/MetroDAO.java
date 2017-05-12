package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metrodeparis.model.Collegamento;
import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.FermataLinea;
import it.polito.tdp.metrodeparis.model.Linea;

public class MetroDAO {

	public Map<Integer, Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy "
						 + "FROM fermata "
						 + "ORDER BY nome ASC";
		

		Map<Integer ,Fermata> fermate = new HashMap<>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.put(rs.getInt("id_Fermata"), f);
			}
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}
		return fermate;
	}
	
	public Map<Integer, Collegamento> getAllCollegamenti() {

		final String sql = "SELECT id_stazP, id_stazA, id_connessione, id_linea "+
						   "FROM connessione";
		
		Map<Integer, Collegamento> c = new HashMap<Integer, Collegamento>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int a = rs.getInt("id_stazP");
				int b = rs.getInt("id_stazA");
				double l = rs.getDouble("id_linea");
				Collegamento x = new Collegamento(a, b, rs.getInt("id_connessione"), l);
				c.put(rs.getInt("id_connessione"), x);
			}
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}
		return c;
	}
	
	public Map<Double, Linea> getAllLinee() {

		final String sql = "SELECT id_linea , nome, velocita, intervallo, colore "+
						   "FROM linea";
		
		Map<Double, Linea> l = new HashMap<Double, Linea>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				double l1 = rs.getDouble("id_linea");
				Linea x = new Linea(l1, rs.getString("nome"), rs.getInt("velocita"), rs.getInt("intervallo"), rs.getString("colore"));
				l.put(l1, x);
			}
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}
		return l;
	}
	
	public Map<String, FermataLinea> getAllFermateLinee() {

		final String sql = "SELECT DISTINCT id_fermata, nome, coordx, coordy, id_linea "
						 + "FROM connessione, fermata "
						 + "WHERE connessione.id_stazP = fermata.id_fermata "
						 + "OR connessione.id_stazA = fermata.id_fermata "			
						 + "ORDER BY nome ASC";
		

		Map<String ,FermataLinea> fermate = new HashMap<>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				FermataLinea f = new FermataLinea(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")), rs.getDouble("id_linea"));
				String s = rs.getInt("id_Fermata")+","+rs.getDouble("id_linea");
				fermate.put(s, f);
			}
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}
		return fermate;
	}
}
