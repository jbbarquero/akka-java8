package com.malsolo.akka.sample3;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.util.Scanner;
import java.util.stream.IntStream;

public class App {

    public static void main(String[] args) {

        ActorSystem system = ActorSystem.create("sample3");

        final ActorRef supervisor = system.actorOf(Supervisor.props(), "supervisor");

        IntStream.range(0, 10).forEach(
                i -> supervisor.tell(new NonTrustWorthyChild.Command(), ActorRef.noSender())
        );

        System.out.println("ENTER to terminate");
        try (Scanner scanner = new Scanner(System.in)) {
            scanner.nextLine();
        }
        system.terminate();
        System.exit(0);

    }
}
