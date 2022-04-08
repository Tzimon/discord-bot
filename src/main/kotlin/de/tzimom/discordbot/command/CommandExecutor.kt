package de.tzimom.discordbot.command

import de.tzimom.discordbot.logging.ChannelLogger
import net.dv8tion.jda.api.entities.Member

interface CommandExecutor {
    val name: String
    val aliases: List<String>

    suspend fun execute(member: Member, label: String, args: List<String>, logger: ChannelLogger)
}