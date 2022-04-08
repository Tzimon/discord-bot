package de.tzimom.discordbot.logging

import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed

interface ChannelLogger {
    suspend fun sendTyping()
    suspend fun sendMessage(message: String): Message
    suspend fun sendEmbed(embed: MessageEmbed): Message
}