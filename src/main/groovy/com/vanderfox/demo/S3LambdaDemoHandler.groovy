package com.vanderfox.demo

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.S3Event
import com.amazonaws.services.s3.event.S3EventNotification


class sS3LambdaDemoHandler implements RequestHandler<S3Event, Void> {

    @Override
    Void handleRequest(S3Event event, Context context) {
        LambdaLogger logger = context.getLogger();

        try {

            logger.log("S3 Lambda Consumer Demo invoked!".toString())
            logger.log("event="+event.toString().toString())
            event.records.each { S3EventNotification.S3EventNotificationRecord record ->
                logger.log("received message! bucket:${record.s3.bucket} message:${record.s3.object.key}".toString())
                // in a real app you'd do something with a message
            }
        } catch (Throwable te) {
            logger.log("Error running lambda: ${te.message} - aborting run:${te.message}".toString())
        }
        logger.log("finished lambda".toString())
    }

}
