package com.malsolo.akka.sample4;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SynchornousDatabaseConnection {

    public static class ConnectionLost extends RuntimeException {
        public ConnectionLost() {super("Database connection lost");}
    }

    public static class RequestTimeOut extends RuntimeException {
        public RequestTimeOut() {super("Database request time out");}
    }

    private Map<Long, Product> inMemoryDb;
    private final Random failureRandom = new Random();

    public SynchornousDatabaseConnection() {
        inMemoryDb = new HashMap<>();
        inMemoryDb.put(1L, new Product(1, "Clean Socks", "Socks description"));
        inMemoryDb.put(2L, new Product(2, "Hat", "Hat description"));
        inMemoryDb.put(3L, new Product(3, "Gloves", "Gloves description"));
    }

    public Optional<Product> findProduct(long id) {
        try {
            TimeUnit.MILLISECONDS.sleep(failureRandom.nextInt(500) + 200); //Simulate delay
        } catch (InterruptedException ie) {}

        final int random = failureRandom.nextInt(10);
        if (random == 0) {
            //10% irrecoverably fail
            inMemoryDb = null;
            throw new ConnectionLost();
        } else if (random > 6) {
            //70% simple fail
            throw new RequestTimeOut();
        }

        return Optional.ofNullable(inMemoryDb.get(id));

    }
}
