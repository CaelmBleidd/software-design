package graph

import drawing.DrawingApi
import java.io.InputStreamReader

class AdjacencyMatrixGraph(private val drawingApi: DrawingApi, filePath: String = "adj") : Graph(drawingApi) {
    private val adjacencyMatrix = mutableListOf<List<Int>>()
    private val size: Int
        get() = adjacencyMatrix.size

    init {
        InputStreamReader(javaClass.classLoader.getResourceAsStream(filePath)!!).buffered().useLines { lines ->
            lines.forEach { line ->
                adjacencyMatrix += line.split(" ").map { it.toInt() }
            }
        }
    }

    override fun drawGraph() {
        drawVertices(size)
        (0 until size).forEach { i ->
            (0 until size).forEach { j ->
                if (i < j && adjacencyMatrix[i][j] == 1) {
                    drawEdge(i, j)
                }
            }
        }
        drawingApi.show()
    }
}