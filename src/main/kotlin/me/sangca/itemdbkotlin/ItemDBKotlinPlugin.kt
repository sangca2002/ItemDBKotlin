package me.sangca.itemdbkotlin

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kr.junhyung.pluginjar.annotation.Plugin
import me.sangca.itemdbkotlin.command.*
import me.sangca.itemdbkotlin.service.ItemStackTransactionService
import me.sangca.itemdbkotlin.service.ItemStackTransformationService
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.net.http.HttpClient

@Plugin
class ItemDBKotlinPlugin: JavaPlugin() {
    private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onEnable() {
        val transformationService = ItemStackTransformationService(ObjectMapper())
        val transactionService = ItemStackTransactionService(HttpClient.newHttpClient())

        val itemCommand = ItemCommand(coroutineScope)
        itemCommand.addCommand(ItemListCommand(transformationService, transactionService))
        itemCommand.addCommand(RegisterItemCommand(transformationService, transactionService))
        itemCommand.addCommand(DeleteItemCommand(transactionService))
        itemCommand.addCommand(SpawnItemCommand(transformationService, transactionService))
        Bukkit.getCommandMap().register("itemdb", itemCommand)
    }
 }