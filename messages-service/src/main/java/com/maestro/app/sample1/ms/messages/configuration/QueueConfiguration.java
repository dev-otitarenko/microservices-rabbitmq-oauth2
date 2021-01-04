package com.maestro.app.sample1.ms.messages.configuration;

import com.maestro.app.sample1.ms.messages.utils.Constants;
import com.maestro.app.utils.queue.QueueConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.util.ErrorHandler;

@EnableRabbit
@Configuration
public class QueueConfiguration {
    @Bean
    public TopicExchange eventExchange() {
        return new TopicExchange(QueueConstants.EXCHANGE_GENERAL_NAME);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue queueUserMessages() {
        return new Queue(Constants.QUEUE_USERMESSAGE_NAME);
    }

    @Bean
    public Queue queueBroadcastMessages() {
        return new Queue(Constants.QUEUE_BROADCASTMESSAGE_NAME);
    }

    @Bean
    public Binding bindingUserMessages(Queue queueUserMessages, TopicExchange eventExchange) {
        return BindingBuilder
                .bind(queueUserMessages)
                .to(eventExchange)
                .with(Constants.QUEUE_USERMESSAGE_KEY_ROUTES);
    }

    @Bean
    public Binding bindingBroadcastMessages(Queue queueBroadcastMessages, TopicExchange eventExchange) {
        return BindingBuilder
                .bind(queueBroadcastMessages)
                .to(eventExchange)
                .with(Constants.QUEUE_BROADCASTMESSAGE_KEY_ROUTES);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                               SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setErrorHandler(errorHandler());
        return factory;
    }

    @Bean
    public ErrorHandler errorHandler() {
        return new ConditionalRejectingErrorHandler(new CustomFatalExceptionStrategy());
    }
}
