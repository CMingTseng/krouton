@file:JvmName("PathElementTypes")
package com.natpryce.krouton

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

val string = object : PathElement<String>() {
    override fun parsePathElement(element: String) = element
}

val int = object : PathElement<Int>() {
    override fun parsePathElement(element: String) =
            parse<Int, NumberFormatException>(element, String::toInt)
}

val long = object : PathElement<Long>() {
    override fun parsePathElement(element: String) =
            parse<Long, NumberFormatException>(element, String::toLong)
}

val double = object : PathElement<Double>() {
    override fun parsePathElement(element: String) =
            parse<Double, NumberFormatException>(element, String::toDouble)
}

inline fun <reified E : Enum<E>> enum(): UrlScheme<E> = object : PathElement<E>() {
    override fun parsePathElement(element: String)
            = try { java.lang.Enum.valueOf(E::class.java, element) } catch (e: IllegalArgumentException) { null }
}

val isoLocalDate = object : PathElement<LocalDate>() {
    private val format = DateTimeFormatter.ISO_LOCAL_DATE

    override fun parsePathElement(element: String) =
            parse<LocalDate, DateTimeParseException>(element) { LocalDate.parse(element, format) }

    override fun pathElementFrom(value: LocalDate) =
            value.format(format)
}

val locale = object : PathElement<Locale>() {
    override fun parsePathElement(element: String) = Locale.forLanguageTag(element)
    override fun pathElementFrom(value: Locale) = value.toLanguageTag()
}
