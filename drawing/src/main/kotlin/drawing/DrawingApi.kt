package drawing

import graph.structures.Circle
import graph.structures.Line

interface DrawingApi {
    fun drawingAreaWidth(): Int

    fun drawingAreaHeight(): Int

    fun drawCircle(circle: Circle)

    fun drawLine(line: Line)

    fun show()
}