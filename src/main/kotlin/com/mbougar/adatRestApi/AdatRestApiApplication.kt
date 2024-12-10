package com.mbougar.adatRestApi

import com.mbougar.adatRestApi.security.RSAKeysProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RSAKeysProperties::class)
class AdatRestApiApplication

fun main(args: Array<String>) {
	runApplication<AdatRestApiApplication>(*args)
}
