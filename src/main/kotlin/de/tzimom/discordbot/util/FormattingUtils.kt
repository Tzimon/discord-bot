package de.tzimom.discordbot.util

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo
import java.time.Duration

object FormattingUtils {
    fun formatTrackDuration(duration: Long) = Duration.ofMillis(duration).run {
        if (toHours() > 0) "%02d:%02d:%02d".format(toHours(), toMinutesPart(), toSecondsPart())
        else "%02d:%02d".format(toMinutesPart(), toSecondsPart())
    }

    fun formatTrackAsText(track: AudioTrackInfo) = """
        [${track.title}](${track.uri}) · (`${formatTrackDuration(track.length)}`)
    """.trimIndent()
}