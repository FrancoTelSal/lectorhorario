/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebaaccess;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author reto3
 */
public class registroBD {
    private String badge;
    private String nombre;
    private String cedula;
    private String cargo;
    private LocalDateTime fechayhora;
    
    DateTimeFormatter formatHora = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    public registroBD() {
    }

    public registroBD(String badge, String nombre, String cedula, String cargo, LocalDateTime fechayhora) {
        this.badge = badge;
        this.nombre = nombre;
        this.cedula = cedula;
        this.cargo = cargo;
        this.fechayhora = fechayhora;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public LocalDateTime getFechayhora() {
        return fechayhora;
    }

    public void setFechayhora(LocalDateTime fechayhora) {
        this.fechayhora = fechayhora;
    }
    
    public String getFecha(){
        return fechayhora.getYear() + "-" + fechayhora.getMonthValue() + "-" + fechayhora.getDayOfMonth();
    }
    
    public LocalTime getHora(){
        String h,m,s;
        if (fechayhora.getHour()<10) {
            h = "0"+fechayhora.getHour(); 
        } else
            h= fechayhora.getHour()+"";
        if (fechayhora.getMinute()<10) {
            m = "0"+fechayhora.getMinute();
        } else
            m = fechayhora.getMinute()+"";
        if (fechayhora.getSecond()<10) {
            s = "0"+fechayhora.getSecond();
        } else
            s = fechayhora.getSecond()+"";
        String hora = h+":"+m+":"+s;
        /*String datemod = getFechayhora().toString();
        String date = datemod.replace("T", " ");*/
        return LocalTime.parse(hora, formatHora);
        
    }
    
    public String getHora_() {
        int hora = fechayhora.getHour();
        int minuto = fechayhora.getMinute();
        int segundo = fechayhora.getSecond();
        
        return Integer.toString(hora) + ":" + Integer.toString(minuto) + ":" + Integer.toString(segundo);
    }
   
}
