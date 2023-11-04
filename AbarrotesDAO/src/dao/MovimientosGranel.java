package dao;

import excepciones.DAOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import objetosNegocio.MovimientoGranel;
import objetosNegocio.Producto;
import objetosServicio.Periodo;

/**
 *
 * @author Equipo 07
 */
public class MovimientosGranel {
    private List<MovimientoGranel> movimientosGranel;
    
    /** 
     * Constructor que crea una instancia para almacenar los movimientos de producto a granel de la abarrotera.
     */
    public MovimientosGranel(){
        this.movimientosGranel = new ArrayList();
    }
    
    /** 
     * Metodo que regresa la lista de movimiento de producto granel que se da por el parametro, 
     * siempre y cuando su clave sea la misma, regresara null si no existe.
     * 
     * @param movimientoGranel movimiento granel
     * @return Movimiento a granel
     */
    public MovimientoGranel obten(MovimientoGranel movimientoGranel){
        MovimientoGranel compra1 = null;
        Iterator<MovimientoGranel> it = movimientosGranel.iterator();
        int existencia=0;
        while (it.hasNext()) {
            compra1 = it.next();
            if (compra1.equals(movimientoGranel)) {
                existencia++;
            }
        }
        if (existencia>=1) {
            return compra1;
        } else {
            return null;
        }
//        if (movimientosGranel.contains(movimientoGranel)){
//         return movimientoGranel;   
//        }else{
//            return null;
//        }
    }
    
    /** 
     * Agrega el movimiento dado por el parametro a la lista de movimientos.
     * 
     * @param movimientoGranel movimiento granel
     */
    public void agrega(MovimientoGranel movimientoGranel){
        movimientosGranel.add(movimientoGranel);
    }
    
    /** 
     * Remplaza el movimiento que coincida (con clave) con el producto dado en el parametro,
     * a menos que no exista.
     * 
     * @param movimientoGranel movimiento granle
     * @throws excepciones.DAOException error
     */
    public void actualiza(MovimientoGranel movimientoGranel) throws DAOException{
        boolean check = false;
        for (int i = 0; i < movimientosGranel.size(); i++) {
            if (movimientoGranel.equals(movimientosGranel.get(i))) {
                movimientosGranel.set(i, movimientoGranel);
                check= true;
                break;
            }
            
            if (check==false) {
                throw new DAOException("**El movimiento del producto granel a actualizar no existe**");
            }
        }
    }
    
    /** 
     * Metodo que elimina el movimiento de producto granel de la lista (si existe), 
     * por el movimiento de producto granel dado en el parametro, deben tener la misma clave.
     * 
     * @param movimientoGranel mocimiento granel
     * @throws excepciones.DAOException error
     */
    public void elimina(MovimientoGranel movimientoGranel) throws DAOException{
        if (!movimientosGranel.remove(movimientoGranel)) {
            throw new DAOException("El movimiento de producto granel no se pudo eliminar");
        }
    }
    
    /** 
     * Metodo el cual muestra la lista de movimientos de productos granel.
     * 
     * @return lista de movimiento de producto granel
     */ 
    public List<MovimientoGranel> lista(){
        return movimientosGranel;
    }
    
    /** 
     * Metodo el cual muestra la lista de movimientos de productos granel en un periodo dado.
     * @param periodo perioso
     * @return regresa una lista dentro de un periodo
     */
    public List<MovimientoGranel> lista(Periodo periodo){
        List<MovimientoGranel>movimientosPeriodo = new ArrayList();
        for (MovimientoGranel movimientoGranel : movimientosGranel) {
            if (periodo.contiene(movimientoGranel.getFecha())) {
                movimientosPeriodo.add(movimientoGranel);
            }
        }
        return movimientosPeriodo;
    }
}
