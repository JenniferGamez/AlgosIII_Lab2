// Estudiantes: Jennifer Gamez 16-10396
//              Amaranta Villegas 16-11247
// laboratorio Semana 2
// nombre del archivo: BFS.kt

package ve.usb.libGrafo

/* 
   Implementación del algoritmo DFS. 
   Con la creación de la instancia, se ejecuta el algoritmo DFS
   desde todos los vértices del grafo
*/
public class DFS(val g: Grafo) {
    
    private val n : Int  
    private val color: Array<Color>
    private var ti     : Array<Int>
    private var tf     : Array<Int>
    private var tiempo : Int = 0
    private var pred  : Array<Int?>
    private var componentesList: Array<Int>
    private var contadorComponentes : Int = 0

    //PRE:  v > 0
    //post: ( forall i: color[i] == NEGRO)
    // tiempo: O(|v + E|)

    init {
        // Se ejecuta DFS
        this.n = g.obtenerNumeroDeVertices()
        this.color = Array<Color>(this.n){Color.BLANCO}
        this.ti = Array<Int>(this.n){(0)}
        this.tf = Array<Int>(this.n){(0)}
        this.tiempo = 0
        this.pred =  Array<Int?>(this.n){null}
        this.componentesList = Array(n){(0)}
        this.contadorComponentes = 0 
        for (v  in (0.. this.n-1)){
            if (this.color[v] == Color.BLANCO){
                contadorComponentes += 1
                dfsVisit(g,v)
            }
        }
    }

    /*
    fun dfsVisit: Aplica al grafo g, DFS partiendo desde el nodo u
    pre: (u in 0..n-1)
    post: (this.color[u] == Negro)
    Tiempo: O(|E|)
    */
    private fun dfsVisit(g: Grafo, u: Int) {
        this.tiempo += 1
        componentesList[u] = this.contadorComponentes
        this.ti[u] = tiempo
        this.color[u] = Color.GRIS

        g.adyacentes(u).forEach { v ->
            if (color[v.elOtroVertice(u)] == Color.BLANCO){
                this.pred[v.elOtroVertice(u)] = u
                this.dfsVisit(g, v.elOtroVertice(u))
            }
        }
        this.color[u] = Color.NEGRO 
        this.tiempo = this.tiempo +1
        this.tf[u] = this.tiempo

    }

    /*
     Retorna el predecesor de un vértice v. 

     pre: (v in 0..n-1)
     post: (obtenerPredecesor(v: Int) == this.pred[v] || null)

     tiempo: O(|1|)
    */
    fun obtenerPredecesor(v: Int) : Int? { 
        if ((v < 0) || (v >= this.n)){
            throw RuntimeException("Error: El vertice no pertenece al grafo")
        }
        return this.pred[v]
    }

    /*
     Retorna un par ordenado con el tiempo inical y final de un vértice durante la ejecución de DFS. 

      pre: (v in 0..n-1)
      post: ( obtenerTiempos(v) == pair(ti[v],tf[v]))
      tiempo: O(|1|)
    */
    fun obtenerTiempos(v: Int) : Pair<Int, Int> {  
        if ((v < 0) || (v >= this.n)){
            throw RuntimeException("Error: El vertice no pertence al grafo")
        }   
        return Pair<Int,Int>(this.ti[v],this.tf[v]) 
    }

    /*
    Indica si hay camino desde el vértice inicial u hasta el vértice v.
      PRECONDICION: ((v in 0..n-1) && (u in 0..n-1))
      POSTCONDICION: (hay == (ti[u] < ti[v]) && (tf[v] < tf[u]))
    
    TIEMPO: O(|V|**2)
    */
    fun hayCamino(u: Int, v: Int) : Boolean {
        var numeroVertices = g.obtenerNumeroDeVertices()
        if ((v !in 0..numeroVertices) || (u !in 0..numeroVertices)) {
            throw RuntimeException("Error: Alguno de los vertices no pertence al grafo")}
        
        var existe = false

        if ((ti[u]< ti[v]) && (tf[v] < tf[u])){
            existe = true
        }
        return existe 
    }

