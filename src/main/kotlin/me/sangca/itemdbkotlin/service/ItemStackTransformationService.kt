package me.sangca.itemdbkotlin.service

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import me.sangca.itemdbkotlin.entity.SerializedItemStack
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

class ItemStackTransformationService(private val objectMapper: ObjectMapper) {

    @Throws(IllegalStateException::class, IOException::class)
    fun encodeItemStackToBase64(itemStack: ItemStack): String {
        val outputStream = ByteArrayOutputStream()
        val dataOutput = BukkitObjectOutputStream(outputStream)

        dataOutput.writeObject(itemStack)
        return Base64Coder.encodeLines(outputStream.toByteArray())
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    fun encodeItemStackFromBase64(itemStackAsString: String): ItemStack {
        val inputStream = ByteArrayInputStream(Base64Coder.decodeLines(itemStackAsString))
        val dataInput = BukkitObjectInputStream(inputStream)

        return dataInput.readObject() as ItemStack
    }

    @Throws(JsonProcessingException::class)
    fun getItemListFromString(itemListAsString: String): List<SerializedItemStack> {

        return objectMapper.readValue(itemListAsString, object : TypeReference<List<SerializedItemStack>>() {})
    }
}