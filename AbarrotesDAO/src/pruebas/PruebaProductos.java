/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import conexion.ConexionAbarrotesBD;
import conexion.ConexionConfig;
import dao.PersistenciaListas;
import dao.Productos;
import dao.ProductosEmpacados;
import dao.ProductosGranel;
import dao.Usuarios;
import excepciones.DAOException;
import excepciones.ErrorConexionException;
import excepciones.PersistenciaException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetosNegocio.MovimientoEmpacado;
import objetosNegocio.MovimientoGranel;
import objetosNegocio.Producto;
import objetosNegocio.ProductoEmpacado;
import objetosNegocio.ProductoGranel;
import objetosNegocio.Usuario;
import objetosServicio.Fecha;
import plantillas.ValidacionesAbarrotes;

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
        
        PersistenciaListas persistencia = new PersistenciaListas(conn); 
        
        // =========================[MOVIMIENTOS GRANEL]==========================


        try {
            persistencia.agregar(new Producto("AMO1010", "Pepino", 'G', "kg"));
        } catch (PersistenciaException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Producto p = null;
        
        try {
            p = persistencia.obten(new Producto("AMO1010"));
            
            if (p == null) {
                System.out.println("NO ENCONTRADO!!!");
                return;
            }
            
            System.out.println("SHESH: " + p);
        } catch (PersistenciaException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ProductoGranel pg = new ProductoGranel(p, 88.942f);
        
        // anadimos una compra (movimiento) de ese producto a granel
        MovimientoGranel mov = new MovimientoGranel(
                "LOL9999",
                new Fecha(),
                false,
                pg,
                new Usuario(100002)
        );
        
        try {
            persistencia.agregarCompra(mov);
            System.out.println("COMPRA REALIZADA");
        } catch (PersistenciaException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            mov = persistencia.obtenCompra(mov);
            
            if (mov == null) {
                System.out.println("Compra no encontrada!!!");
            }
            
            System.out.println(mov);
        } catch (PersistenciaException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        mov.getProductoGranel().setCantidad(900.234f);
        
        try {
            persistencia.actualizarCompra(mov);
            System.out.println("Se actualizo con exito la compra!!!");
        } catch (PersistenciaException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*
        try {
            persistencia.eliminarCompra(mov);
            System.out.println("Producto eliminado!!!");
        } catch (PersistenciaException ex) {
            throw new Error(ex);
        }
        */
        
        try {
            MovimientoGranel movimiento = persistencia.obtenCompra(mov);
            
            if (movimiento == null) {
                System.out.println("La compra no se encontro!!!");
            }
            
            System.out.println(movimiento);
            
        } catch (PersistenciaException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
  
        
        // =============================[MOVIMIENTOS EMPACADOS]================================
        
        /*
        Producto p = null;
            
        try {
            p = persistencia.obten(new Producto("SNK0012"));
            
            if (p == null) {
                System.out.println("NO ENCONTRADO!!!");
                return;
            }
            
            System.out.println("SHESH: " + p);
        } catch (PersistenciaException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ProductoEmpacado pe = new ProductoEmpacado(p, 20);
        
        // anadimos una compra (movimiento) de ese producto empacado
        MovimientoEmpacado movemp = new MovimientoEmpacado(
                "LXX2222",
                new Fecha(25, 11, 2023),
                false,
                pe,
                new Usuario(100000)
        );
        
        System.out.println(new Fecha(25, 11, 2023));
        
        try {
            persistencia.agregarCompra(movemp);
            System.out.println("COMPRA REALIZADA");
        } catch (PersistenciaException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            movemp = persistencia.obtenCompra(movemp);
            
            if (movemp == null) {
                System.out.println("Compra no encontrada!!!");
            }
            
            System.out.println(movemp);
        } catch (PersistenciaException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pe.setClave("SNK0017");
        pe.setCantidad(125);
        
        movemp.setProductoEmpacado(pe);
        
        try {
            persistencia.actualizarCompra(movemp);
            System.out.println("Se actualizo con exito la compra!!!");
        } catch (PersistenciaException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("# " + movemp.getFecha());
        
        /*
        try {
            persistencia.eliminarCompra(movemp);
            System.out.println("Compra eliminaaa!!");
        } catch (PersistenciaException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        try {
            MovimientoEmpacado movimientoemp = persistencia.obtenCompra(movemp);
            
            if (movimientoemp == null) {
                System.out.println("La compra no se encontro!!!");
            }
            
            System.out.println(movimientoemp);
            
        } catch (PersistenciaException ex) {
            Logger.getLogger(PruebaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        */
        
    }
}
