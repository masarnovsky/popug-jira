package com.masarnovsky.popugjira.tasks.config

import com.masarnovsky.popugjira.tasks.model.AccountDto
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@EnableKafka
@Configuration
class KafkaConsumerConfig {

    @Value(value = "\${kafka.server}")
    val bootstrapAddress: String = ""

    @Bean
    fun consumerFactory(): ConsumerFactory<String, AccountDto> {
        val props = mutableMapOf<String, Any>()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = "accounts"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java.name

        return DefaultKafkaConsumerFactory(
            props as Map<String, Any>,
            StringDeserializer(),
            JsonDeserializer(AccountDto::class.java, false)
        )
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, AccountDto> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, AccountDto>()
        factory.consumerFactory = consumerFactory()

        return factory
    }
}