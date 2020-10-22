package ru.akirakozov.sd.refactoring.model

data class Product(val name: String, val price: Int) {
    override fun toString() = "${name}\t${price}"
}