import aspect.ProfilingExecutionTimeAspect
import calculator.token.Tokenizer
import calculator.visitor.CalcVisitor
import calculator.visitor.ParserVisitor
import configuration.ApplicationContextProvider
import configuration.ContextConfiguration
import org.springframework.context.annotation.AnnotationConfigApplicationContext

fun main() {
    ApplicationContextProvider().setApplicationContext(AnnotationConfigApplicationContext(ContextConfiguration::class.java))
    val ctx = ApplicationContextProvider.context

    val input = "(23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5) + 8 / 2 + ".repeat(5000) + "0"
    val tokenizer = ctx.getBean(Tokenizer::class.java)
    input.forEach { tokenizer.handle(it) }

    val tokens = tokenizer.result
    val parserVisitor = ctx.getBean(ParserVisitor::class.java)
    tokens.forEach { it.accept(parserVisitor) }

    val rpnTokens = parserVisitor.result
    val calcVisitor = ctx.getBean(CalcVisitor::class.java)
    rpnTokens.forEach { it.accept(calcVisitor) }

    println(calcVisitor.result)

    val profilingAspect = ctx.getBean(ProfilingExecutionTimeAspect::class.java)
    profilingAspect.print()
}