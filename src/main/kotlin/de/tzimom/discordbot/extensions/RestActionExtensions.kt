package de.tzimom.discordbot.extensions

import kotlinx.coroutines.coroutineScope

import net.dv8tion.jda.api.requests.RestAction

suspend fun <T> RestAction<T>.execute(): T = coroutineScope { complete() }