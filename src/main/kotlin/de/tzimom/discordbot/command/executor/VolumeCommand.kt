package de.tzimom.discordbot.command.executor

import de.tzimom.discordbot.audio.AudioManager
import de.tzimom.discordbot.command.CommandExecutor
import de.tzimom.discordbot.exception.InvalidSyntaxException
import de.tzimom.discordbot.logging.ChannelLogger
import net.dv8tion.jda.api.entities.Member

class VolumeCommand : CommandExecutor {
    override val name = "volume"
    override val aliases = listOf("vol")

    override suspend fun execute(member: Member, label: String, args: List<String>, logger: ChannelLogger) {
        val volume = args.firstOrNull()?.toIntOrNull() ?: throw InvalidSyntaxException()

        AudioManager.setVolume(member.guild, volume)

        logger.sendMessage("🔊 Set volume to $volume%")
    }
}