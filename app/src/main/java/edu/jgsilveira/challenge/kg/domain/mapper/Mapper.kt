package edu.jgsilveira.challenge.kg.domain.mapper

interface Mapper<in T, out R> {
    fun map(from: T): R
}