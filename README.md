# Discord Bot

A discord bot largely inspired by the beloved Rythm bot (R.I.P.)

## Usage

To start using the bot you have to create kotlin main function and launch it from there.
You need to pass it the token of your discord bot and a [`BotConfig`](./src/main/kotlin/de/tzimom/discordbot/config/BotConfig.kt)

Example:

```kotlin
fun main(args: Array<String>) {
    DiscordBot.launch(
        args[0],
        BotConfig(
            OnlineStatus.ONLINE,
            null,
            GuildSettings("!")
        )
    )
}
```