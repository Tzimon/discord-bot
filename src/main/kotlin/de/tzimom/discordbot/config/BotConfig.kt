package de.tzimom.discordbot.config

import de.tzimom.discordbot.settings.GuildSettings
import net.dv8tion.jda.api.entities.Activity

data class BotConfig(val activity: Activity, val defaultGuildSettings: GuildSettings)