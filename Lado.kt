// Estudiantes: Jennifer Gamez 16-10396
//              Amaranta Villegas 16-11247
// laboratorio Semana 1
// nombre del archivo: Lado.kt

package ve.usb.libGrafo

abstract class Lado(val a: Int, val b: Int) {


    /*
    DESCRIPCIÓN: Función que retorna cualquier vértice pertenciente al lado, por defecto se retorna
    el primer vertice ingresado

    PRECONDICIÓN: (a y b son del tipo Entero)

    POSTCONDICIÓN: (cualquieraDeLosVertices() retorna una variable tipo Entero)

    TIEMPO: O(1)
    */
    fun cualquieraDeLosVertices() : Int {
        return this.a
    }
    /*
    DESCRIPCION: Función que retorna el vértice contrario, pertenciente al lado, del que se encuentra 
    en el argumento.

    PRECONDICION: (w == a || w == b)

    POSTCONDICION:  (vertice == a || vertice == b )

    TIEMPO: O(1)
    */
    @Throws(RuntimeException:: class)
    fun elOtroVertice(w: Int) : Int {
        return when {
            (w == this.a) -> this.b
            (w == this.b) -> this.a
            else -> throw RuntimeException("Error: Vertice no pertenece al lado")
        }

    }
}
