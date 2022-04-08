package de.tzimom.discordbot.command

import de.tzimom.discordbot.command.executor.*
import de.tzimom.discordbot.exception.BotException
import de.tzimom.discordbot.settings.SettingsManager
import de.tzimom.discordbot.logging.FormattedChannelLogger
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.exceptions.PermissionException

import java.util.regex.Pattern

class CommandManager(private val settingsManager: SettingsManager) : CommandDispatcher {
    private val commands = listOf(
        JoinCommand(),
        LeaveCommand(),
        PlayCommand(),
        PauseCommand(),
        RemoveCommand(),
        SkipCommand(),
        PlaySkipCommand(),
        LoopCommand(),
        LoopQueueCommand(),
        VolumeCommand(),
        SeekCommand(),
        QueueCommand()
    )

    override fun parseCommand(sender: Member, channel: MessageChannel, message: String): CommandContext? {
        val prefix = settingsManager[sender.guild].prefix

        if (!message.startsWith(prefix)) return null

        val args = message.substring(prefix.length).split(Pattern.compile(" +")).toMutableList()
        val label = args.removeFirstOrNull()?.lowercase() ?: return null

        return CommandContext(sender, channel, label, args)
    }

    override fun findCommand(label: String) =
        commands.firstOrNull { it.name.lowercase() == label }
            ?: commands.firstOrNull { it.aliases.any { alias -> alias.lowercase() == label } }

    override fun dispatchCommand(command: CommandExecutor, context: CommandContext) {
        val sender = context.sender
        val label = context.label
        val args = context.args
        val channel = context.channel

        val logger = FormattedChannelLogger(channel)

        try {
            runBlocking { command.execute(sender, label, args, logger) }
        } catch (botException: BotException) {
            if (channel.canTalk()) channel.sendMessage("**${botException.emoji} ${botException.errorMessage}**").queue()
        } catch (permissionException: PermissionException) {}
    }
}