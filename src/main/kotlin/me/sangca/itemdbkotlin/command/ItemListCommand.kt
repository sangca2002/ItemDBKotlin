package me.sangca.itemdbkotlin.command

import me.sangca.itemdbkotlin.service.ItemStackTransactionService
import me.sangca.itemdbkotlin.service.ItemStackTransformationService
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.inventory.ItemStack
import java.io.IOException

class ItemListCommand(
    private val transformationService: ItemStackTransformationService,
    private val transactionService: ItemStackTransactionService,
) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, alias: String, args: Array<out String>?): Boolean {
        if ((args?.size == 2) && args[0].equals("list", ignoreCase = true)) {

            val serializedItemStackList = try {
                transformationService.getItemListFromString(transactionService.getEncodedItemStackList(args[1]))
            } catch (e: IOException) {
                sender.sendMessage("§cIOException occurred")
                return true
            } catch (e: InterruptedException) {
                sender.sendMessage("§cInterruptedException occurred")
                return true
            }

            for (serializedItemStack in serializedItemStackList) {
                val itemStack: ItemStack
                try {
                    itemStack =
                        transformationService.encodeItemStackFromBase64(serializedItemStack.itemStackAsString)
                } catch (e: IOException) {
                    sender.sendMessage("§cIOException occurred")
                    return true
                } catch (e: ClassNotFoundException) {
                    sender.sendMessage("§cClassNotFoundException occurred")
                    return true
                }

                sender.sendMessage(
                    String.format(
                        "§cCategory: %s Key: %s ItemName: %s ItemType: %s",
                        serializedItemStack.category,
                        serializedItemStack.key,
                        itemStack.itemMeta?.displayName(),
                        itemStack.type.translationKey()
                    )
                )
            }

            sender.sendMessage(String.format("§cCurrent Page: %s", args[1]))
            return true
        }

        return true
    }
}