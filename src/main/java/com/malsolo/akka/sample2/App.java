package com.malsolo.akka.sample2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sample2");

        final ActorRef alarm = system.actorOf(Alarm.props("password"), "alarm");

        alarm.tell(new Alarm.Activity(), ActorRef.noSender());
        alarm.tell(new Alarm.Enable("puf"), ActorRef.noSender());
        alarm.tell(new Alarm.Enable("password"), ActorRef.noSender());
        alarm.tell(new Alarm.Activity(), ActorRef.noSender());
        alarm.tell(new Alarm.Disable("incorrect"), ActorRef.noSender());
        alarm.tell(new Alarm.Disable("password"), ActorRef.noSender());
        alarm.tell(new Alarm.Activity(), ActorRef.noSender());

        System.out.println("ENTER to terminate");
        try (Scanner scanner = new Scanner(System.in)) {
            scanner.nextLine();
        }
        system.terminate();
        System.exit(0);
    }

}
