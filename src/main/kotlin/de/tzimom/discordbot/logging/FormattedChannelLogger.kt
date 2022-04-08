package de.tzimom.discordbot.logging

import de.tzimom.discordbot.extensions.execute

import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageEmbed

class FormattedChannelLogger(private val channel: MessageChannel) : ChannelLogger {
    override suspend fun sendTyping() { channel.sendTyping().execute() }
    override suspend fun sendMessage(message: String): Message = channel.sendMessage("**$message**").execute()
    override suspend fun sendEmbed(embed: MessageEmbed): Message = channel.sendMessageEmbeds(embed).execute()
}