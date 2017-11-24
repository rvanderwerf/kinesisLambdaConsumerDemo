package com.vanderfox.demo

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.KinesisEvent
import groovy.json.JsonSlurper
import net.sf.json.groovy.JsonSlurper

class KinesisLambdaDemoHandler implements RequestHandler<KinesisEvent, Void> {

    @Override
    Void handleRequest(KinesisEvent event, Context context) {
        LambdaLogger logger = context.getLogger();

        try {

            logger.log("Kinesis Lambda Consumer Demo invoked!".toString())
            logger.log("event="+event.toString().toString())
            event.records.each { KinesisEvent.KinesisEventRecord record ->
                logger.log("record:${record.toString()}".toString())
                record.properties.keySet().each { key ->
                    logger.log("property:${key}=${record.properties.get(key)}".toString())
                }
                JsonSlurper slurper = new JsonSlurper()
                // convert the parameter map into human readable JSON for the kinesis stream
                def stringPayload = new String(record.kinesis.getData().array())
                logger.log("stringpayload:${stringPayload}".toString())
                def payload = slurper.parseText(stringPayload)
                // in a real app we'd do something with these parameters in the parsed payload

                logger.log("data=${payload}".toString())
            }
        } catch (Throwable te) {
            logger.log("Error running lambda: ${te.message} - aborting run: ${te.message}".toString())
        }
        logger.log("finished lambda")
    }
}
