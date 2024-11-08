/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebaaccess;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author reto3
 */
public class con_Access {
    Connection conectar = null; //variable de conexion
    File bd = null;

    public File getBd() {
        return bd;
    }

    public void setBd(File bd) {
        this.bd = bd;
    }
    
    //Funcion conexionbase, primera funcion realizada para las pruebas de conexion a la bd
    //junto con la ejecucion y muestra de sentencias, en este caso SELECT.
    /*public void conexionbase() {
        mainFrame f = new mainFrame();
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            String rutadb = bd.getAbsolutePath();
            for (int i = 0; i < rutadb.length(); i++) {
                Character a = rutadb.charAt(i);
                Character b = '\\';
                if (a.equals(b)) {
                    rutadb = rutadb.substring(0, i) + "\\" + rutadb.substring(i);
                    i++;
                }
            }
            System.out.println(rutadb);
            //String rutadb = "C:\\Users\\reto3\\OneDrive\\Escritorio\\ARCHIVOS PASANTE\\BD CHECKINOUT\\bdprueba.accdb";
            String url = "jdbc:ucanaccess://"+rutadb+"; memory=false";
            conectar = DriverManager.getConnection(url);
            sentencia = conectar.createStatement();
            JOptionPane.showMessageDialog(null, "Conectado a la base de datos", "Conectado", 3, null);
            f.setVisible(true);
            
            
        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, e, "Error al conectar la base de datos", 0, null);
            System.out.print(e);
            System.exit(0);
            
        }

        try {
            //Sentencia SELECT guardada en la variable sqlString
            String sqlString = "SELECT TOP 50 temporal.Badgenumber, temporal.Name, temporal.cedula, temporal.cargo, CHECKINOUT.CHECKTIME"+
            " FROM (CHECKINOUT INNER JOIN USERINFO ON CHECKINOUT.USERID = USERINFO.USERID) INNER JOIN temporal ON USERINFO.Badgenumber = temporal.Badgenumber;";
            
            //Ejecucion de la sentencia en la base de datos y guardado del resultado de la misma
            ResultSet resultado = sentencia.executeQuery(sqlString);

            //Recorrido del ResultSet, funcionando de manera similar a un array
            //Siendo cada posicion una fila de los datos obtenidos. 
            while(resultado.next()){
                String Badge = resultado.getString("Badgenumber");
                String Name = resultado.getString("Name");
                String Cedula = resultado.getString("cedula");
                String Cargo = resultado.getString("cargo");
                String CheckTimes = resultado.getString("CHECKTIME");

                System.out.print(" | "+Badge+" | "+Name+" | "+Cedula+" | "+Cargo+" | "+CheckTimes+"\n");
            }

            conectar.close();
        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, e, "Error al ejecutar la sentencia", 0, null);
            System.out.print(e);
            System.exit(0);
        }
    }*/
    
    //Funcion conectar, permitiendo la conexion de bd y regresando un valor tipo Connection
    public Connection conectarBD(){
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            String rutadb = bd.getAbsolutePath();
            for (int i = 0; i < rutadb.length(); i++) {
                Character a = rutadb.charAt(i);
                Character b = '\\';
                if (a.equals(b)) {
                    rutadb = rutadb.substring(0, i) + "\\" + rutadb.substring(i);
                    i++;
                }
            }
            System.out.println(rutadb);
            //String rutadb = "C:\\Users\\reto3\\OneDrive\\Escritorio\\ARCHIVOS PASANTE\\BD CHECKINOUT\\bdprueba.accdb";
            String url = "jdbc:ucanaccess://"+rutadb+"; memory=true";
            conectar = DriverManager.getConnection(url);
            JOptionPane.showMessageDialog(null, "Conectado a la base de datos", "Conectado", 3, null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, new JLabel("Archivo Incompatible\n " + e, JLabel.CENTER ), "Error al conectar la base de datos", 0, null);
            System.out.print(e);
            //System.exit(0);
        }
            
        return conectar;
    }

}
