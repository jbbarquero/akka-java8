package com.malsolo.akka.sample1;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.util.Scanner;

public class App {
    public static void main( String[] args ) {

        ActorSystem system = ActorSystem.create("sample1");

        final ActorRef counter = system.actorOf(Counter.props(), "counter");

        counter.tell(new Counter.Message(), ActorRef.noSender());

        System.out.println("ENTER to terminate");
        try (Scanner scanner = new Scanner(System.in)) {
            scanner.nextLine();
        }
        System.exit(0);
    }
}
