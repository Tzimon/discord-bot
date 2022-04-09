package de.tzimom.discordbot

import de.tzimom.discordbot.config.BotConfig
import de.tzimom.discordbot.command.CommandManager
import de.tzimom.discordbot.eventhandler.MessageReceivedListener
import de.tzimom.discordbot.settings.GuildSettingsManager
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.utils.cache.CacheFlag

class DiscordBot(jda: JDA, botConfig: BotConfig) {
    private val guildSettingsManager = GuildSettingsManager(botConfig.defaultGuildSettings)

    private val commandManager = CommandManager(guildSettingsManager)

    init {
        jda.addEventListener(MessageReceivedListener(commandManager))
    }

    companion object {
        fun launch(token: String, botConfig: BotConfig): DiscordBot {
            val builder = JDABuilder.createDefault(token)

            builder.disableCache(
                CacheFlag.ACTIVITY,
                CacheFlag.EMOTE,
                CacheFlag.CLIENT_STATUS,
                CacheFlag.ONLINE_STATUS,
                CacheFlag.MEMBER_OVERRIDES,
                CacheFlag.ROLE_TAGS
            )

            builder.enableCache(
                CacheFlag.VOICE_STATE
            )

            builder.setActivity(botConfig.activity)

            val jda = builder.build()

            return DiscordBot(jda, botConfig)
        }
    }
}