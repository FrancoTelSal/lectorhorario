/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebaaccess;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author reto3
 */
public class datosMostrar {

    private String badgenum;
    private String Name;
    private String cedula;
    private String cargo;
    private LocalDateTime hyf_primera;
    private LocalDateTime hyf_ultima;
    private String horario;
    private String calculo;
    
    DateTimeFormatter formatHora = DateTimeFormatter.ofPattern("HH:mm:ss");

    public datosMostrar() {
    }

    public datosMostrar(String badgenum, String Name, String cedula, String cargo, LocalDateTime hyf_primera, LocalDateTime hyf_ultima, String horario, String calculo) {
        this.badgenum = badgenum;
        this.Name = Name;
        this.cedula = cedula;
        this.cargo = cargo;
        this.hyf_primera = hyf_primera;
        this.hyf_ultima = hyf_ultima;
        this.horario = horario;
        this.calculo = calculo;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
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

    
    public String getBadgenum() {
        return badgenum;
    }

    public void setBadgenum(String badgenum) {
        this.badgenum = badgenum;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getCalculo() {
        return calculo;
    }

    public void setCalculo(String calculo) {
        this.calculo = calculo;
    }

    public LocalDateTime getHyf_primera() {
        return hyf_primera;
    }

    public void setHyf_primera(LocalDateTime hyf_primera) {
        this.hyf_primera = hyf_primera;
    }

    public LocalDateTime getHyf_ultima() {
        return hyf_ultima;
    }

    public void setHyf_ultima(LocalDateTime hyf_ultima) {
        this.hyf_ultima = hyf_ultima;
    }

    

    public String getFecha() {
        return hyf_primera.getYear() + "-" + hyf_primera.getMonthValue() + "-" + hyf_primera.getDayOfMonth();
    }

    public LocalTime getPHora() {       
        
        //String hora = hyf_primera.getHour() + ":" + hyf_primera.getMinute() + ":" + hyf_primera.getSecond();
        //return LocalTime.parse(dateFormat.format(hora), formatHora);
        return LocalTime.parse(hora(getHyf_primera()), formatHora);
    }
    
    public LocalTime getUHora() {      
        
        //String hora = hyf_ultima.getHour() + ":" + hyf_ultima.getMinute() + ":" + hyf_ultima.getSecond();
        //return LocalTime.parse(dateFormat.format(hora), formatHora);
        return LocalTime.parse(hora(getHyf_ultima()), formatHora);
    }
    
    public String hora(LocalDateTime ldt){
        String h,m,s;
        if (ldt.getHour()<10) {
            h = "0"+ldt.getHour(); 
        } else
            h= ldt.getHour()+"";
        if (ldt.getMinute()<10) {
            m = "0"+ldt.getMinute();
        } else
            m = ldt.getMinute()+"";
        if (ldt.getSecond()<10) {
            s = "0"+ldt.getSecond();
        } else
            s = ldt.getSecond()+"";
        return h+":"+m+":"+s;
    }
}
