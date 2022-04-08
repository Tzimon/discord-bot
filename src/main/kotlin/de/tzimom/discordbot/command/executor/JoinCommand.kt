package de.tzimom.discordbot.command.executor

import de.tzimom.discordbot.audio.AudioManager
import de.tzimom.discordbot.command.CommandExecutor
import de.tzimom.discordbot.logging.ChannelLogger
import net.dv8tion.jda.api.entities.Member

class JoinCommand : CommandExecutor {
    override val name = "join"
    override val aliases = listOf("j")

    override suspend fun execute(member: Member, label: String, args: List<String>, logger: ChannelLogger) {
        val channel = AudioManager.joinMember(member)
        logger.sendMessage("👍 Joined `${channel.name}`")
    }
}