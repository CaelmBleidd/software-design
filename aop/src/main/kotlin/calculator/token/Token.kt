package calculator.token

import calculator.visitor.TokenVisitor


interface Token {
    fun accept(visitor: TokenVisitor)
}

open class NumberToken(val value: Long) : Token {
    override fun accept(visitor: TokenVisitor) = visitor.visit(this)
}

open class Bracket(val value: Char) : Token {
    override fun accept(visitor: TokenVisitor) = visitor.visit(this)

    fun isOpen() = value == '('
}

open class Operation(val value: Char) : Token {
    override fun accept(visitor: TokenVisitor) = visitor.visit(this)
}