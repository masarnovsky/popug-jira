package com.masarnovsky.popugjira.tasks.config

import com.masarnovsky.popugjira.tasks.TASKS_GROUP_ID
import com.masarnovsky.popugjira.tasks.event.AccountCreatedEvent
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
    fun <T> consumerFactory(kclass: Class<T>): ConsumerFactory<String, T> {
        val props = mutableMapOf<String, Any>()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = TASKS_GROUP_ID
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java.name

        return DefaultKafkaConsumerFactory(
            props as Map<String, Any>,
            StringDeserializer(),
            JsonDeserializer(kclass, false)
        )
    }

    @Bean
    fun kafkaAccountCreatedEventListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, AccountCreatedEvent> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, AccountCreatedEvent>()
        factory.consumerFactory = consumerFactory(AccountCreatedEvent::class.java)

        return factory
    }
}