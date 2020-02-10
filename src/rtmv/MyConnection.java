/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtmv;


import java.sql.*;

import javax.swing.JOptionPane;
/**
 *
 * @author Lotus Clan
 */
public class MyConnection{
    protected Connection con=null;
    public MyConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rtmv", "root", "");
            
        }catch(SQLException | ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
        }
    }
}
