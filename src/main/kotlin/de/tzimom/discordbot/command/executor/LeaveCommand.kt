package de.tzimom.discordbot.command.executor

import de.tzimom.discordbot.audio.AudioManager
import de.tzimom.discordbot.command.CommandExecutor
import de.tzimom.discordbot.logging.ChannelLogger
import net.dv8tion.jda.api.entities.Member

class LeaveCommand : CommandExecutor {
    override val name = "leave"
    override val aliases = listOf("l", "disconnect", "dc")

    override suspend fun execute(member: Member, label: String, args: List<String>, logger: ChannelLogger) =
        AudioManager.disconnect(member.guild)
}