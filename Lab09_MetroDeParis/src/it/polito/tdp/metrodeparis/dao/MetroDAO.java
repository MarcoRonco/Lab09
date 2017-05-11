package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metrodeparis.model.Collegamento;
import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Linea;

public class MetroDAO {

	public List<Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy "
						 + "FROM fermata "
						 + "ORDER BY nome ASC";
		
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}
		return fermate;
	}
	
	public List<Collegamento> getAllCollegamenti() {

		final String sql = "SELECT id_stazP, id_stazA, id_connessione, linea.id_linea , nome, velocita, intervallo, colore "+
						   "FROM connessione, linea "+
						   "WHERE connessione.id_linea = linea.id_linea ";
		
		List<Collegamento> c = new ArrayList<Collegamento>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata a = new Fermata(rs.getInt("id_stazP"));
				Fermata b = new Fermata(rs.getInt("id_stazA"));
				Linea l = new Linea(rs.getDouble("id_linea"), rs.getString("nome"), rs.getInt("velocita"), rs.getInt("intervallo"), rs.getString("colore"));
				Collegamento x = new Collegamento(a, b, rs.getInt("id_connessione"), l);
				c.add(x);
			}
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}
		return c;
	}
}
