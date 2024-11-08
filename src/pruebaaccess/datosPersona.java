/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebaaccess;

import java.util.List;

/**
 *
 * @author reto3
 */
public class datosPersona {

    private String nombre;
    private String fecha;
    private List<String> hora;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public datosPersona (String nombre, String fecha, List<String> hora) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public List<String> getHora() {
        return hora;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(List<String> hora) {
        this.hora = hora;
    }

    public void agregarHora(String hora_) {
        hora.add(hora_);
    }
}
