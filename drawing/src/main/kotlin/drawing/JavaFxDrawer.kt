package drawing

import graph.structures.Circle
import graph.structures.Line
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import javafx.stage.Stage

class JavaFxDrawerApi(
    private val lines: MutableList<Line> = mutableListOf(),
    private val circles: MutableList<Circle> = mutableListOf()
) : DrawingApi {

    override fun drawingAreaWidth() = 600

    override fun drawingAreaHeight() = 600

    override fun drawCircle(circle: Circle) {
        circles += circle
    }

    override fun drawLine(line: Line) {
        lines += line
    }

    override fun show() {
        JavaFxDrawer.initialize(lines, circles, drawingAreaWidth(), drawingAreaHeight())
        Application.launch(JavaFxDrawer::class.java)
    }
}

class JavaFxDrawer : Application() {
    override fun start(primaryStage: Stage) {
        val root = Group()
        val canvas = Canvas(width.toDouble(), height.toDouble())
        val gc = canvas.graphicsContext2D
        root.children += canvas
        gc.fill = Color.BLUE
        circles.forEach { gc.fillOval(it.center.x, it.center.y, it.radius, it.radius) }

        gc.stroke = Color.GREEN
        lines.forEach { gc.strokeLine(it.fst.x, it.fst.y, it.snd.x, it.snd.y) }

        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

    companion object {
        private var lines: List<Line> = mutableListOf()
        private var circles: List<Circle> = mutableListOf()
        private var width: Int = 0
        private var height: Int = 0

        fun initialize(lines: List<Line>, circles: List<Circle>, width: Int, height: Int) {
            JavaFxDrawer.lines = lines
            JavaFxDrawer.circles = circles
            JavaFxDrawer.width = width
            JavaFxDrawer.height = height
        }
    }
}