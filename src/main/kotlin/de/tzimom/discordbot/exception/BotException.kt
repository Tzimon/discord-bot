package de.tzimom.discordbot.exception

open class BotException(val errorMessage: String, val emoji: String = "❌") : Exception(errorMessage)