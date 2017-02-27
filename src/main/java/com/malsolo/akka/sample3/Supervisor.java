package com.malsolo.akka.sample3;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.duration.Duration;

public class Supervisor extends AbstractActor {

    public static final OneForOneStrategy STRATEGY = new OneForOneStrategy(
            10,
            Duration.create("10 seconds"),
            DeciderBuilder
                    .match(RuntimeException.class, ex ->
                            //SupervisorStrategy.restart()
                            //SupervisorStrategy.resume()
                            //SupervisorStrategy.stop()
                            SupervisorStrategy.escalate()
                    )
                    .build()
    );

    public Supervisor() {
        final ActorRef child = getContext().actorOf(NonTrustWorthyChild.props(), "child");

        receive(
                ReceiveBuilder
                        .matchAny(any -> child.forward(any, getContext()))
                        .build());
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return STRATEGY;
    }

    public static Props props() {
        return Props.create(Supervisor.class);
    }
}
