package de.tzimom.discordbot.command

import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageChannel

interface CommandDispatcher {
    fun parseCommand(sender: Member, channel: MessageChannel, message: String): CommandContext?
    fun findCommand(label: String): CommandExecutor?
    fun dispatchCommand(command: CommandExecutor, context: CommandContext)
}