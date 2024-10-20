package me.sangca.itemdbkotlin.command

import me.sangca.itemdbkotlin.service.ItemStackTransactionService
import me.sangca.itemdbkotlin.service.ItemStackTransformationService
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.io.IOException

class RegisterItemCommand(
    private val transformationService: ItemStackTransformationService,
    private val transactionService: ItemStackTransactionService,
): CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, alias: String, args: Array<out String>?): Boolean {
        if ((args?.size == 3) && args[0].equals("register", ignoreCase = true)) {

            val heldItem = (sender as Player).inventory.itemInMainHand
            try {
                val encodedString: String = transformationService.encodeItemStackToBase64(heldItem)
                transactionService.postEncodedItemStackWithCategoryAndKey(encodedString, args[1], args[2])
            } catch (e: IOException) {
                sender.sendMessage("§cIOException occurred")
                return true
            } catch (e: InterruptedException) {
                sender.sendMessage("§cInterruptedException occurred")
                return true
            }

            sender.sendMessage("§cItem successfully registered.")
            return true
        }

        return true
    }
}