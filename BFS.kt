// Estudiantes: Jennifer Gamez 16-10396
//              Amaranta Villegas 16-11247
// laboratorio Semana 2
// nombre del archivo: BFS.kt

package ve.usb.libGrafo
import java.util.*
import java.lang.Integer.MAX_VALUE


/* 
   Implementación del algoritmo BFS. 
   Con la creación de la instancia, se ejecuta el algoritmo BFS
   desde el vértice s
*/
public class BFS(val g: Grafo, val s: Int) {

    private val n : Int 
    private val colors: Array<Color>
    private val d : Array<Int>
    private var pred : Array<Int?>
    private var Q : PriorityQueue<Int>
    private var compareNodes = Comparator<Int>{
        a,b ->
        when {
            (a==b) -> 0
            (a < b) -> -1
            else -> 1
        }
    }

    /*
    PRECONDICION: V > 0
    
    POSTCONDICION:     (Q.isEmpty() && (Para todo i : colores[i] == NEGRO))
    
    TIEMPO: O(|V|+|E|)
    */

    init {

    // Se ejecuta BFS
    this.n = g.obtenerNumeroDeVertices()
    this.colors = Array<Color>(n){Color.BLANCO}
    this.d = Array<Int>(n){Integer.MAX_VALUE}
    this.pred = Array<Int?>(n){null}
    d[s] = 0
    colors[s] = Color.GRIS
    Q = PriorityQueue<Int>(this.compareNodes) 
    Q.add(s)
    while (Q.size > 0){
        var u : Int = Q.poll()
        g.adyacentes(u).forEach{ v ->
            if (this.colors[v.elOtroVertice(u)] == Color.BLANCO){
                this.colors[v.elOtroVertice(u)] = Color.GRIS
                this.d[v.elOtroVertice(u)] = this.d[u] + 1
                this.pred[v.elOtroVertice(u)] = u
                Q.add(v.elOtroVertice(u))
                }
            }
            this.colors[u] = Color.NEGRO
        }
    }

    /*
     Retorna el predecesor de un vértice v. Si el vértice no tiene predecesor
     se retorna null. En caso de que el vértice v no exista en el grafo se lanza
     una RuntimeException

     precondicion (v in 0 until n-1)
     postcondicion: obtenerPredecesor = this.pred[v]
     
     tiempo: |O(1)|
     */

    fun obtenerPredecesor(v: Int) : Int? { 
        if ((v < 0 ) or (v >= this.n)){
            throw RuntimeException("Error: El vertice no pertenece al Grafo")

        }
        return this.pred[v]
     }

    /*
    Retorna la distancia, del camino obtenido por BFS, desde el vértice inicial s 
    hasta el un vértice v. En caso de que el vétice v no sea alcanzable desde s,
    entonces se retorna -1.
    En caso de que el vértice v no exista en el grafo se lanza una RuntimeException. 

    precondicion (v in 0 until n-1)
    postcondicion: obtenerDistancia = this.d[v] || obtenerDistancia = -1
    tiempo: O(|V|)
     */
    fun obtenerDistancia(v: Int) : Int {  
        
        if ((v < 0 ) or (v >= this.n)){
            throw RuntimeException("Error: El vertice no pertenece al Grafo")
        }

        var resultado = this.d[v]

        if (this.hayCaminoHasta(v) == false){
            return -1
        }else {
            return resultado
        }
    }
    

    /*
    Indica si hay camino desde el vértice inicial s hasta el vértice v.
    Si el camino existe retorna true, de lo contrario falso
    En caso de que el vértice v no exista en el grafo se lanza una RuntimeException 

    precondicion (v in 0 until n-1)

    postcondicion: hayCaminoHasta(v) == true or hayCaminoHasta(v) == false

    tiempo O(|V|)
    */ 

    fun hayCaminoHasta(v: Int) : Boolean { 
        if ((v < 0) or (v >= this.n)){
            throw RuntimeException("Error: Este vertice no pertenece al grafo")
        }

        if (this.colors[v] != Color.BLANCO){
            return true
        } else {
            return false
        }
     }

    /*
    Retorna el camino con menos lados, obtenido por BFS, desde el vértice inicial s 
    hasta el un vértice v. El camino es representado como un objeto iterable con
    los vértices del camino desde s hasta v.
    En caso de que el vétice v no sea alcanzable desde s, entonces se lanza una RuntimeException.
    En caso de que el vértice v no exista en el grafo se lanza una RuntimeException.

    precondicion (v in 0 until n-1)

    postcondicion: s in pred[v]  
     
    tiempo O(|V|)
     */ 
    fun caminoHasta(v: Int) : Iterable<Int>  { 
        if ((v < 0) or (v >= this.n)){
            throw RuntimeException("Error: Este vertice no pertenece al grafo")
        }
        
        //si no hay un camino hasta v , entonces devuelve una lista vacia
        if (this.hayCaminoHasta(v) == false){
            throw RuntimeException("Error: no existe un camino hasta v ")
        }

        var camino : MutableList<Int> = mutableListOf(v)
        var u : Int? = this.pred[v]
        while (true){
            if (u == null){
                camino.reverse()
                return camino
            }
            camino.add(u)
            u = this.pred[u]
        }
     }

    /*
    Imprime por la salida estándar el breadth-first tree

    
    precondicion: BFS
    Postcondicion: imprime un arbols de niveles

    tiempo: O(|v|**2)
    */
    fun mostrarArbolBFS() {
        var level = -1

        for (i in 0..n-1){
            if (level < this.d[i]){
                level = this.d[i]
            }
        }
        for (i in 0..level){
            var salidaBFS = mutableListOf<Int>()
            for (k in 0..n-1){
                if (this.d[k] == i){
                    salidaBFS.add(k)
                }
            }
            println("Nivel [$i] --> [$salidaBFS] "  )
        
        }
    }
}
