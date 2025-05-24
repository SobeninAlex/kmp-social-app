package ru.sobeninalex.utils.helpers

class UnauthorizedException(message: String?): RuntimeException(message)

class SomethingWrongException(message: String?): RuntimeException(message)