package com.malsolo.akka.sample4;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;

public class DbActor extends AbstractLoggingActor {

    public static class GetProduct {}

    public static Props props() {
        return Props.create(DbActor.class);
    }
}
