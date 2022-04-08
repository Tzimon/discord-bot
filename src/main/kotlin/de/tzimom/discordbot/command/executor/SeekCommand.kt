package de.tzimom.discordbot.command.executor

import de.tzimom.discordbot.audio.AudioManager
import de.tzimom.discordbot.command.CommandExecutor
import de.tzimom.discordbot.exception.InvalidSyntaxException
import de.tzimom.discordbot.logging.ChannelLogger
import net.dv8tion.jda.api.entities.Member

class SeekCommand : CommandExecutor {
    override val name = "seek"
    override val aliases = listOf<String>()

    override suspend fun execute(member: Member, label: String, args: List<String>, logger: ChannelLogger) {
        val timeStamp = args.firstOrNull()?.toLongOrNull() ?: throw InvalidSyntaxException()

        AudioManager.seek(member.guild, timeStamp)

        logger.sendMessage("🎵 Set position to `$timeStamp` ⏩")
    }
}