package com.capstone.utils;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;

public class StepLogger implements ConcurrentEventListener {
    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepFinished.class, this::handleStepFinished);
        publisher.registerHandlerFor(TestCaseStarted.class, this::handleScenarioStart);
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleScenarioEnd);
    }

    private void handleScenarioStart(TestCaseStarted event) {
        System.out.println("\n=== Running Scenario: " + event.getTestCase().getName() + " ===");
    }

    private void handleStepFinished(TestStepFinished event) {
        TestStep step = event.getTestStep();
        if (step instanceof PickleStepTestStep) {
            String text = ((PickleStepTestStep) step).getStep().getKeyword()
                         + ((PickleStepTestStep) step).getStep().getText();

            if (event.getResult().getStatus().isOk()) {
                System.out.println(ConsoleColors.GREEN + text + ConsoleColors.RESET);
            } else {
                System.out.println(ConsoleColors.RED + text + ConsoleColors.RESET);
            }
        }
    }

    private void handleScenarioEnd(TestCaseFinished event) {
        System.out.println("=== Finished Scenario: " + event.getTestCase().getName() + " ===\n");
    }
}
