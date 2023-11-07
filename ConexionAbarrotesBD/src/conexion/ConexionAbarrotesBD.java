
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
    private ConexionConfig configuracion;
        
    // NOTE: NO CAMBIAR
    private final String URL_MYSQL      = "jdbc:mysql://localhost:%s/";  
    private final String DRIVER_MYSQL   = "com.mysql.cj.jdbc.Driver";
    
    private Connection conexion;
    
    /**
     * Crea una conexion a la base de datos con los datos por default.
     */
    public ConexionAbarrotesBD() {
        this.configuracion = new ConexionConfig();
    }
    
    /**
     * Crea una nueva conexion a la base de datos abarrotes con la configuracion
     * de conexion dada.
     * @param configuracion Configuracion de conexion
     */
    public ConexionAbarrotesBD(ConexionConfig configuracion) {
        this.configuracion = configuracion;
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
                    String.format(URL_MYSQL, this.configuracion.getPuertoConexion()), 
                    this.configuracion.getNombreUsuario(), 
                    this.configuracion.getContrasena()
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
