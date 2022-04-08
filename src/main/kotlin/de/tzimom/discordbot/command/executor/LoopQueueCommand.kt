package de.tzimom.discordbot.command.executor

import de.tzimom.discordbot.audio.AudioManager
import de.tzimom.discordbot.audio.LoopMode
import de.tzimom.discordbot.command.CommandExecutor
import de.tzimom.discordbot.logging.ChannelLogger
import net.dv8tion.jda.api.entities.Member

class LoopQueueCommand : CommandExecutor {
    override val name = "loopqueue"
    override val aliases = listOf("lq")

    override suspend fun execute(member: Member, label: String, args: List<String>, logger: ChannelLogger) {
        logger.sendTyping()

        val loopMode = AudioManager.toggleLoop(member.guild, LoopMode.LOOP_QUEUE)

        logger.sendMessage(if (loopMode == LoopMode.NO_LOOP) "🔁 Disabled" else "🔁 Enabled")
    }
}