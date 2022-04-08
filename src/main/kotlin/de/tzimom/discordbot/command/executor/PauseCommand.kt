package de.tzimom.discordbot.command.executor

import de.tzimom.discordbot.audio.AudioManager
import de.tzimom.discordbot.command.CommandExecutor
import de.tzimom.discordbot.logging.ChannelLogger
import net.dv8tion.jda.api.entities.Member

class PauseCommand : CommandExecutor {
    override val name = "pause"
    override val aliases = listOf<String>()

    override suspend fun execute(member: Member, label: String, args: List<String>, logger: ChannelLogger) {
        val paused = AudioManager.togglePause(member.guild)

        if (paused) logger.sendMessage("Paused ⏸")
        else logger.sendMessage("⏯ Resuming 👍")
    }
}