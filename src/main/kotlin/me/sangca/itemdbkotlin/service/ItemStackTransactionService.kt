package me.sangca.itemdbkotlin.service

import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class ItemStackTransactionService(private val httpClient: HttpClient) {

    @Throws(IOException::class, InterruptedException::class)
    fun getEncodedItemStackList(page: String): String {
        val request: HttpRequest = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(String.format("http://127.0.0.1:8080/items?page=%s", page)))
            .build()

        val response: HttpResponse<String> = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        return response.body()
    }

    @Throws(IOException::class, InterruptedException::class)
    fun postEncodedItemStackWithCategoryAndKey(itemStackAsString: String, category: String, key: String) {
        val request: HttpRequest = HttpRequest.newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString(itemStackAsString))
            .uri(URI.create(String.format("http://127.0.0.1:8080/items/%s/%s", category, key)))
            .build()

        httpClient.send(request, HttpResponse.BodyHandlers.ofString())
    }

    @Throws(IOException::class, InterruptedException::class)
    fun deleteEncodedItemStackWithCategoryAndKey(category: String, key: String) {
        val request: HttpRequest = HttpRequest.newBuilder()
            .DELETE()
            .uri(URI.create(String.format("http://127.0.0.1:8080/items/%s/%s", category, key)))
            .build()

        httpClient.send(request, HttpResponse.BodyHandlers.ofString())
    }

    @Throws(IOException::class, InterruptedException::class)
    fun getEncodedItemStackWithCategoryAndKey(category: String, key: String): String {
        val request: HttpRequest = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(String.format("http://127.0.0.1:8080/items/%s/%s", category, key)))
            .build()
        val response: HttpResponse<String> = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        return response.body()
    }
}