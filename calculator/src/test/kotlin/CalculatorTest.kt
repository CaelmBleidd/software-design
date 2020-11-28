import junit.framework.Assert.assertEquals
import org.junit.Test
import token.Tokenizer
import visitor.CalcVisitor
import visitor.ParserVisitor
import visitor.PrintVisitor

class CalculatorTest {
    private fun testCalculator(input: String): Long {
        val tokenizer = Tokenizer()
        input.forEach { tokenizer.handle(it) }

        val tokens = tokenizer.result
        val parserVisitor = ParserVisitor()
        tokens.forEach { it.accept(parserVisitor) }

        val rpnTokens = parserVisitor.result
        val printVisitor = PrintVisitor()
        rpnTokens.forEach { it.accept(printVisitor) }

        val calcVisitor = CalcVisitor()
        rpnTokens.forEach { it.accept(calcVisitor) }

        return calcVisitor.result
    }

    @Test
    fun testCalculation() {
        assertEquals(2L, testCalculator("2"))
        assertEquals(4L, testCalculator("2 + 2"))
        assertEquals(4L, testCalculator("(2 + 2)"))
        assertEquals(4L, testCalculator("( (2 + 2))"))
        assertEquals(4L, testCalculator("2+2"))
        assertEquals(4L, testCalculator("2 +2"))
        assertEquals(4L, testCalculator("2+ 2"))
        assertEquals(0L, testCalculator("( (2 - 2))"))
        assertEquals(-1L, testCalculator("( (1 - 2  ))"))
        assertEquals(6L, testCalculator("2 + 2 * 2"))
        assertEquals(8L, testCalculator("(2 + 2) * 2"))
        assertEquals(6L, testCalculator("2 * 3"))
        assertEquals(4L, testCalculator("16 /4"))
        assertEquals(3L, testCalculator("14 /4"))
        assertEquals(0L, testCalculator("2/5"))
        assertEquals(0L, testCalculator("0/1"))
        assertEquals(23L, testCalculator("21-5*5/2+(44/4-12/3)*2"))
        assertEquals(33L, testCalculator("1*2*3*4+5+6+7-8-9/2/3"))
        assertEquals(1279L, testCalculator("(23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5) + 8 / 2"))
    }
}