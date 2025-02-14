package com.felipe.mvnsalarios.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class ExecutorProducer {

    @Produces
    public ExecutorService produceExecutorService() {
        return Executors.newFixedThreadPool(5);
    }

}