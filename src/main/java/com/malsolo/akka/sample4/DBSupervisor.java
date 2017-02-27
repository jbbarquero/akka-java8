package com.malsolo.akka.sample4;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;

public class DBSupervisor extends AbstractLoggingActor {

    public DBSupervisor() {
        //On separate dispatcher, since it's blocking
        final Props connectionProps = DbActor.props().withDispatcher("akkasample.dispatcher");

        //A router with 5 actor, one per DB connection
        List<Routee> routees = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ActorRef r = getContext().actorOf(connectionProps);
            getContext().watch(r);
            routees.add(new ActorRefRoutee(r));
        }
        Router router = new Router(new RoundRobinRoutingLogic(), routees);

        receive(
                ReceiveBuilder
                        .match(DbActor.GetProduct.class, request -> router.route(request, sender()))
                        .build()
        );
    }

    private final SupervisorStrategy strategy =
            new OneForOneStrategy(10, Duration.create("1 minute")
                    , DeciderBuilder
                        .match(SynchornousDatabaseConnection.ConnectionLost.class, e ->
                                SupervisorStrategy.restart()) //This require a new connection
                        .match(SynchornousDatabaseConnection.RequestTimeOut.class, e ->
                                SupervisorStrategy.resume()) //This just failed the current request
                        .build()
            );

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    public static Props props() {
        return Props.create(DBSupervisor.class);
    }
}
