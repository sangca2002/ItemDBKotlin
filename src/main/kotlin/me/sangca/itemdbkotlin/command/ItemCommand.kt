package me.sangca.itemdbkotlin.command

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ItemCommand(
    private val coroutineScope: CoroutineScope
): Command("itemdb") {
    private val commandList: ArrayList<CommandExecutor> = ArrayList()

    override fun execute(sender: CommandSender, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§cYou should be a player to use this command.")
            return true
        }

        if ((args?.size != 2) && (args?.size != 3)) {
            val helpMessage = arrayOfNulls<String>(4)
            helpMessage[0] = "§c/itemdb list <page> : shows the list of items in a page."
            helpMessage[1] = "§c/itemdb register <category> <key> : registers an item to a category with a unique key."
            helpMessage[2] = "§c/itemdb delete <category> <key> : deletes item in a category from a database."
            helpMessage[3] = "§c/itemdb spawn <category> <key> : spawns an item by its category and unique key."
            for (message in helpMessage) {
                sender.sendMessage(message!!)
            }
            return true
        }

        if (args.size == 2) {
            if (args[0].equals("register", ignoreCase = true)) {
                sender.sendMessage("§c/itemdb register <category> <key> : registers an item to a category with a unique key.")
                return true
            }
            if (args[0].equals("delete", ignoreCase = true)) {
                sender.sendMessage("§c/itemdb delete <category> <key> : deletes an item in a category with a unique key.")
                return true
            }
            if (args[0].equals("spawn", ignoreCase = true)) {
                sender.sendMessage("§c/itemdb spawn <category> <key> : spawns an item by its category and unique key.")
                return true
            }
            if (args[0].equals("list", ignoreCase = true) && (args.getOrNull(1)?.toIntOrNull() == null)) {
                sender.sendMessage("§cIncorrect usage. /itemdb list <page>")
                return true
            }
        }

        for (commandExecutor in commandList) {
            coroutineScope.launch {
                commandExecutor.onCommand(sender, this@ItemCommand, label, args)
            }
        }
        return true
    }

    fun addCommand(commandExecutor: CommandExecutor) {
        commandList.add(commandExecutor)
    }

    fun removeCommand(commandExecutor: CommandExecutor) {
        commandList.remove(commandExecutor)
    }
}