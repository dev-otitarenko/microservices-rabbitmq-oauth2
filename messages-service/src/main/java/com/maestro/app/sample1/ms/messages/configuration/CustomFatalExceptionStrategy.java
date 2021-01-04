package com.maestro.app.sample1.ms.messages.configuration;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.TransactionException;

import java.sql.SQLException;

public class CustomFatalExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    @Override
    public boolean isFatal(Throwable t) {
        if (t instanceof ListenerExecutionFailedException) {
            ListenerExecutionFailedException lefe = (ListenerExecutionFailedException) t;
            logger.error("[CustomFatalExceptionStrategy, ListenerExecutionFailedException] Failed to process inbound message from queue "
                    + lefe.getFailedMessage().getMessageProperties().getConsumerQueue()
                    + "; failed message: " + lefe.getFailedMessage(), t);
            return true;
        }
        if (t.getCause() instanceof JpaSystemException) {
            JpaSystemException ex = (JpaSystemException) t.getCause();
            logger.error("[CustomFatalExceptionStrategy, JpaSystemException] Failed to process inbound message from queue: " + ex.getMessage(), t);

            return true;
        }

        if (t.getCause() instanceof TransactionException) {
            TransactionException ex = (TransactionException) t.getCause();
            logger.error("[CustomFatalExceptionStrategy, TransactionException] Failed to process inbound message from queue: " + ex.getMessage(), t);

            return true;
        }

        if (t.getCause() instanceof SQLException) {
            SQLException ex = (SQLException) t.getCause();
            logger.error("[CustomFatalExceptionStrategy, SQLException] Failed to process inbound message from queue: " + ex.getMessage(), t);

            return true;
        }

        if (t.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) t.getCause();
            logger.error("[CustomFatalExceptionStrategy, ConstraintViolationException] Failed to process inbound message from queue: " + ex.getMessage(), t);

            return true;
        }
        return super.isFatal(t);
    }
}
