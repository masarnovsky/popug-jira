package com.masarnovsky.popugjira.accounting.config

import com.masarnovsky.popugjira.accounting.ACCOUNTING_GROUP_ID
import com.masarnovsky.popugjira.accounting.event.*
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
    fun taskCreatedConsumerFactory(): ConsumerFactory<String, TaskCreatedEvent> {
        val props = mutableMapOf<String, Any>()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = ACCOUNTING_GROUP_ID
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java.name

        return DefaultKafkaConsumerFactory(
            props as Map<String, Any>,
            StringDeserializer(),
            JsonDeserializer(TaskCreatedEvent::class.java, false)
        )
    }

    @Bean
    fun taskClosedConsumerFactory(): ConsumerFactory<String, TaskClosedEvent> {
        val props = mutableMapOf<String, Any>()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = ACCOUNTING_GROUP_ID
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java.name

        return DefaultKafkaConsumerFactory(
            props as Map<String, Any>,
            StringDeserializer(),
            JsonDeserializer(TaskClosedEvent::class.java, false)
        )
    }

    @Bean
    fun taskAssignedConsumerFactory(): ConsumerFactory<String, TaskAssignedEvent> {
        val props = mutableMapOf<String, Any>()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = ACCOUNTING_GROUP_ID
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java.name

        return DefaultKafkaConsumerFactory(
            props as Map<String, Any>,
            StringDeserializer(),
            JsonDeserializer(TaskAssignedEvent::class.java, false)
        )
    }

    @Bean
    fun kafkaTaskCreatedEventListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, TaskCreatedEvent> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, TaskCreatedEvent>()
        factory.consumerFactory = taskCreatedConsumerFactory()

        return factory
    }

    @Bean
    fun kafkaTaskClosedEventListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, TaskClosedEvent> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, TaskClosedEvent>()
        factory.consumerFactory = taskClosedConsumerFactory()

        return factory
    }

    @Bean
    fun kafkaTaskAssignedEventListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, TaskAssignedEvent> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, TaskAssignedEvent>()
        factory.consumerFactory = taskAssignedConsumerFactory()

        return factory
    }

    @Bean
    fun accountConsumerFactory(): ConsumerFactory<String, AccountCreatedEvent> {
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
        factory.consumerFactory = accountConsumerFactory()

        return factory
    }

    @Bean
    fun payoutCreatedConsumerFactory(): ConsumerFactory<String, PayoutCreated> {
        val props = mutableMapOf<String, Any>()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = ACCOUNTING_GROUP_ID
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java.name

        return DefaultKafkaConsumerFactory(
            props as Map<String, Any>,
            StringDeserializer(),
            JsonDeserializer(PayoutCreated::class.java, false)
        )
    }

    @Bean
    fun kafkaPayoutCreatedEventListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, PayoutCreated> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, PayoutCreated>()
        factory.consumerFactory = payoutCreatedConsumerFactory()

        return factory
    }
}