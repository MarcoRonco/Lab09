package it.polito.tdp.metrodeparis;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MetroDeParisController {
	
	private Model m;
	
	public void setModel(Model m){
		
		this.m = m;
		List<Fermata> f = m.createGraph();
		comboPartenza.getItems().addAll(f);
		comboArrivo.getItems().addAll(f);
	}

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="comboPartenza"
    private ComboBox<Fermata> comboPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcola"
    private Button btnCalcola; // Value injected by FXMLLoader

    @FXML // fx:id="comboArrivo"
    private ComboBox<Fermata> comboArrivo; // Value injected by FXMLLoader
    
    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcola(ActionEvent event) {
    	if(comboPartenza.getValue()!=null && comboArrivo.getValue()!=null){
    		String y = m.camminoMinimo(comboPartenza.getValue(), comboArrivo.getValue());
    		txtResult.appendText(y);
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert comboPartenza != null : "fx:id=\"comboPartenza\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert comboArrivo != null : "fx:id=\"comboArrivo\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MetroDeParis.fxml'.";

        
    }
}