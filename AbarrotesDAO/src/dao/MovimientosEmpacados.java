package dao;

import excepciones.DAOException;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import objetosNegocio.MovimientoEmpacado;
import objetosServicio.Periodo;

/**
 *
 * @author Equipo
 */
public class MovimientosEmpacados {

    private List<MovimientoEmpacado> movimientosEmpacados;
    
    /** 
     * Constructor que crea una instancia para almacenar los movimientos de producto a granel de la abarrotera.
     */
    public MovimientosEmpacados() {
        this.movimientosEmpacados = new ArrayList();
    }

    /** 
     * Metodo que regresa la lista de movimiento de producto granel que se da por el parametro, 
     * siempre y cuando su clave sea la misma, regresara null si no existe.
     * 
     * @param movimientoEmpacado movimiento empacado
     * @return Movimiento empacado.
     * @throws excepciones.PersistenciaException error
     */
    public MovimientoEmpacado obten(MovimientoEmpacado movimientoEmpacado) throws PersistenciaException{
        if (movimientosEmpacados.contains(movimientoEmpacado)){
         return movimientoEmpacado;   
        }else{
            return null;
        }
    }
    
    /**
     * Agrega el movimiento dado por el parametro a la lista de movimientos.
     * 
     * @param movimientoEmpacado movimiento empacado
     */
    public void agrega(MovimientoEmpacado movimientoEmpacado) {
        movimientosEmpacados.add(movimientoEmpacado);

    }
    
    /**
     * Remplaza el movimiento que coincida (con clave) con el producto dado en el parametro,
     * a menos que no exista.
     * 
     * @param movimientoEmpacado movimiento empacado
     * @throws DAOException error
     */
    public void actualiza(MovimientoEmpacado movimientoEmpacado) throws DAOException {
        boolean check = false;
        for (int i = 0; i < movimientosEmpacados.size(); i++) {
            if (movimientoEmpacado.equals(movimientosEmpacados.get(i))) {
                movimientosEmpacados.set(i, movimientoEmpacado);
                check = true;
                break;
            }
            if (check == false) {
                throw new DAOException("**El movimiento a granel no pude ser actualizado**");
            }
        }
    }
    
    /**
     * Metodo que elimina el movimiento de producto empacado de la lista (si existe), 
     * por el movimiento de producto empacado dado en el parametro, deben tener la misma clave.
     * 
     * @param movimientoEmpacado movimiento empacado
     * @throws DAOException error
     */
    public void elimina(MovimientoEmpacado movimientoEmpacado) throws DAOException {
        if (!movimientosEmpacados.remove(movimientoEmpacado)) {
            throw new DAOException("El movimiento de producto empacado no se pudo eliminar");
        }
    }
    
    /**
     * Metodo el cual muestra la lista de movimientos de productos empacados.
     * 
     * @return regresa una lista
     */
    public List<MovimientoEmpacado> lista() {
        return movimientosEmpacados;
    }
    
    /** 
     * Metrodo el que muestra una lista dentro de un periodo
     * @param periodo fechas
     * @return regresa la lista de los periodos
     */
    public List<MovimientoEmpacado> lista(Periodo periodo) {
        List<MovimientoEmpacado> movimientosEmpacadosPeriodo = new ArrayList();
        for (MovimientoEmpacado movimientoEmpacado : movimientosEmpacados) {
            if (periodo.contiene(movimientoEmpacado.getFecha())) {
                movimientosEmpacadosPeriodo.add(movimientoEmpacado);
            }
        }
        return movimientosEmpacadosPeriodo;
    }

}
