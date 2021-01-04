package com.maestro.app.sample1.ms.events.configuration;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.TransactionException;
import org.springframework.util.ErrorHandler;

import java.sql.SQLException;

public class CustomAMQPErrorHandler implements ErrorHandler {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    @Override
    public void handleError(Throwable t) {
        if (t instanceof ListenerExecutionFailedException) {
            ListenerExecutionFailedException lefe = (ListenerExecutionFailedException) t;
            logger.error("[CustomAMQPErrorHandler, ListenerExecutionFailedException] Failed to process inbound message from queue "
                    + lefe.getFailedMessage().getMessageProperties().getConsumerQueue()
                    + "; failed message: " + lefe.getFailedMessage(), t);
        }
        if (t.getCause() instanceof JpaSystemException) {
            JpaSystemException ex = (JpaSystemException) t.getCause();
            logger.error("[CustomAMQPErrorHandler, JpaSystemException] Failed to process inbound message from queue: " + ex.getMessage(), t);
        }

        if (t.getCause() instanceof TransactionException) {
            TransactionException ex = (TransactionException) t.getCause();
            logger.error("[CustomAMQPErrorHandler, TransactionException] Failed to process inbound message from queue: " + ex.getMessage(), t);
        }

        if (t.getCause() instanceof SQLException) {
            SQLException ex = (SQLException) t.getCause();
            logger.error("[CustomAMQPErrorHandler, SQLException] Failed to process inbound message from queue: " + ex.getMessage(), t);
        }

        if (t.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) t.getCause();
            logger.error("[CustomAMQPErrorHandler, ConstraintViolationException] Failed to process inbound message from queue: " + ex.getMessage(), t);
        }

        throw new AmqpRejectAndDontRequeueException("Error Handler converted exception to fatal", t);
    }
}
