package de.tzimom.discordbot.eventhandler

import de.tzimom.discordbot.command.CommandDispatcher

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageReceivedListener(private val commandDispatcher: CommandDispatcher) : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        val author = event.author
        val channel = event.channel
        val content = event.message.contentRaw

        if (author.isBot) return

        if (!event.isFromGuild) {
            channel.sendMessage("**I only work in guilds**").complete()
            return
        }

        if (content.isEmpty()) return

        val callInfo = commandDispatcher.parseCommand(event.member!!, channel, content) ?: return
        val command = commandDispatcher.findCommand(callInfo.label) ?: return

        commandDispatcher.dispatchCommand(command, callInfo)
    }
}