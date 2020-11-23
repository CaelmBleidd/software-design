package drawing

import graph.structures.Circle
import graph.structures.Line
import java.awt.Color.blue
import java.awt.Color.green
import java.awt.Frame
import java.awt.Graphics2D
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.geom.Ellipse2D
import kotlin.system.exitProcess


class JavaAwtDrawer : Frame(), DrawingApi {
    init {
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(unused: WindowEvent) {
                exitProcess(0)
            }
        })
        setSize(drawingAreaWidth(), drawingAreaHeight())
        isVisible = true
    }

    override fun drawingAreaWidth() = 600
    override fun drawingAreaHeight() = 600

    override fun drawCircle(circle: Circle) {
        val g = graphics as Graphics2D
        g.paint = blue
        g.fill(
            Ellipse2D.Float(
                circle.center.x.toFloat(),
                circle.center.y.toFloat(),
                circle.radius.toFloat(),
                circle.radius.toFloat()
            )
        )
    }

    override fun drawLine(line: Line) {
        val g = graphics as Graphics2D
        g.paint = green
        g.drawLine(line.fst.x.toInt(), line.fst.y.toInt(), line.snd.x.toInt(), line.snd.y.toInt())
    }
}
