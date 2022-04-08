package de.tzimom.discordbot.command

import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageChannel

data class CommandContext(val sender: Member, val channel: MessageChannel, val label: String, val args: List<String>)