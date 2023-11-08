/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import conexion.ConexionAbarrotesBD;
import conexion.ConexionConfig;
import dao.Productos;
import excepciones.DAOException;
import excepciones.ErrorConexionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetosNegocio.Producto;

/**
 *
 * @author Saul Neri
 */
public class PruebaProductos {
    public static void main(String[] args) {
        ConexionAbarrotesBD conn = new ConexionAbarrotesBD(new ConexionConfig("abarrotes", "9999", "root", ""));
        
        try {
            conn.conectar();
        } catch (ErrorConexionException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Productos productos = new Productos(conn);
        
        Producto p = productos.obten(new Producto("AMC0101223"));
        
        p = new Producto("AMC0101", "Mazapan", 'E', "pz");
        
        try {
            productos.agrega(p);
        } catch (DAOException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //p.setNombre("Lala 100");
        
        try {
            productos.actualiza(p);
        } catch (DAOException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //p.setClave("AMC0101");
        
         try {
            productos.actualiza(p);
        } catch (DAOException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(p);
        
        p.setClave("CLAVEXX");
        
        try {
            productos.elimina(p);
        } catch (DAOException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            conn.desconectar();
        } catch (ErrorConexionException ex) {
            System.out.println("shesh!");
        }
        
    }
}
