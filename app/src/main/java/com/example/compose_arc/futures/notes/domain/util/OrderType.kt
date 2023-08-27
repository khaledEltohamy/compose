package com.example.compose_arc.futures.notes.domain.util

sealed class OrderType {
    object  Ascending : OrderType()
    object  Descending : OrderType()
}
