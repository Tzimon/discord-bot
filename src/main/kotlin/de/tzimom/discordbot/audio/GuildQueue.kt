package de.tzimom.discordbot.audio

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo
import de.tzimom.discordbot.exception.NothingPlayingException
import de.tzimom.discordbot.exception.TrackNotFoundException
import net.dv8tion.jda.api.entities.Guild

class GuildQueue(guild: Guild, audioPlayerManager: AudioPlayerManager) : AudioEventAdapter() {
    private val audioPlayer = audioPlayerManager.createPlayer()
    private val sendHandler = AudioPlayerSendHandler(audioPlayer)

    private val tracks = ArrayDeque<AudioTrack>()

    var loopMode = LoopMode.NO_LOOP
    var volume by audioPlayer::volume

    init {
        audioPlayer.addListener(this)
        guild.audioManager.sendingHandler = sendHandler
    }

    fun enqueueTrack(track: AudioTrack, top: Boolean) {
        if (!audioPlayer.startTrack(track, true)) if (top) tracks.addFirst(track) else tracks.addLast(track)
    }

    fun removeTrack(index: Int): AudioTrackInfo {
        val track = tracks.getOrNull(index) ?: throw TrackNotFoundException()
        tracks.remove(track)
        return track.info
    }

    fun nextTrack() {
        audioPlayer.stopTrack()
        audioPlayer.startTrack(tracks.removeFirstOrNull() ?: return, false)
    }

    fun seek(timeStamp: Long) {
        val playingTrack = audioPlayer.playingTrack ?: throw NothingPlayingException()
        playingTrack.position = timeStamp
    }

    fun togglePaused(): Boolean {
        audioPlayer.isPaused = !audioPlayer.isPaused
        return audioPlayer.isPaused
    }

    override fun onTrackEnd(player: AudioPlayer, track: AudioTrack, endReason: AudioTrackEndReason) {
        if (endReason.mayStartNext) {
            when (loopMode) {
                LoopMode.LOOP_SONG -> tracks.addFirst(track.makeClone())
                LoopMode.LOOP_QUEUE -> tracks.addLast(track.makeClone())
                else -> {}
            }

            nextTrack()
        }
    }

    val playingTrack: AudioTrackInfo? get() = audioPlayer.playingTrack.info
    val trackList get() = tracks.map { it.info }
}