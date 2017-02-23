package com.malsolo.akka.sample1;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

public class Counter extends AbstractLoggingActor {

    public static class Message {
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
              .build()
        );
        //@formatter:on
    }

    private void onMessage(Message message) {
        counter++;
        log().info("Incremented counter {} with the message {}", counter, message);
    }
}
