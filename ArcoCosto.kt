// Estudiantes: Jennifer Gamez 16-10396
//              Amaranta Villegas 16-11247
// laboratorio Semana 2
// nombre del archivo: ArcoCosto.kt

package ve.usb.libGrafo

public class ArcoCosto(val x: Int, val y: Int, val costo: Double) : Arco(x, y) {

    /*
    DESCRIPCIÓN: Función encargada de retornar el peso o costo del lado (x,y)
    
    PRECONDICIÓN: (peso es un dato tipo Double)
    
    POSTCONDICIÓN: (peso() retorna una variable tipo Double)
    
    TIEMPO: O(1)
    */
    fun costo() : Double {
        return this.costo

    }

    /*
    DESCRIPCIÓN: Función que convierte el arco en un String

    PRECONDICIÓN: ((x,y,costo) == tripleta)

    POSTCONDICIÓN: (toString() retorna un dato tipo String)
    
    TIEMPO: O(1)
    */
    override fun toString() : String {
        return "(${this.x}-> ${this.y}), costo:${this.costo}"

    }
}