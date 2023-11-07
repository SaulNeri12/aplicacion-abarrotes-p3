
package conexion;

import excepciones.ErrorConexionException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Esta clase contiene la conexion de la base de datos necesaria para la aplicacion
 * "AplicacionAbarrotes", permite conectarse y obtener permisos para realizar consultas 
 * a la misma.
 * @author Saul Neri
 */
public class ConexionAbarrotesBD {
    private String NOMBRE_BASE_DATOS  = "abarrotes";
    private String PUERTO_BASE_DATOS  = "9999"; 
    private String URL_MYSQL          = "jdbc:mysql://localhost:" + PUERTO_BASE_DATOS + "/";      
    private String USUARIO            = "root";
    private String CONTRASENA         = "";
    // NOTE: NO CAMBIAR
    private final String DRIVER_MYSQL   = "com.mysql.cj.jdbc.Driver";
    
    private Connection conexion;
    
    /**
     * Crea una conexion a la base de datos con los datos por default.
     */
    public ConexionAbarrotesBD() {
        
    }
    
    /**
     * Crea una conexion a la base de datos con los datos especificados.
     * @param baseDatosNombre Nombre de la base de datos (default es "abarrotes")
     * @param puerto Puerto de la base de datos (default es 9999)
     * @param usuario Usuario de la base de datos MySQL (default es "root")
     * @param contrasena Contrasena para acceder a dicha base de datos (default es "" (cadena vacia))
     */
    public ConexionAbarrotesBD(String baseDatosNombre, String puerto, String usuario, String contrasena) {
        this.NOMBRE_BASE_DATOS = baseDatosNombre;
        this.PUERTO_BASE_DATOS = puerto;
        this.USUARIO = usuario;
        this.CONTRASENA = contrasena;
    }
  
    /**
     * Devuelve la conexion interna de MySQL de la base de datos, utilizada para 
     * trabajar con ella en consultas a la misma.
     * @return La conexion a MySQL
     */
    public Connection getConexionMySQL() {
        return this.conexion;
    }
    
    /**
     * Se conecta a la base de datos Abarrotes.
     * @throws ErrorConexionException si no se puede conectar a la base de datos
     * Abarrotes
     */
    public void conectar() throws ErrorConexionException {
        try {
            try {
                Class.forName(DRIVER_MYSQL);
            } catch (ClassNotFoundException ex) {
                throw new ErrorConexionException("No se pudo conectar a la base de datos por error un desonocido...");
            }
            conexion = DriverManager.getConnection(
                    URL_MYSQL + NOMBRE_BASE_DATOS, 
                    USUARIO, 
                    CONTRASENA
            );
        } catch (SQLException ex) {
            throw new ErrorConexionException("No se pudo conectar a la base de datos");
        }
    }
    
    /**
     * Se desconecta y termina la conexion con la base de datoa Abarrotes.
     * @throws ErrorConexionException Si no se puede terminar la conexion con la base de 
     * datos Abarrotes.
     */
    public void desconectar() throws ErrorConexionException {
        try {
            conexion.close();
        } catch (SQLException ex) {
            throw new ErrorConexionException("No se pudo terminar la conexion con la base de datos");
        }
    }
}
