
package dao;

import conexion.ConexionAbarrotesBD;
import excepciones.DAOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import objetosNegocio.Producto;
import objetosNegocio.Usuario;
import plantillas.ConsultasAbarrotes;
import plantillas.EliminacionesAbarrotes;
import plantillas.ErrorMySQL;
import plantillas.InsercionesAbarrotes;
import plantillas.ModificacionesAbarrotes;

/**
 * Permite el registro e inicio de sesion del usuario en la aplicacion, ademas de
 * tambien manejar el registro de usuarios para realizar operaciones como insercion,
 * consulta, modificacion y eliminacion de usuarios.
 * @author Saul Neri
 */
public class Usuarios {
    private ConexionAbarrotesBD conexionBD;
    // donde se alojaran los productos que se vayan guardando con la PAGINACION
    private List<Usuario> usuarios;
    
    private int desplazamiento = 0;
    private int limiteListaUsuarios = 20;
    
    /** 
     * Crea una instancia del manejador de usuarios
     */
    public Usuarios() {
        this.conexionBD = new ConexionAbarrotesBD();
        usuarios = new ArrayList<>();
    }
    
    /**
     * Crea una instancia del manejador de usuarios e inicios de sesion.
     * @param conexion Conexion a la base de datos de abarrotes
     */
    public Usuarios(ConexionAbarrotesBD conexion) {
        this.conexionBD = conexion;
        usuarios = new ArrayList<>();
    }
    
