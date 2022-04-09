package de.tzimom.discordbot.settings

import net.dv8tion.jda.api.entities.Guild

class GuildSettingsManager(private val defaultGuildSettings: GuildSettings) {
    private val configs = mutableMapOf<Long, GuildSettings>()

    operator fun get(guildId: Long): GuildSettings = configs.getOrPut(guildId, defaultGuildSettings::copy)
    operator fun get(guild: Guild) = this[guild.idLong]
}