/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtmv;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author Lotus Clan
 */
public class FXMLDocumentController implements Initializable {
    
    
    @FXML private TextField fname,lname, txtSearch;

    @FXML private RadioButton rdoMale, rdoFemale;
    
    @FXML TableView tablePersons;
    
    @FXML TableColumn <String, Person> colFirstName, colLastName, colGender;
    
    @FXML TableColumn <Integer, Person> colAge, colPersonId;
    
    @FXML private ChoiceBox chAge;
    
    private final ToggleGroup group = new ToggleGroup();
    
    private String gender = "Male";
    
    @FXML
    public void savePerson(ActionEvent event){
        Person person = new Person(fname.getText(), lname.getText(), (int) chAge.getValue(), gender);
        int result;
        try {
            result = person.create();
            if(result>0)
            JOptionPane.showMessageDialog(null, person.getFname()+" is saved to the database.",
                    "Saved", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, person.getFname()+" not saved to the database.",
                    "Something happened", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Something happened", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    
    //search persons
    @FXML public void searchPersons(ActionEvent event) throws SQLException{
        //TableColumns
       
        
        Person p = new Person();
        ResultSet rs = p.findByFirstName(txtSearch.getText());
        
        while(rs.next()){
            tablePersons.getItems().add(new Person(
                    rs.getInt("pid"), rs.getString("fname"),
                    rs.getString("lname"), rs.getInt("age"),
                    rs.getString("gender")
            ));
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //radiobuttons
        group.getToggles().add(rdoMale);
        group.getToggles().add(rdoFemale);
        rdoMale.setUserData("Male");
        rdoFemale.setUserData("Female");
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                 if(group.getSelectedToggle()!=null){
                     gender=group.getSelectedToggle().getUserData().toString();
                 }
            }
        });
       
        
        
        //choicebox
        ObservableList<Object> cb = FXCollections.observableArrayList();    
        int j = 18;
        for(int i=0; j<=60; i++){
            cb.add(i, j);
            j++;
        }
        chAge.setItems(FXCollections.observableArrayList(cb));
        chAge.setValue(18);
        
        //TableView
        tablePersons.setPlaceholder(new Label("NO item to Display"));
        
        colPersonId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("fname"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lname"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    }    
    
}
