package com.maestro.app.sample.ms.events.configuration;

import com.maestro.app.sample.ms.events.utils.Constants;
import com.maestro.app.utils.queue.QueueConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
    public Queue queueLogPrivate() {
        return new Queue(Constants.QUEUE_LOGSPRIVATE_NAME);
    }

    @Bean
    public Queue queueLogPublic() {
        return new Queue(Constants.QUEUE_LOGSPUBLIC_NAME);
    }

    @Bean
    public Queue queueLogConnects() {
        return new Queue(Constants.QUEUE_LOGSCONNECTS_NAME);
    }

    @Bean
    public Binding bindingLogPrivate(Queue queueLogPrivate, TopicExchange eventExchange) {
        return BindingBuilder
                .bind(queueLogPrivate)
                .to(eventExchange)
                .with(Constants.QUEUE_LOGSPRIVATE_KEY_ROUTES);
    }

    @Bean
    public Binding bindingLogPublic(Queue queueLogPublic, TopicExchange eventExchange) {
        return BindingBuilder
                .bind(queueLogPublic)
                .to(eventExchange)
                .with(Constants.QUEUE_LOGSPUBLIC_KEY_ROUTES);
    }

    @Bean
    public Binding bindingLogConnects(Queue queueLogConnects, TopicExchange eventExchange) {
        return BindingBuilder
                .bind(queueLogConnects)
                .to(eventExchange)
                .with(Constants.QUEUE_LOGSCONNECTS_KEY_ROUTES);
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
        return new CustomAMQPErrorHandler();
    }
}
