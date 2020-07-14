package cl.zpricing.avant.util;

/**
 * Provee de m�todos para realizar una regresi�n lineal sobre un conjunto de pares de datos
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 14-01-2009 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class Regresion {
    private double[] x;
    private double[] y;
    private int n;          //n�mero de datos
    private double a, b;    //pendiente y ordenada en el origen
    public Regresion(double[] x, double[] y) {
        this.x=x;
        this.y=y;
        n=x.length; //n�mero de datos
    }
    public void lineal(){
        double pxy, sx, sy, sx2, sy2;
        pxy=sx=sy=sx2=sy2=0.0;
        for(int i=0; i<n; i++){
            sx+=x[i];
            sy+=y[i];
            sx2+=x[i]*x[i];
            sy2+=y[i]*y[i];
            pxy+=x[i]*y[i];
        }
        a=(n*pxy-sx*sy)/(n*sx2-sx*sx);
        b=(sy-a*sx)/n;
    }
    public double correlacion(){
//valores medios
        double suma=0.0;
        for(int i=0; i<n; i++){
            suma+=x[i];
        }
        double mediaX=suma/n;

        suma=0.0;
        for(int i=0; i<n; i++){
            suma+=y[i];
        }
        double mediaY=suma/n;
//coeficiente de correlaci�n 
        double pxy, sx2, sy2;
        pxy=sx2=sy2=0.0;
        for(int i=0; i<n; i++){
            pxy+=(x[i]-mediaX)*(y[i]-mediaY);
            sx2+=(x[i]-mediaX)*(x[i]-mediaX);
            sy2+=(y[i]-mediaY)*(y[i]-mediaY);
        }
        return pxy/Math.sqrt(sx2*sy2);
    }
    public double interseccion(){
    	return this.b;
    }
    
    public double pendiente(){
    	return this.a;
    }
    
}