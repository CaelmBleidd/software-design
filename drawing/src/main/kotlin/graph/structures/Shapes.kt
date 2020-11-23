package graph.structures

data class Point(val x: Double, val y: Double)

data class Circle(val center: Point, val radius: Double)

data class Line(val fst: Point, val snd: Point)