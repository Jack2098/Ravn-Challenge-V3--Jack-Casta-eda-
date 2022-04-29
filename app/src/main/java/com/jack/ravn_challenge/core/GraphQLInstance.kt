package com.jack.ravn_challenge.core

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.jack.ravn_challenge.core.apollo.AuthorizationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object GraphQLInstance {

    private val BASE_URL = "https://swapi-graphql.netlify.app/.netlify/functions/index"

    fun get(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://swapi-graphql.netlify.app/.netlify/functions/index")
            .build()
    }
}