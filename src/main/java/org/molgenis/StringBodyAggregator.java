package org.molgenis;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

/**
 * Aggregates String message bodies by concatenating them so that they can be
 * more efficiently written to file.
 */
public class StringBodyAggregator implements AggregationStrategy {
    //See http://www.catify.com/2012/07/09/parsing-large-files-with-apache-camel/
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        if (oldExchange == null) {
            return newExchange;
        }

        String oldBody = oldExchange
                .getIn()
                .getBody(String.class);
        String newBody = newExchange
                .getIn()
                .getBody(String.class);
        String body = oldBody + newBody;

        oldExchange
                .getIn()
                .setBody(body);

        return oldExchange;
    }

}
