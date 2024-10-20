package me.sangca.itemdbkotlin.command

import me.sangca.itemdbkotlin.service.ItemStackTransactionService
import me.sangca.itemdbkotlin.service.ItemStackTransformationService
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.io.IOException

class SpawnItemCommand(
    private val transformationService: ItemStackTransformationService,
    private val transactionService: ItemStackTransactionService,
): CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, alias: String, args: Array<out String>?): Boolean {
        if ((args?.size == 3) && args[0].equals("spawn", ignoreCase = true)) {

            val itemStack: ItemStack
            try {
                itemStack = transformationService.encodeItemStackFromBase64(
                    transactionService.getEncodedItemStackWithCategoryAndKey(args[1], args[2])
                )
            } catch (e: IOException) {
                sender.sendMessage("§cIOException occurred")
                return true
            } catch (e: InterruptedException) {
                sender.sendMessage("§cInterruptedException occurred")
                return true
            } catch (e: ClassNotFoundException) {
                sender.sendMessage("§cClassNotFoundException occurred")
                return true
            }

            (sender as Player).inventory.addItem(itemStack)
            sender.sendMessage("§cItem successfully spawned.")
            return true
        }

        return true
    }
}