    /*
    fun caminoDesdeHasta: Retorna el camino desde el vértice  u hasta el un vértice v.

    PRECONDICION: ((v in 0..n-1) && (u in 0..n-1))
    POSTCONDICION: (camino = P<u..v>)
    
    TIEMPO: O(|V|**2)
    */

    
    @Throws(RuntimeException::class)
    fun caminoDesdeHasta(u: Int, v: Int) : Iterable<Int>  {  
        if ((v !in 0..n-1) || (u !in 0..n-1)) {
            throw RuntimeException("Error: Alguno de los vertices no pertence al grafo")
        }
            
        var camino = mutableListOf<Int>()
        
        if (this.hayCamino(u,v)){
            var auxiliar = v
           
            while ( u != auxiliar){
                var predecesor = this.obtenerPredecesor(auxiliar) as Int
                camino.add(0,predecesor)
                auxiliar = predecesor 
            }
            camino.add(v)
            return camino 
        } else{
            throw RuntimeException("No hay camino de: $u -> $v.")
        }
    }

    /*
    Retorna true si hay lados del bosque o false en caso contrario.
    Precondicion: g.size > 0
    Postcondicion: hayLadosBosque==True or hayLadosBosque == False
    Tiempo = O(1)
    */
    fun hayLadosDeBosque(): Boolean {
        var hayBosque: Boolean = false
        if (this.contadorComponentes > 0){
            hayBosque = true
        }
        return hayBosque
    }
    
    /*
    fun ladosDeBosque: Retorna los lados del bosque obtenido por DFS.
    Precondicion: hayLadosBosque == True
    Postcondicion: ladosBosque
    t(n) = O(|V|**2)
    */ 
    fun ladosDeBosque() : Iterator<Lado> {
        var verBosque: MutableList<Int> = mutableListOf()
        var ladosBosque = listOf<Lado>()

        try {
            if (!this.hayLadosDeBosque()){
                throw RuntimeException("No existen este tipo de lados.")
            }
            
        } catch (e: RuntimeException) {
            println("No existen este tipo de lados.")
            return ladosBosque.iterator()
        }

        for ( cont in 1..this.contadorComponentes){
            for (vert in 0..n-1){
                if (this.componentesList[vert] == cont){
                    verBosque += vert
                }
            }
        }

        for (i in 0..verBosque.size-2){
            ladosBosque += Arco(verBosque[i],verBosque[i+1])
        }

        return ladosBosque.iterator()
    }

    /*
    fun hayLadosDeIda: Retorna true si hay forward edges o false en caso contrario.

    Precondicion: g.size > 0
    Postcondicion: hayForwardEdges==True or hayForwardEdges==False
    Tiempo: O(|E|)
    */
    fun hayLadosDeIda(): Boolean {
        val ladosB = this.ladosDeBosque()
        var forwardEdges: Boolean = false
        
        for (lado in ladosB) {
            val u:Int = lado.cualquieraDeLosVertices()
            val v:Int = lado.elOtroVertice(u)
            
            if (((this.ti[u] < this.ti[v]) < (this.tf[v] < this.tf[u])) && !forwardEdges) {
                forwardEdges = true
                break
            }
        }
        return forwardEdges        
    }
    
    /*
    Retorna los forward edges del bosque obtenido por DFS.
    Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    Precondicion: hayLadosDeIda == True
    Postcondicion: ladosForward
    Tiempo: O(|E|)
    */
    fun ladosDeIda() : Iterator<Lado> {
        var ladoB =  this.ladosDeBosque()
        var ladosForward: MutableList<Lado> = mutableListOf()
        
        try {
            if (!this.hayLadosDeIda()){
                throw RuntimeException("No existen lados de tipo forward edges.")
            }
        } catch (e: RuntimeException) {
            println("No existen lados de tipo forward edges.")
            return ladosForward.iterator()
        }

        for (lado in ladoB){
            val u:Int = lado.cualquieraDeLosVertices()
            val v:Int = lado.elOtroVertice(u)
            if ((this.ti[u] < this.ti[v]) < (this.tf[v] < this.tf[u])){
                ladosForward += lado
            }
        }
        return ladosForward.iterator()
    }

