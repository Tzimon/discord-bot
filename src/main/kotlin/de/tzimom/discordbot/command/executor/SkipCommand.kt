package de.tzimom.discordbot.command.executor

import de.tzimom.discordbot.audio.AudioManager
import de.tzimom.discordbot.command.CommandExecutor
import de.tzimom.discordbot.logging.ChannelLogger
import net.dv8tion.jda.api.entities.Member

class SkipCommand : CommandExecutor {
    override val name = "skip"
    override val aliases = listOf("s")

    override suspend fun execute(member: Member, label: String, args: List<String>, logger: ChannelLogger) {
        AudioManager.skip(member.guild)
        logger.sendMessage("⏩ Skipped 👍")
    }
}