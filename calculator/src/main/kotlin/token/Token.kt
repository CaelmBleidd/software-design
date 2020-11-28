package token

import visitor.TokenVisitor

interface Token {
    fun accept(visitor: TokenVisitor)
}

data class NumberToken(val value: Long) : Token {
    override fun accept(visitor: TokenVisitor) = visitor.visit(this)
}

data class Bracket(val value: Char) : Token {
    override fun accept(visitor: TokenVisitor) = visitor.visit(this)

    fun isOpen() = value == '('
}

data class Operation(val value: Char) : Token {
    override fun accept(visitor: TokenVisitor) = visitor.visit(this)
}