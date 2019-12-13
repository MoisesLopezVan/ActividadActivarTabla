/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basesql;

import com.mysql.jdbc.Connection;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


/**
 *
 * @author MoisesDario
 */
public class FXMLDocumentController implements Initializable {
       
    @FXML
    private Button btnConectar;

    @FXML
    private Text textEstado;
    
    @FXML
    private Button btnDesconectar;

    @FXML
    private TextField txtUser;
    
    @FXML
    private TextField txtPassword;

    @FXML
    private ImageView imageEstado2;

    @FXML
    private ImageView imageEstado1;
    
    @FXML
    private Text txtMensaje;
    
    @FXML
    private TextField TxConexion;

    Connection conn;    
    
    @FXML   
    private TableView<Ciudad> tablaSentencia;
    @FXML
    private TableColumn<Ciudad, String> txtNegocio;
    @FXML
    private TableColumn<Ciudad, String> txtCiudad;
    
    ObservableList<Ciudad> data = FXCollections.observableArrayList();

    @FXML
    private void clickConectar(ActionEvent event) {
         btnConectar.setDisable(true);
         btnDesconectar.setDisable(false);
       try{
           String SQL = "select idciudad, nombre from ciudad ";
           String url = "jdbc:mysql://localhost:3308/lmcd"; 
           //String url = "jdbc:mariadb://localhost:3308/lmcd"; 
           Class.forName("com.mysql.jdbc.Driver").newInstance();
           //Class.forName("org.mariadb.jdbc.Driver").newInstance();
           conn = (Connection) DriverManager.getConnection(url,txtUser.getText(),txtPassword.getText());
           TxConexion.setText("Conectado...\n" );
           txtNegocio.setText("IdCiudad");
           txtCiudad.setText("Nombre");
           Statement st = conn.createStatement();
           ResultSet rs = st.executeQuery(SQL);
           
           while(rs.next()){
                imageEstado1.setVisible(true);
                imageEstado2.setVisible(false);
                data.add(new Ciudad(rs.getString("idciudad") ,rs.getString("nombre") + "\n"));
                
           }
       }catch(Exception e){
           imageEstado2.setVisible(true);
           txtMensaje.setText(e.getMessage() + "\n" );
           txtMensaje.setText("Exception: \n" );
       }
        txtNegocio.setCellValueFactory(new PropertyValueFactory<Ciudad, String>("idciudad"));
        txtCiudad.setCellValueFactory(new PropertyValueFactory<Ciudad, String>("nombre"));   
        tablaSentencia.setItems(data);
    }

    @FXML
    void clickDesconectar(ActionEvent event) {
        btnConectar.setDisable(false);
        btnDesconectar.setDisable(true);
        try{
           imageEstado1.setVisible(false);
           imageEstado2.setVisible(true);
            conn.close();
            data.clear();
            TxConexion.setText("DESCONECTADO...\n" );  
        }catch(SQLException ex){
            txtMensaje.setText(ex.getMessage() + "\n" );
            txtMensaje.setText("Exception: \n" );
        }
    }
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
         
    }   
  
    
    //Creando la clase Negocio
    public static class Ciudad {
        
        String idciudad;
        String nombre;

        private Ciudad(String idciudad,String nombre) {
            this.idciudad = idciudad;
            this.nombre = nombre; 
        }

        public String getIdciudad() {
            return idciudad;
        }

        public void setIdciudad(String idciudad) {
            this.idciudad = idciudad;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        
    }  
}
