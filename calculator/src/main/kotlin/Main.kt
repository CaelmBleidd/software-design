import token.Tokenizer
import visitor.CalcVisitor
import visitor.ParserVisitor
import visitor.PrintVisitor

fun main(args: Array<String>) {
    val expression = args.firstOrNull() ?: "(23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5) + 8 / 2"
    val tokenizer = Tokenizer()

    expression.forEach { tokenizer.handle(it) }

    val tokens = tokenizer.result.toList()
    val parserVisitor = ParserVisitor()

    tokens.forEach { it.accept(parserVisitor) }

    val rpnTokens = parserVisitor.result
    val printVisitor = PrintVisitor()

    rpnTokens.forEach { it.accept(printVisitor) }

    println(printVisitor.result)

    val calcVisitor = CalcVisitor()
    rpnTokens.forEach { it.accept(calcVisitor) }
    println(calcVisitor.result)
}