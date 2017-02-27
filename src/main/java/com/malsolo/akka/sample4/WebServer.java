package com.malsolo.akka.sample4;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class WebServer extends AbstractLoggingActor {

    private final ActorRef dbSupervisor;
    private final String host;
    private final int port;

    public WebServer(ActorRef dbSupervisor, String host, int port) {
        this.dbSupervisor = dbSupervisor;
        this.host = host;
        this.port = port;
    }

    public static Props props(ActorRef dbSupervisor, String host, int port) {
        return Props.create(() -> new WebServer(dbSupervisor, host, port));
    }
}
