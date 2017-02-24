package com.malsolo.akka.sample1;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class App {
    public static void main( String[] args ) throws InterruptedException {

        ActorSystem system = ActorSystem.create("sample1");

        final ActorRef counter = system.actorOf(Counter.props(), "counter");

        //counter.tell(new Counter.Message("Hello World!"), ActorRef.noSender());
        counter.tell(new Integer(1), ActorRef.noSender());

        //@formatter:off
        IntStream
                .range(0, 5)
                .forEach(
                        i -> new Thread(
                                () -> IntStream
                                        .range(0, 5)
                                .forEach(j -> counter.tell(new Counter.Message(String.format("message %d-%d", i, j)), ActorRef.noSender()))
                        ).start()
        );
        //@formatter:on

        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println("ENTER to terminate");
        try (Scanner scanner = new Scanner(System.in)) {
            scanner.nextLine();
        }
        System.exit(0);
    }
}
