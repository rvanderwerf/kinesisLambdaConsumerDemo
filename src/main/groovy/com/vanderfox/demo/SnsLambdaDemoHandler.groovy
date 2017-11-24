package com.vanderfox.demo

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.KinesisEvent
import com.amazonaws.services.lambda.runtime.events.SNSEvent
import net.sf.json.groovy.JsonSlurper

class SnsLambdaDemoHandler implements RequestHandler<SNSEvent, Void> {

    @Override
    Void handleRequest(SNSEvent event, Context context) {
        LambdaLogger logger = context.getLogger();

        try {

            logger.log("SNS Lambda Consumer Demo invoked!".toString())
            logger.log("event="+event.toString())
            event.records.each { SNSEvent.SNSRecord record ->
                logger.log("received message! subject:${record.sns.subject} message:${record.sns.message}".toString())
                // in a real app you'd do something with a message
            }
        } catch (Throwable te) {
            logger.log("Error running lambda: ${te.message} - aborting run:${te.message}".toString())
        }
        logger.log("finished lambda".toString())
    }

}
