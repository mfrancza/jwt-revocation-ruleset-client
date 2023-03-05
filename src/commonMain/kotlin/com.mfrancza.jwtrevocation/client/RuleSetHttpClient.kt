package com.mfrancza.jwtrevocation.client

import com.mfrancza.jwtrevocation.rules.RuleSet
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthConfig
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.request.get
import io.ktor.http.takeFrom
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

/**
 * Retrieves a ruleset from an HTTP endpoint which provides it as JSON
 */
class RuleSetHttpClient(private val ruleServerUrl: String, bearerAuthConfig: BearerAuthConfig.() -> Unit, engine: HttpClientEngine? = null) {

    private val httpClient = run {
        val configBlock: HttpClientConfig<*>.() -> Unit = {
            install(ContentNegotiation) {
                json()
            }
            install(HttpCache)
            install(Auth) {
                bearer(bearerAuthConfig)
            }
        }
        if (engine != null) {
            HttpClient(engine, configBlock)
        } else {
            HttpClient(configBlock)
        }
    }

    suspend fun ruleSet() : RuleSet = httpClient.get { url {takeFrom(ruleServerUrl)} }.body()
}