package de.tzimom.discordbot.command.executor

import de.tzimom.discordbot.audio.AudioManager
import de.tzimom.discordbot.command.CommandExecutor
import de.tzimom.discordbot.logging.ChannelLogger
import de.tzimom.discordbot.util.FormattingUtils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import java.time.Duration

class PlaySkipCommand : CommandExecutor {
    override val name = "playskip"
    override val aliases = listOf("ps")

    override suspend fun execute(member: Member, label: String, args: List<String>, logger: ChannelLogger) {
        AudioManager.skip(member.guild)

        if (args.isEmpty()) return

        val query = args.joinToString(" ")
        logger.sendMessage("🎵 Searching 🔎 `$query`")

        val trackInfo = AudioManager.play(member, query, true)
        val embedBuilder = EmbedBuilder()

        embedBuilder.setTitle(trackInfo.title, trackInfo.uri)
        embedBuilder.addField("Channel", trackInfo.author, true)
        embedBuilder.addField("Song Duration", FormattingUtils.formatTrackDuration(trackInfo.length), true)
        embedBuilder.setAuthor("Added to queue")

        logger.sendEmbed(embedBuilder.build())
    }
}