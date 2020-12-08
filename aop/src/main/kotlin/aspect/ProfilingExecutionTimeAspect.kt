package aspect

import kotlin.math.max
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

@Aspect
open class ProfilingExecutionTimeAspect {
    open val executions = mutableMapOf<String, MutableList<Long>>()

    @Around("execution(* calculator..*(..))")
    open fun logExecution(joinPoint: ProceedingJoinPoint): Any? {
        val beganAt = System.currentTimeMillis()
        val methodName = "${joinPoint.signature.declaringTypeName}.${joinPoint.signature.name}"
        return joinPoint.proceed(joinPoint.args).also {
            executions.getOrPut(methodName) {
                mutableListOf()
            }.add(System.currentTimeMillis() - beganAt)
        }
    }

    open fun print() {
        val tree = Tree("")
        executions.forEach {
            val name = it.key
            val parts = name.split(".")
            val total = it.value.size
            val sum = it.value.sum()
            val average = sum.toDouble() / total
            var curTree = tree

            var index = 0
            parts.forEach { part ->
                var found = false
                curTree.children.forEach here@{ t ->
                    if (t.name == part) {
                        curTree = t
                        found = true
                        return@here
                    }
                }
                index++
                var newName = part
                if (!found) {
                    if (index == parts.size) {
                        newName += "| Count: $total | Sum time: ${sum}ms | Average: ${average}ms"
                    }
                    val newTree = Tree(newName, level = curTree.level + 1)
                    curTree.children += newTree
                    curTree = newTree
                }
            }
        }
        tree.print(0)
    }
}

open class Tree(val name: String, val children: MutableList<Tree> = mutableListOf(), val level: Int = 0) {
    open fun print(i: Int) {
        val l = (".".repeat(max(0, (level - 1) * 7)) + name).also { println(it) }
        children.forEach { it.print(l.length) }
    }
}