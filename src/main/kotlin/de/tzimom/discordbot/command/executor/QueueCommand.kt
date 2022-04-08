package de.tzimom.discordbot.command.executor

import de.tzimom.discordbot.audio.AudioManager
import de.tzimom.discordbot.command.CommandExecutor
import de.tzimom.discordbot.exception.NothingPlayingException
import de.tzimom.discordbot.logging.ChannelLogger
import de.tzimom.discordbot.util.FormattingUtils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import kotlin.math.min

class QueueCommand : CommandExecutor {
    override val name = "queue"
    override val aliases = listOf("q")

    override suspend fun execute(member: Member, label: String, args: List<String>, logger: ChannelLogger) {
        val guild = member.guild

        val queue = AudioManager[guild]
        val tracks = queue.trackList
        val playingTrack = queue.playingTrack ?: throw NothingPlayingException()

        val embedBuilder = EmbedBuilder()

        embedBuilder.setTitle("Queue for ${guild.name}")
        embedBuilder.addField("Now Playing", FormattingUtils.formatTrackAsText(playingTrack), false)

        if (tracks.isNotEmpty()) {
            var tracksAsText = tracks.mapIndexed { index, audioTrack ->
                "`${index + 1}` ${FormattingUtils.formatTrackAsText(audioTrack)}"
            }.slice(0..min(tracks.size - 1, 5)).joinToString("\n\n")

            if (tracks.size > 5) tracksAsText += "\n\n${tracks.size - 5} more"

            embedBuilder.addField("Up Next", tracksAsText, false)
        }

        logger.sendEmbed(embedBuilder.build())
    }
}