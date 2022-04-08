package de.tzimom.discordbot.audio

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo
import de.tzimom.discordbot.exception.*
import net.dv8tion.jda.api.entities.AudioChannel
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import java.net.URI
import java.net.URISyntaxException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object AudioManager {
    private val queues = mutableMapOf<Long, GuildQueue>()
    private val audioPlayerManager = DefaultAudioPlayerManager()

    init {
        AudioSourceManagers.registerRemoteSources(audioPlayerManager)
        AudioSourceManagers.registerLocalSource(audioPlayerManager)
    }

    fun joinMember(member: Member): AudioChannel {
        val channel = member.voiceState?.channel ?: throw UserNotConnectedException()
        val audioManager = member.guild.audioManager

        audioManager.openAudioConnection(channel)
        audioManager.isSelfDeafened = true

        return channel
    }

    fun disconnect(guild: Guild) {
        val audioManager = guild.audioManager

        audioManager.closeAudioConnection()
    }

    suspend fun play(member: Member, query: String, top: Boolean = false): AudioTrackInfo {
        joinMember(member)

        val validatedQuery = try {
            URI(query)
            query
        } catch (ignored: URISyntaxException) { "ytsearch:$query" }

        return suspendCoroutine { continuation ->
            audioPlayerManager.loadItemOrdered(this[member.guild], validatedQuery, object : AudioLoadResultHandler {
                override fun trackLoaded(track: AudioTrack) {
                    AudioManager[member.guild].enqueueTrack(track, top)
                    continuation.resume(track.info)
                }

                override fun playlistLoaded(playlist: AudioPlaylist) {
                    if (playlist.isSearchResult) return trackLoaded(playlist.tracks.first())
                    continuation.resumeWithException(UnsupportedVideoFormatException())
                }

                override fun noMatches() = continuation.resumeWithException(NoMatchesException())
                override fun loadFailed(exception: FriendlyException) = continuation.resumeWithException(VideoUnavailableException())
            })
        }
    }

    fun togglePause(guild: Guild) = this[guild].togglePaused()

    fun skip(guild: Guild) = this[guild].nextTrack()

    fun remove(guild: Guild, index: Int) = this[guild].removeTrack(index - 1)

    fun toggleLoop(guild: Guild, loopMode: LoopMode): LoopMode {
        val queue = this[guild]

        if (queue.loopMode == loopMode) queue.loopMode = LoopMode.NO_LOOP
        else queue.loopMode = loopMode

        return queue.loopMode
    }

    fun setVolume(guild: Guild, volume: Int) {
        this[guild].volume = volume
    }

    fun seek(guild: Guild, timeStamp: Long) = this[guild].seek(timeStamp * 1000)

    operator fun get(guild: Guild) = queues.getOrPut(guild.idLong) { GuildQueue(guild, audioPlayerManager) }
}