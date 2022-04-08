package de.tzimom.discordbot.command.executor

import de.tzimom.discordbot.audio.AudioManager
import de.tzimom.discordbot.audio.LoopMode
import de.tzimom.discordbot.command.CommandExecutor
import de.tzimom.discordbot.logging.ChannelLogger
import net.dv8tion.jda.api.entities.Member

class LoopCommand : CommandExecutor {
    override val name = "loop"
    override val aliases = listOf("lo")

    override suspend fun execute(member: Member, label: String, args: List<String>, logger: ChannelLogger) {
        logger.sendTyping()

        val loopMode = AudioManager.toggleLoop(member.guild, LoopMode.LOOP_SONG)

        logger.sendMessage(if (loopMode == LoopMode.NO_LOOP) "🔂 Disabled" else "🔂 Enabled")
    }
}