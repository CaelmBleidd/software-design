package graph

import drawing.DrawingApi
import graph.structures.Circle
import graph.structures.Line
import graph.structures.Point
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

abstract class Graph(private val drawingApi: DrawingApi) {
    private val points = mutableMapOf<Int, Point>()

    abstract fun drawGraph()

    fun drawVertices(numberOfVertices: Int) {
        val multiplier = 200
        val radius = 25.0
        val widthWithOffset = drawingApi.drawingAreaWidth() / 2
        val heightWithOffset = drawingApi.drawingAreaHeight() / 2
        val step = 2 * PI / numberOfVertices
        (0 until numberOfVertices).forEach {
            val x = (multiplier * cos(2 * PI - it * step) + widthWithOffset)
            val y = (multiplier * sin(2 * PI - it * step) + heightWithOffset)
            points += it to Point(x + radius / 2, y + radius / 2)
            drawingApi.drawCircle(Circle(Point(x, y), radius))
        }
    }

    fun drawEdge(from: Int, to: Int) {
        val fromPoint = points.getValue(from)
        val toPoint = points.getValue(to)
        drawingApi.drawLine(Line(fromPoint, toPoint))
    }
}