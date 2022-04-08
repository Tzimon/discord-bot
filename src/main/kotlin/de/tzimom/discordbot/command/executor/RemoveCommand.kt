package de.tzimom.discordbot.command.executor

import de.tzimom.discordbot.audio.AudioManager
import de.tzimom.discordbot.command.CommandExecutor
import de.tzimom.discordbot.exception.InvalidSyntaxException
import de.tzimom.discordbot.logging.ChannelLogger
import net.dv8tion.jda.api.entities.Member

class RemoveCommand : CommandExecutor {
    override val name = "remove"
    override val aliases = listOf("rm")

    override suspend fun execute(member: Member, label: String, args: List<String>, logger: ChannelLogger) {
        val index = args.firstOrNull()?.toIntOrNull() ?: throw InvalidSyntaxException()

        val track = AudioManager.remove(member.guild, index)

        logger.sendMessage("✅ Removed `${track.title}`")
    }
}