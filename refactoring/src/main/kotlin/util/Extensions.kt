package ru.akirakozov.sd.refactoring.util

import java.sql.Connection

fun <T> Connection.use(block: (Connection) -> T): T {
    try {
        return block(this)
    } finally {
        this.close()
    }
}