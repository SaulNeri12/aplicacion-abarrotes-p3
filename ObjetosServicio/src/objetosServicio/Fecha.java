package objetosServicio;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Clase con métodos para diversas funciones referentes a fechas.
 *
 * @author Enrique Rodríguez Angulo
 */
public class Fecha extends GregorianCalendar {

    /**
     * Método constructor, construye un objeto tipo fecha y lo inicializa a la
     * fecha del sistema. También establece los atributos hora, minuto, segundo
     * y milésima de segundo de la clase padre a cero.
     *
     */
    public Fecha() {
        super.set(GregorianCalendar.HOUR, 0);
        super.set(GregorianCalendar.MINUTE, 0);
        super.set(GregorianCalendar.SECOND, 0);
        super.set(GregorianCalendar.MILLISECOND, 0);

    }

    /**
     * Método que establece día(s), mes(es) y año(s), según los parámetros
     * dados. También establece los atributos hora, minuto, segundo y milésima
     * de segundo de la clase padre a cero.
     *
     * @param dia Día(s).
     * @param mes Mes(es).
     * @param anio Año(s).
     */
    public Fecha(int dia, int mes, int anio) {

        super.set(GregorianCalendar.DAY_OF_MONTH, dia);
        super.set(GregorianCalendar.MONTH, mes - 1);
        super.set(GregorianCalendar.YEAR, anio);
        super.set(GregorianCalendar.HOUR, 0);
        super.set(GregorianCalendar.MINUTE, 0);
        super.set(GregorianCalendar.SECOND, 0);
        super.set(GregorianCalendar.MILLISECOND, 0);
    }

    /**
     * Constructor de tipo espejo, establece a un nuevo objeto de tipo fecha,
     * los mismos valores de la fecha ingresada como parámetro.
     *
     * @param fecha Fecha que será copiada.
     */
    public Fecha(Fecha fecha) {

        super(fecha.get(YEAR), fecha.get(MONTH), fecha.get(DAY_OF_MONTH), 0, 0, 0);
        super.set(MILLISECOND, 0);

    }

    /**
     * Método que construye un objeto de tipo fecha a partir de una cadena dada.
     *
     * @param s Parametro de tipo String.
     */
    public Fecha(String s) {
        super(Integer.parseInt(s.substring(6, 10)), (Integer.parseInt(s.substring(3, 5)) - 1), Integer.parseInt(s.substring(0, 2)));

    }

    /**
     * Método que obtiene el día.
     *
     * @return Día(s).
     */
    public int getDia() {
        return super.get(DAY_OF_MONTH);
    }

    /**
     * Método que obtiene mes(es).
     *
     * @return Mes(es)
     */
    public int getMes() {
        return super.get(MONTH) + 1;
    }

    /**
     * Método que obtiene año(s).
     *
     * @return Año(s).
     */
    public int getAnio() {
        return super.get(YEAR);
    }

    /**
     * Método que establece día(s) al del parámetro dado.
     *
     * @param dia Día(s).
     */
    public void setDia(int dia) {
        super.set(GregorianCalendar.DAY_OF_MONTH, dia);
    }

    /**
     * Método que establece Mes(es) al del parámetro dado.
     *
     * @param mes Mes(es).
     */
    public void setMes(int mes) {
        super.set(GregorianCalendar.MONTH, mes - 1);
    }

    /**
     * Método que establece año(s) al del parámetro dado.
     *
     * @param anio Año(s).
     */
    public void setAnio(int anio) {
        super.set(GregorianCalendar.YEAR, anio);
    }

    /**
     * Método que establece los valores de día, mes y año al de los parámetros
     * dados.
     *
     * @param dia Día(s).
     * @param mes Mes(es).
     * @param anio Año(s).
     */
    public void setFecha(int dia, int mes, int anio) {
        super.set(GregorianCalendar.DAY_OF_MONTH, dia);
        super.set(GregorianCalendar.MONTH, mes - 1);
        super.set(GregorianCalendar.YEAR, anio);
        super.set(GregorianCalendar.HOUR, 0);
        super.set(GregorianCalendar.MINUTE, 0);
        super.set(GregorianCalendar.SECOND, 0);
        super.set(GregorianCalendar.MILLISECOND, 0);
    }

    /**
     * Método que suma días, meses y años a una fecha.
     *
     * @param numDias Los días.
     * @param numMeses Los meses.
     * @param numAnios Los años.
     *
     * @return Fecha modificada con dias, meses y años.
     */
    public Fecha vencimiento(int numDias, int numMeses, int numAnios) {
        Fecha fecha = new Fecha(this);
        fecha.add(DATE, numDias);
        fecha.add(MONTH, numMeses);
        fecha.add(YEAR, numAnios);
        return fecha;
    }

    /**
     * Método que suma días y meses a una fecha.
     *
     * @param numDias Cantidad de días.
     * @param numMeses Cantidad de meses.
     * @return Fecha modificada sumando días y meses.
     */
    public Fecha vencimiento(int numDias, int numMeses) {
        Fecha fecha = new Fecha(this);
        fecha.add(DATE, numDias);
        fecha.add(MONTH, numMeses);
        return fecha;

    }

    /**
     * Método que suma días a una fecha.
     *
     * @param numDias Cantidad de días.
     * @return Fecha modificada sumando dias.
     */
    public Fecha vencimiento(int numDias) {
        Fecha fecha = new Fecha(this);
        fecha.add(DATE, numDias);
        return fecha;

    }

    /**
     * Método que calcula los días transcurridos desde la fecha que se ingresa
     * como parámetro hasta la dada por la clase.
     *
     * @param desde Fecha de comienzo.
     * @return Regresa la cantidad de dias transcurridos
     */
    public int lapso(Fecha desde) {

        return (int) (((((this.getTimeInMillis() - desde.getTimeInMillis()) / 1000) / 60) / 60) / 24);
    }

    //Sobre escritura de un método.
    /**
     * Método que regresa una fecha con un formato establecido: dd/mm/aaaa.
     *
     * @return Fecha con el formato: dd/mm/aaaa.
     */
    @Override
    public String toString() {
        return this.getDia() + "/" + this.getMes() + "/" + this.getAnio();

    }

}
