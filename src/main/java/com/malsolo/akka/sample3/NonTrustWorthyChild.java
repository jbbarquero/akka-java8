package com.malsolo.akka.sample3;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

public class NonTrustWorthyChild extends AbstractLoggingActor {
    public static class Command {}

    private long messages = 0L;

    public NonTrustWorthyChild() {
        receive(
                ReceiveBuilder
                        .match(Command.class, this::onCommand)
                        .build()
        );
    }

    private void onCommand(Command command) {
        messages++;
        if (messages % 4 == 0) {
            throw new RuntimeException("4th command");
        }
        else {
            log().info("Got a command ({})", messages);
        }
    }

    public static Props props() {
        return Props.create(NonTrustWorthyChild.class);
    }
}
