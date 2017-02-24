package com.malsolo.akka.sample1;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import lombok.Data;

public class Counter extends AbstractLoggingActor {

    @Data
    public static class Message {
        private final String value;
    }

    public static Props props() {
        return Props.create(Counter.class);
    }

    private int counter;

    public Counter() {
        //@formatter:off
        receive(
           ReceiveBuilder
              .match(Message.class, this::onMessage)
              .matchAny(m -> log().error("Unexpected message class {}", m.getClass().getName()))
              .build()
        );
        //@formatter:on
    }

    private void onMessage(Message message) {
        counter++;
        log().info("Incremented counter {} with the message {}", counter, message);
    }
}
