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
 * @author MIC-P ICT
 */
public class Person extends MyConnection{
    private String fname, lname ,gender;
    private int age, id;
    
    //constructors
    public Person(){}
    
    public Person(String fname, String lname, int age, String gender){
        this.fname=fname;
        this.lname=lname;  
        this.age=age;
        this.gender=gender;
    }
    
    public Person(int id, String fname, String lname, int age, String gender){
        this.id=id;
        this.fname=fname;
        this.lname=lname;  
        this.age=age;
        this.gender=gender;
    }
    
    
    //SETTERS
    public void setId(int id){
        this.id=id;
    }
    public void setFname(String fname){
        this.fname=fname;
    }
    public void setLname(String lname){
        this.lname=lname;
    }
    public void setAge(int age){
        this.age=age;
    }
    public void setGender(String gender){
        this.gender=gender;
    }
    
    
    //GETTERS
    public int getId(){
        return this.id;
    }
    public String getFname(){
       return this.fname;
    }
     public String getLname(){
       return this.lname;
    }
     public int getAge(){
       return this.age;
    }
     public String getGender(){
       return this.gender;
    }
    
    /**
     * 
     * @return
     * @throws SQLException 
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public int create() throws SQLException{
        
        int result = 0;
         PreparedStatement stmt = this.con.prepareStatement("insert into persons(fname,lname,age, gender)" +
                    " values(?,?,?,?);");
        try {
            stmt.setString(1, this.fname);
            stmt.setString(2, this.lname);
            stmt.setInt(3, this.age);
            stmt.setString(4, this.gender);
            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Something happened", JOptionPane.ERROR_MESSAGE);
            result =-1;
        }
        finally{
            stmt.close();
            this.con.close();
        }
        return result;
    }
    
    //returns record signified by id
    public ResultSet findById(int id){
        
        String sql="SELECT * FROM persons WHERE pid=?;";
         try {
            PreparedStatement stmt = this.con.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            return stmt.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Something happened", JOptionPane.ERROR_MESSAGE);
            return null;
        }

    }
    
    
    public ResultSet findByFirstName(String fname) throws SQLException{
        String sql="SELECT * FROM persons WHERE fname = ?;";
         try {
            PreparedStatement stmt = this.con.prepareStatement(sql);
            stmt.setString(1, fname);
            return stmt.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Something happened", JOptionPane.ERROR_MESSAGE);
            return null;
        }
         
    }
    
    //returns all records
    public ResultSet findAll(){
        try {
            PreparedStatement stmt = this.con.prepareStatement("SELECT * FROM persons;");
            return stmt.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Something happened", JOptionPane.ERROR_MESSAGE);
           return null;
        }
    }
}
