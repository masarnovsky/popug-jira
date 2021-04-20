package com.masarnovsky.popugjira.analytics.config

import com.masarnovsky.popugjira.analytics.ACCOUNTING_GROUP_ID
import com.masarnovsky.popugjira.event.AccountCreatedEvent
import com.masarnovsky.popugjira.event.TransactionCreatedEvent
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
    fun accountStreamConsumerFactory(): ConsumerFactory<String, AccountCreatedEvent> {
        val props = mutableMapOf<String, Any>()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = ACCOUNTING_GROUP_ID
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java.name

        return DefaultKafkaConsumerFactory(
            props as Map<String, Any>,
            StringDeserializer(),
            JsonDeserializer(AccountCreatedEvent::class.java, false)
        )
    }

    @Bean
    fun kafkaAccountCreatedEventListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, AccountCreatedEvent> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, AccountCreatedEvent>()
        factory.consumerFactory = accountStreamConsumerFactory()

        return factory
    }

    @Bean
    fun transactionCreatedConsumerFactory(): ConsumerFactory<String, TransactionCreatedEvent> {
        val props = mutableMapOf<String, Any>()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = ACCOUNTING_GROUP_ID
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java.name

        return DefaultKafkaConsumerFactory(
            props as Map<String, Any>,
            StringDeserializer(),
            JsonDeserializer(TransactionCreatedEvent::class.java, false)
        )
    }

    @Bean
    fun kafkaTransactionCreatedEventListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, TransactionCreatedEvent> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, TransactionCreatedEvent>()
        factory.consumerFactory = transactionCreatedConsumerFactory()

        return factory
    }

}