    /**
     * Regresa el usuario dado si se encuentra registrado, en caso contrario
     * devuelve null
     * @param usuario Objeto usuario
     * @return Usuario en el registro
     * @throws DAOException Si ocurre un error en la busqueda
     */
    public Usuario obten(Usuario usuario) throws DAOException {
        Usuario usuarioEncontrado = null;
        
        try {
            
            PreparedStatement stmt = this.conexionBD.getConexionMySQL().prepareStatement(
                    ConsultasAbarrotes.USUARIOS_POR_ID, 
                    ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE
            );
            
            stmt.setInt(1, usuario.getId());
            
            ResultSet rs = stmt.executeQuery();
            
            if (!rs.next()) {
            // si no existe el usuario...
                
                rs.close();
                stmt.close();
                
                return null;
            }
            
            // indica que se requiere el primero encontrado
            rs.first();
            
            // obtiene los datos del primer producto encontrado encontrado
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            String telefono = rs.getString("telefono");
            String email = rs.getString("email");
            String rolUsuario = rs.getString("rol");
            String contrasena = rs.getString("contrasena");
            
            usuarioEncontrado = new Usuario(
                    id,
                    nombre, 
                    telefono,
                    email,
                    rolUsuario
            );
            
            usuarioEncontrado.setContrasena(contrasena);
            
            rs.close();
            stmt.close();
            
            return usuarioEncontrado;
            
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage());
            //throw new DAOException("Ocurrio un error de busqueda");    
            //System.out.println("#" + ex);
        }
    }
    
    /**
     * Registra a un usuario en la base de datos
     * @param usuario Usuario a agregar
     * @throws DAOException Si no se pudo agregar a la base de datos o debido
     * a un error interno
     */
    public void agregar(Usuario usuario) throws DAOException {
        PreparedStatement stmt;        

        try {
            stmt = this.conexionBD.getConexionMySQL().prepareStatement(InsercionesAbarrotes.REGISTRAR_USUARIO);
            
            stmt.setInt(1, usuario.getId());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getTelefono());
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getContrasena());
            stmt.setString(6, usuario.getRolUsuario());
            
            int numAnadidos = stmt.executeUpdate();
            
            if (numAnadidos <= 0) {
                throw new DAOException("No se pudo registrar al usuario");
            }
            
            //System.out.println("# anadidos: "+numAnadidos);

            stmt.close();

        } catch (SQLException ex) {
            //System.out.println(ex.getErrorCode());
            if (ex.getErrorCode() == ErrorMySQL.DUPLICATE) {
                throw new DAOException("El usuario ya esta registrado");
            } 
            else if (ex.getErrorCode() == ErrorMySQL.TRUNCATE_DATA) {
                throw new DAOException("No se pudo registrar al usuario debido a que los datos son errones");
            }
            
            throw new DAOException(ex.getMessage());
        }
    }
    
    /**
     * Actualiza la informacion del usuario dado.
     * @param usuario Usuario
     * @throws DAOException Si no se puede actualizar el usuario o debido a un 
     * error interno
     */
    public void actualiza(Usuario usuario) throws DAOException {
        PreparedStatement stmt;        

        try {
            stmt = this.conexionBD.getConexionMySQL().prepareStatement(ModificacionesAbarrotes.ACTUALIZAR_USUARIO);
            
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getTelefono());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getContrasena());
            stmt.setString(5, usuario.getRolUsuario());
            // WHERE id = ?...
            stmt.setInt(6, usuario.getId());
            
            int numModificados = stmt.executeUpdate();

            if (numModificados <= 0) {
                throw new DAOException("No se pudo actualizar los datos del usuario ya que no esta registrado");
            }
            
            stmt.close();

        } catch (SQLException ex) {
            if (ex.getErrorCode() == ErrorMySQL.TRUNCATE_DATA) {
                throw new DAOException("No se pudo actualizar al usuario debido a que los datos son errones");
            }
            
            throw new DAOException("No se pudo actualizar la informacion de un usuario debido a un error, intentelo mas tarde...");
        }
    }    
    
    /**
     * Elimina a un usuario de la base de datos de Abarrotes
     * @param usuario Usuario a eliminar
     * @throws DAOException Si no se puede eliminar al usuario o debido a un 
     * error interno
     */
    public void elimina(Usuario usuario) throws DAOException {
        PreparedStatement stmt;        

        try {
            stmt = this.conexionBD.getConexionMySQL().prepareStatement(EliminacionesAbarrotes.ELIMINAR_USUARIO);
            
            // WHERE id = ? ...
            stmt.setInt(1, usuario.getId());
            
            int numEliminados = stmt.executeUpdate();

            //System.out.println(numEliminados);
            
            if (numEliminados <= 0) {
                throw new DAOException("No se pudo eliminar al usuario debido a que no esta registrado");
            }
            
            stmt.close();

        } catch (SQLException ex) {
            throw new DAOException("No se pudo eliminar al usuario debido a un error, intentelo mas tarde...");
        }
    }
    
    /**
     * Consulta a la base de datos una lista de usuarios de 20 en 20, cada vez
     * que se llama al metodo se anaden 20 usuarios mas a la lista visualizacion
     * de usuarios.
     * @throws excepciones.DAOException Si ocurre un error en la consulta
     */
    public void consultaUsuarios() throws DAOException {
        Usuario usuarioEncontrado = null;
        
        try {
            
            PreparedStatement stmt = this.conexionBD.getConexionMySQL().prepareStatement(
                    ConsultasAbarrotes.USUARIOS_TODOS, 
                    ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE
            );
            
            stmt.setInt(1, this.limiteListaUsuarios);
            stmt.setInt(2, this.desplazamiento);
            
            ResultSet rs = stmt.executeQuery();
            
            if (!rs.next()) {
                // si no existe el usuario...
                rs.close();
                stmt.close();
                
                return ;
            }
            
            while (rs.next()) {

                // obtiene los datos del primer producto encontrado encontrado
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                String contrasena = rs.getString("contrasena");
                String rolUsuario = rs.getString("rol");

                usuarioEncontrado = new Usuario(
                        id,
                        nombre, 
                        telefono,
                        email,
                        rolUsuario
                );
                
                usuarioEncontrado.setContrasena(contrasena);

                usuarios.add(usuarioEncontrado);
            }
            
            rs.close();
            stmt.close();
            
            desplazamiento += this.limiteListaUsuarios;
                    
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage());
        }
    }
    
    /**
     * Devuelve la lista de visualizacion de usuarios
     * @return Lista de usuarios
     */
    public List<Usuario> lista() {
        return this.usuarios;
    }
    
    /**
     * Vacia la lista visualizacion de usuarios 
     */
    public void limpiarLista() {
        this.usuarios.clear();
    }
}
