package com.masarnovsky.popugjira.analytics

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AnalyticsServiceApplication

fun main(args: Array<String>) {
	runApplication<AnalyticsServiceApplication>(*args)
}