    /*
    Retorna true si hay back edges o false en caso contrario.
    Precondicion: g.size > 0
    Postcondicion: hayLadosDeVuelta==True or hayLadosDeVuelta==False
    Tiempo: O(|E|)
    */
    fun hayLadosDeVuelta(): Boolean {
        var ladoB =  this.ladosDeBosque()
        var backEdges: Boolean = false

        for (lado in ladoB) {
            val u:Int = lado.cualquieraDeLosVertices()
            val v:Int = lado.elOtroVertice(u)
            if (((this.ti[v] <= this.ti[u]) < (this.tf[u] <= this.tf[v])) && !backEdges) {
                backEdges = true
                break
            }
        }    
        return backEdges
    }
    
    /*
    Retorna los back edges del bosque obtenido por DFS.
    Si no existen ese tipo de lados, entonces se lanza una RuntimeException
    Precondicion: hayLadosDeVuelta == True
    Postcondicion: ladosForward
    Tiempo: O(|E|)
    */
    fun ladosDeVuelta() : Iterator<Lado> {
        var ladoB =  this.ladosDeBosque()
        var ladosBack: MutableList<Lado> = mutableListOf()
        
        try {
            if (!this.hayLadosDeVuelta()){
                throw RuntimeException("No existen lados de tipo back edges.")
            }
        } catch (e: RuntimeException) {
            println("No existen lados de tipo back edges.")
            return ladosBack.iterator()
        }

        for (lado in ladoB){
            val u:Int = lado.cualquieraDeLosVertices()
            val v:Int = lado.elOtroVertice(u)
            if ((this.ti[v] <= this.ti[u]) < (this.tf[u] <= this.tf[v])){
                ladosBack += lado
            }
        }
        return ladosBack.iterator()
    }

    /*
    Retorna true si hay cross edges o false en caso contrario.
    Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    Precondicion: g.size > 0
    Postcondicion: hayLadosCruzados==True or hayLadosCruzados==False
    Tiempo: O(|E|)
    */
    fun hayLadosCruzados(): Boolean {
        var ladoB =  this.ladosDeBosque()
        var crossEdges: Boolean = false
        
        for (lado in ladoB) {
            val u:Int = lado.cualquieraDeLosVertices()
            val v:Int = lado.elOtroVertice(u)
            if (((this.ti[v] <= this.tf[v]) < (this.ti[u] <= this.tf[u])) && !crossEdges) {
                crossEdges = true
                break
            }
        }    
        return crossEdges
    }
    
    /*
    Retorna los cross edges del bosque obtenido por DFS.
    Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    Precondicion: hayLadosCruzados == True
    Postcondicion: ladosCross
    Tiempo: O(|E|)
    */
    fun ladosCruzados() : Iterator<Lado> {
        var ladoB =  this.ladosDeBosque()
        var ladosCross: MutableList<Lado> = mutableListOf()
        
        try {
            if (!this.hayLadosCruzados()){
                throw RuntimeException("No existen lados de tipo cross edges.")
            }
        } catch (e: RuntimeException) {
            println("No existen lados de tipo cross edges.")
            return ladosCross.iterator()
        }

        for (lado in ladoB){
            val u:Int = lado.cualquieraDeLosVertices()
            val v:Int = lado.elOtroVertice(u)
            if ((this.ti[v] <= this.ti[u]) < (this.tf[u] <= this.tf[v])){
                ladosCross += lado
            }
        }
        return ladosCross.iterator()
    }

    // Imprime por la salida estándar el depth-first forest.
    fun mostrarBosqueDFS() {
        var arbol = mutableSetOf<Int>()
        var bosque : MutableList<MutableSet<Int>> = mutableListOf()
        val n: Int = g.obtenerNumeroDeVertices()
        for (i in 1..contadorComponentes) {
            for (j in 0..n-1) {
                if (componentesList[j] == i) {
                    arbol.add(j)
                }
            }
            bosque.add(arbol)
            arbol = mutableSetOf<Int>()
        }
        println(bosque)
    }
}
