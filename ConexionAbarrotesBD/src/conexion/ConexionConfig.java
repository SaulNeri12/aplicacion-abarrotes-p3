/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

/**
 * Esta clase funciona como ajustes de configuracion para la conexion entre el
 * "AbarrotesDAO" y la base de datos de abarrotes.
 * @author Saul Neri
 */
public class ConexionConfig {
    private String nombreBaseDatos      = "abarrotes";
    private String nombreUsuario        = "root";
    private String puertoBaseDatos      = "3306";
    private String contrasenaBaseDatos  = "";
    
    /**
     * Crea una configuracion de conexion con los datos por defecto
     */
    public ConexionConfig() {
        
    }
    
    /**
     * Crea una conexion a la base de datos con los datos especificados.
     * @param baseDatosNombre Nombre de la base de datos (default es "abarrotes")
     * @param puerto Puerto de la base de datos (default es 9999)
     * @param usuario Usuario de la base de datos MySQL (default es "root")
     * @param contrasena Contrasena para acceder a dicha base de datos (default es "" (cadena vacia))
     */
    public ConexionConfig(String baseDatosNombre, String puerto, String usuario, String contrasena) {
        this.nombreBaseDatos = baseDatosNombre;
        this.puertoBaseDatos = puerto;
        this.nombreUsuario = usuario;
        this.contrasenaBaseDatos = contrasena;
    }
    
    /**
     * Devuelve el nombre de la base de datos de la configuracion de conexion.
     * @return Nombre de base de datos
     */
    public String getNombreBaseDatos() {
        return this.nombreBaseDatos;
    }
    
    /**
     * Devuelve el puerto necesario para la conexion a la base de datos.
     * @return Puerto de la base de datos
     */
    public String getPuertoConexion() {
        return this.puertoBaseDatos;
    }
    
    /**
     * Devuelve el nombre de usuario de la configuracion de conexion.
     * @return Nombre usuario
     */
    public String getNombreUsuario() {
        return this.nombreUsuario;
    }
    
    /**
     * Devuelve la contrasena de la configuracion de conexion.
     * @return 
     */
    public String getContrasena() {
        return this.contrasenaBaseDatos;
    }
    
}
