// Estudiantes: Jennifer Gamez 16-10396
//              Amaranta Villegas 16-11247
// laboratorio Semana 2
// nombre del archivo: AristaCosto.kt
package ve.usb.libGrafo

public class AristaCosto(val x: Int,
			val y: Int,
			val costo: Double) : Comparable<AristaCosto>, Arista(x, y) {

     /*
     DESCRIPCIÓN: Función encargada de retornar el peso o costo del lado (x,y)
    
     PRECONDICIÓN: (peso es un dato tipo Double)
    
     POSTCONDICIÓN: (peso() retorna una variable tipo Double)
    
     TIEMPO: O(1)
     */
    fun costo() : Double {
         return this.costo
    }

    // Representación en string de la arista
        /*
    DESCRIPCIÓN: Función que convierte el arista en un String

    PRECONDICIÓN: ((v,u) == par)

    POSTCONDICIÓN: (toString() retorna un dato tipo String)
    
    TIEMPO: O(1)
    */
    override fun toString() : String {
         return "(${this.x} <-> ${this.y}), costo:${this.costo}"
    }
    /*
    DESCRIPCIÓN: Función que compara el peso de la arista con otro peso, en el caso
    de que el peso sea mayor al ingresdado se retorna 1, en caso contrario se retorna -1
    y si son iguales retorna 0
    ingresado en el argumento
    
    PRECONDICIÓN: (other es un dato tipo Double)
    
    POSTCONDICIÓN: (-1 <= comparador <=1)
    
    TIEMPO: O(1)
    */
     override fun compareTo(other: AristaCosto): Int {
          return when {
               this.costo > other.costo -> 1
               this.costo < other.costo -> -1
               else -> 0
          }
     }
}