package pruebas;


import java.util.Date;
import objetosServicio.Fecha;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Saul Neri
 */
public class PruebaFecha {
    public static void main(String[] args) {
        
        Date d = new Date();
        
        d.setDate(24);
        d.setMonth(10);
        d.setYear(2023);
        
        Fecha fecha = new Fecha(d);
        
        System.out.println(fecha);
    }
}
