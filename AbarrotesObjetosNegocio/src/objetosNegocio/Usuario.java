
package objetosNegocio;

/**
 * Clase la cual contiene la informacion necesaria para un usuario del sistema 
 * de abarrotes
 * @author Saul Neri
 */
public class Usuario {
    
    public static int REPARTIDOR    = 0;
    public static int ADMINISTRADOR = 1;
    
    private String id;
    private String nombre;
    private String telefono;
    private String email;
    private String rol;
    private String contrasena;
    
    /**
     * Crea una instancia de un usuario a partir de su ID
     * @param id ID del usuario
     */
    public Usuario(String id) {
        this.id = id;
    }
    
    /**
     * Crea una instancia de un usuario a partir de su ID y nombre
     * @param id ID del usuario
     * @param nombre Nombre de usuario
     */
    public Usuario(String id, String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Crea una instancia de un usuario preparado con todos sus datos exceptuando
     * la contrasena, la cual se inicializa por default como "user".
     * @param id ID del usuario
     * @param nombre Nombre del usuario
     * @param telefono Numero telefonico del usuario
     * @param email Correo electronico del usuario
     * @param rol Rol del usuario (administrador o repartidor)
     */
    public Usuario(String id, String nombre, String telefono, String email, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.rol = rol;
        this.contrasena = "user"; // por defecto
    }
    
    /**
     * Devuelve el identificador del usuario
     * @return ID
     */
    public String getId() {
        return this.id;
    }
    
    /**
     * Devuelve el nombre del usuario
     * @return nombre usuario
     */
    public String getNombre() {
        return this.nombre;
    }
    
    /**
     * Devuelve el numero telefonico del usuario
     * @return Telefono
     */
    public String getTelefono() {
        return this.telefono;
    }
    
    /**
     * Devuelve el email (correo electronico) del usuario
     * @return Correo electronico
     */
    public String getEmail() {
        return this.email;
    }
    
    /**
     * Devuelve el rol del usuario en el sistema
     * @return Rol (administrador o repartidor)
     */
    public String getRolUsuario() {
        return this.rol;
    }
    
    /**
     * Devuelve la contrasena del usuario
     * @return Contrasena
     */
    public String getContrasena() {
        return this.contrasena;
    }
    
    /**
     * Asigna el identificador del usuario
     * @param id ID de usuario
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Asigna el nombre de usuario
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Asigna el numero telefonico del usuario
     * @param telefono Numero de telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    /**
     * Asigna el correo electronico del usuario
     * @param email Correo electronico
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Asigna el rol del usuaro (administrador o repartidor)
     * @param rol Rol del usuario
     */
    public void setRolUsuario(String rol) {
        this.rol = rol;
    }
    
    /**
     * Asigna la contrasena del usuario
     * @param contrasena Contrasena del usuario
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    @Override
    public String toString() {
        String str = String.format(
                "Usuario {id: %s, nombre: %s, telefono: %s, email: %s, contrasena: %s, rol: %s}",
                id, nombre, telefono, email, contrasena, rol
        );
        
        return str;
    }
}
