/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import conexion.ConexionAbarrotesBD;
import conexion.ConexionConfig;
import dao.Productos;
import dao.Usuarios;
import excepciones.DAOException;
import excepciones.ErrorConexionException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetosNegocio.Producto;
import objetosNegocio.Usuario;

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
        
        /**
        Productos productos = new Productos(conn);
        
        Producto p;
        try {
            p = productos.obten(new Producto("AMC0101223"));
        } catch (DAOException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
        * */
        
        Usuarios usuarios = new Usuarios(conn);
        
        Usuario usr = new Usuario(
                "92199192",
                "Juan de la barrera",
                "64412388128",
                "pedroshesh124@gmail.com",
                "administrador"
        );
        
        usr.setContrasena("nmap12345");
        
        Usuario shesh = null;
        
        try {
            shesh = usuarios.obten(usr);
            System.out.println(shesh);
        } catch (DAOException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            usuarios.agregar(usr);
        } catch (DAOException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //usr.setNombre("Saul Neri");
        //usr.setRolUsuario("asaosoaoslls");
        
        /*
        try {
            usuarios.actualiza(usr);
        } catch (DAOException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            usuarios.elimina(usr);
        } catch (DAOException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        } 
        */
        
        try {
            usuarios.consultaUsuarios(); // se cargan 20
            usuarios.consultaUsuarios(); // se cargan OTROS 20...
        } catch (DAOException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int i = 1;
        
        for (Usuario u: usuarios.lista()) {
            System.out.println(i + "# " + u);
            
            i++;
        }
        
        try {
            conn.desconectar();
        } catch (ErrorConexionException ex) {
            System.out.println("shesh!");
        }
        
    }
}
