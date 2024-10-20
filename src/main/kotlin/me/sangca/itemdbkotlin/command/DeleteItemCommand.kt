package me.sangca.itemdbkotlin.command

import me.sangca.itemdbkotlin.service.ItemStackTransactionService
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.io.IOException

class DeleteItemCommand(
    private val transactionService: ItemStackTransactionService,
): CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, alias: String, args: Array<out String>?): Boolean {
        if ((args?.size == 3) && args[0].equals("delete", ignoreCase = true)) {
            try {
                transactionService.deleteEncodedItemStackWithCategoryAndKey(args[1], args[2])
            } catch (e: IOException) {
                sender.sendMessage("§cIOException occurred")
                return true
            } catch (e: InterruptedException) {
                sender.sendMessage("§cInterruptedException occurred")
                return true
            }

            sender.sendMessage("Item successfully deleted.")
            return true
        }

        return true
    }
}