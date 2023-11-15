
package plantillas;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Contiene las validaciones de expresiones regulares necesarias para el sistema de 
 * abarrotes
 * @author Nomar Limon
 */
public class ValidacionesAbarrotes {
    
    public static final String REGEX_ID_USUARIO = "^(100000|[1-9]\\d{5})$";
    public static final String REGEX_NOMBRE_USUARIO = "^[a-zA-Z]+(?:\\s[a-zA-Z]+)*$";
    public static final String REGEX_EMAIL_USUARIO = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    public static final String REGEX_TELEFONO_USUARIO = "^(\\d{10}|\\d{3}-\\d{3}-\\d{3})$";
    public static final String REGEX_CONTRASENA_USUARIO = "^(?=.*[a-zA-Z0-9?])[a-zA-Z0-9?]{8,30}$";
    public static final String REGEX_ROL_USUARIO = "^(?i)(administrador|repartidor)$";
    
    /**
     * Devuelve true si el ID dado cumple con el formato requerido
     * @param id ID del usuario
     * @return true si el ID es correcto
     */
    public static boolean validarIDUsuario(String id) {
        return id.matches(ValidacionesAbarrotes.REGEX_ID_USUARIO);
    }
    
    /**
     * Devuelve true si el nombre del usuario no contiene numeros ni simbolos, ademas
     * de no contener espacios en el inicio y final del nombre
     * @param nombre Nombre del usuario a validar
     * @return true si es valido
     */
    public static boolean validarNombreUsuario(String nombre) {
        return nombre.matches(ValidacionesAbarrotes.REGEX_NOMBRE_USUARIO);
    }
    
    /**
     * Devuelve true si la direccion de email dada es valida
     * @param email Direccion de correo electronico del usuario
     * @return true si es un correo electronico valido
     */
    public static boolean validarEmailUsuario(String email) {
        Pattern pattern = Pattern.compile(ValidacionesAbarrotes.REGEX_EMAIL_USUARIO);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    /**
     * Devuelve true si el numero de telefono es valido, si es de 10 digitos o 
     * si cumple con el formato XXX-XXX-XXX.
     * @param telefono Numero de telefono a validar
     * @return true si el telefono es valido
     */
    public static boolean validarTelefonoUsuario(String telefono) {
        Pattern pattern = Pattern.compile(ValidacionesAbarrotes.REGEX_TELEFONO_USUARIO);
        Matcher matcher = pattern.matcher(telefono);
        return matcher.matches();
    }
    
    /**
     * Devuelve true si la longitud de contrasena es igual o mayor que 8 caracteres y a su vez
     * es menor o igual a 30, ademas de no tener signos a excepcion del signo "?".
     * @param contrasena Contrasena a validar
     * @return true si la contrasena es valida
     */
    public static boolean validarContrasenaUsuario(String contrasena) {
        Pattern pattern = Pattern.compile(ValidacionesAbarrotes.REGEX_CONTRASENA_USUARIO);
        Matcher matcher = pattern.matcher(contrasena);
        return matcher.matches();
    }
    
    /**
     * Verifica si el rol del usuario es de Administrador o Repartidor, devuelve
     * true si es uno de los dos
     * @param rolUsuario Rol a verificar
     * @return true si es un rol valido
     */
    public static boolean validarRolUsuario(String rolUsuario) {
        Pattern pattern = Pattern.compile(ValidacionesAbarrotes.REGEX_ROL_USUARIO);
        Matcher matcher = pattern.matcher(rolUsuario);
        return matcher.matches();
    }
    
    /**
     * Encripta la contrasena dada con el algoritomo SHA-256
     * @param contrasena Contrasena a encriptar
     * @return Contrasena encriptada o null si no se pudo encriptar la contrasena dada
     * @throws NoSuchAlgorithmException Si el algoritmo especificado no existe
     */
    public static String hashearContrasena(String contrasena) throws NoSuchAlgorithmException {   
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(contrasena.getBytes(StandardCharsets.UTF_8));
            return String.format("%064x", new BigInteger(1, hash));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
            return null;
        }
    }
}
