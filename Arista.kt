// Estudiantes: Jennifer Gamez 16-10396
//              Amaranta Villegas 16-11247
// laboratorio Semana 2
// nombre del archivo: Arista.kt
package ve.usb.libGrafo

public open class Arista(val v: Int, val u: Int) : Lado(v, u) {

    /*
    DESCRIPCIÓN: Función que convierte el arista en un String

    PRECONDICIÓN: ((v,u) == par)

    POSTCONDICIÓN: (toString() retorna un dato tipo String)
    
    TIEMPO: O(1)
    */
    override fun toString() : String {
        return "${this.u} <-> ${this.v}"
    }
}
