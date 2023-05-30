package com.example.LiftManagement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// LiftController class
@RestController
public class LiftController {
    private List<Lift> lifts = new ArrayList<>();

    // Add a new lift
    @PostMapping("/lifts")
    public ResponseEntity<Void> addLift(@RequestBody Lift lift) {
        lifts.add(lift);
        return ResponseEntity.ok().build();
    }

    // Add a new passenger to a lift
    @PostMapping("/lifts/{liftNo}/passengers")
    public ResponseEntity<Void> addPassengerToLift(
            @PathVariable int liftNo, @RequestBody Passenger passenger) {
        Lift lift = getLiftByNumber(liftNo);
        if (lift != null) {
            lift.getPassengers().add(passenger);
            passenger.setLift(lift);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete all passengers with ID less than 5
    @DeleteMapping("/passengers")
    public ResponseEntity<Void> deletePassengersWithIdLessThanFive() {
        for (Lift lift : lifts) {
            List<Passenger> passengers = lift.getPassengers();
            passengers.removeIf(passenger -> passenger.getPassengerId() < 5);
        }
        return ResponseEntity.ok().build();
    }

    // Find the maximum number of people with weight X that can travel in a lift
    @GetMapping("/lifts/{liftNo}/max-people")
    public ResponseEntity<Integer> findMaxPeopleWithWeightX(
            @PathVariable int liftNo, @RequestParam int weightX) {
        Lift lift = getLiftByNumber(liftNo);
        if (lift != null) {
            int maxPeople = 0;
            for (Passenger passenger : lift.getPassengers()) {
                if (passenger.getWeight() <= weightX) {
                    maxPeople++;
                }
            }
            return ResponseEntity.ok(maxPeople);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Find the number of people with weight greater than 50 in a given lift
    @GetMapping("/lifts/{liftNo}/people-weight-greater-than-fifty")
    public ResponseEntity<Integer> findPeopleWithWeightGreaterThanFifty(
            @PathVariable int liftNo) {
        Lift lift = getLiftByNumber(liftNo);
        if (lift != null) {
            int count = 0;
            for (Passenger passenger : lift.getPassengers()) {
                if (passenger.getWeight() > 50) {
                    count++;
                }
            }
            return ResponseEntity.ok(count);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Helper method to retrieve a lift by number
    private Lift getLiftByNumber(int liftNo) {
        for (Lift lift : lifts) {
            if (lift.getLiftNo() == liftNo) {
                return lift;
            }
        }
        return null;
    }
}

