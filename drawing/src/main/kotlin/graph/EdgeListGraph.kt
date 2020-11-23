package graph

import drawing.DrawingApi
import java.io.InputStreamReader

class EdgeListGraph(private val drawingApi: DrawingApi, filePath: String = "edge") : Graph(drawingApi) {
    private val edges = mutableListOf<Edge>()
    private var size: Int = 0

    init {
        InputStreamReader(javaClass.classLoader.getResourceAsStream(filePath)!!).buffered().useLines { lines ->
            lines.forEachIndexed { i, line ->
                if (i == 0) {
                    size = line.toInt()
                    return@forEachIndexed
                }
                edges += line.split(" ").map { it.toInt() }.let { Edge(it[0] - 1, it[1] - 1) }
            }
        }
    }

    override fun drawGraph() {
        drawVertices(size)
        edges.forEach {
            drawEdge(it.from, it.to)
        }
        drawingApi.show()
    }

    private data class Edge(val from: Int, val to: Int)
}