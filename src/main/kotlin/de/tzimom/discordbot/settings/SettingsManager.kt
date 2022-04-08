package de.tzimom.discordbot.settings

import net.dv8tion.jda.api.entities.Guild

class SettingsManager(private val defaultSettingsGenerator: () -> GuildSettings) {
    private val configs = mutableMapOf<Long, GuildSettings>()

    operator fun get(guildId: Long): GuildSettings = configs.getOrPut(guildId, defaultSettingsGenerator)
    operator fun get(guild: Guild) = this[guild.idLong]
}