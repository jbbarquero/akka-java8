package com.malsolo.akka.sample2;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import lombok.Data;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public class Alarm extends AbstractLoggingActor {

    static class Activity {}
    @Data
    static class Disable {
        private final String password;
    }
    @Data
    static class Enable {
        private final String password;
    }

    private final String password;
    private final PartialFunction<Object, BoxedUnit> enabled;
    private final PartialFunction<Object, BoxedUnit> disabled;

    public Alarm(String password) {
        this.password = password;

        enabled = ReceiveBuilder
                .match(Activity.class, this::onActivity)
                .match(Disable.class, this::onDisable)
                .build();

        disabled = ReceiveBuilder
                .match(Enable.class, this::onEnable)
                .build();

        receive(disabled);
    }

    private void onActivity(Activity activity) {
        log().warning("ALAAAAAAAAAAAAAAAAAAAAAARM!");
    }

    private void onDisable(Disable disable) {
        if (password.equals(disable.password)) {
            log().info("Alarm disabled");
            getContext().become(disabled);
        }
        else {
            log().error("Incorrect password for disabling the alarm");
        }
    }

    private void onEnable(Enable enable) {
        if (password.equals(enable.password)) {
            log().info("Alarm enabled");
            getContext().become(enabled);
        }
        else {
            log().warning("Incorrect password for enabling the alarm");
        }
    }

    public static Props props(String password) {
        return Props.create(Alarm.class, password);
    }



}
