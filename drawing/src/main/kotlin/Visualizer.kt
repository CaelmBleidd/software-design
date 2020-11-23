import drawing.DrawingApi
import drawing.JavaAwtDrawer
import drawing.JavaFxDrawerApi
import graph.AdjacencyMatrixGraph
import graph.EdgeListGraph
import graph.Graph

fun main(args: Array<String>) {
    require(args.size == 2) { "Usage: <api> <representation>, found $args" }

    val drawingApi: DrawingApi = when (args[0]) {
        "awt" -> JavaAwtDrawer()
        "fx" -> JavaFxDrawerApi()
        else -> error("Unknown drawer ${args[0]}, \"awt\" or \"fx\" expected")
    }

    val graph: Graph = when (args[1]) {
        "adj" -> AdjacencyMatrixGraph(drawingApi)
        "edge" -> EdgeListGraph(drawingApi)
        else -> error("Unknown graph representation ${args[1]}, \"adj\" or \"edge\" expected")
    }

    graph.drawGraph()
}