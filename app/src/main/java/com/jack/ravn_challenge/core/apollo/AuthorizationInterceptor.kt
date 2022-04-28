package com.jack.ravn_challenge.core.apollo

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(): Interceptor {

    private val KEY= "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwb3NpdGlvbklkIjoiNTE3MDlkYTU3OTkwIiwicHJvamVjdElkIjoiYjVhNDVhNDAtNWU4OC00YmIxLWIxNTQtNGYyMmM1ZDUyNzc0IiwiZnVsbE5hbWUiOiJKYWNrIENyaXN0aWFuIENhc3Rhw7FlZGEgQ3J1eiIsImVtYWlsIjoiamFja3NzLmpjY2NAZ21haWwuY29tIiwiaWF0IjoxNjUwOTE4MDYyfQ.H6ZS8a6X1yJho0ZEOLTm3CHsrucR6vQuYHcMLpkwyMU"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", KEY)
            .build()

        return chain.proceed(request)
    }
}