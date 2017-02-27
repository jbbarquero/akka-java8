package com.malsolo.akka.sample4;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sample4");

        final ActorRef dbsupervisor = system.actorOf(DBSupervisor.props(), "dbsupervisor");

        system.actorOf(WebServer.props(dbsupervisor, "localhost", 80), "webserver");


        System.out.println("ENTER to terminate");
        try (Scanner scanner = new Scanner(System.in)) {
            scanner.nextLine();
        }
        system.terminate();
        System.exit(0);

    }
}
