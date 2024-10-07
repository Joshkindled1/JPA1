package org.example.jpa.controller;

import jakarta.validation.Valid;
import org.example.jpa.Exception.ResourceNotFoundException;
import org.example.jpa.model.Customer;
import org.example.jpa.model.Feedback;
import org.example.jpa.repository.CustomerRepository;
import org.example.jpa.repository.FeedbackRepository;
import org.example.jpa.service.CustomerService;
import org.example.jpa.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<Object> allFeedback(){
        return new ResponseEntity<>(feedbackService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> saveFeedback(@PathVariable("id") Long customerId, @RequestBody @Valid Feedback feedback){

        // TODO Check if the customer exists
      Feedback checkFeedback = customerService.findById(customerId).map(_customer->{
            Feedback _feedback = new Feedback(_customer,feedback.getDescription());
            return feedbackService.save(_feedback);
        }).orElseThrow(()->new ResourceNotFoundException());



        return new ResponseEntity<>(checkFeedback,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateFeedback(@PathVariable("id") Long feedbackId, @RequestBody @Valid Feedback feedback ){
        Feedback checkFeedback = feedbackService.findById(feedbackId).map(_feedback->{

            _feedback.setDescription(feedback.getDescription());
            return feedbackService.save(_feedback);

        }).orElseThrow(()->new ResourceNotFoundException());

        return new ResponseEntity<>(checkFeedback, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFeedback(@PathVariable("id") Long feedbackId){
        Feedback checkFeedback = feedbackService.findById(feedbackId).map(_feedback->{

            feedbackService.deleteById(_feedback.getId());
            return _feedback;

        }).orElseThrow(()->new ResourceNotFoundException());
//        if(checkFeedback.isEmpty())
//            return new ResponseEntity<>("Feedback Not Deleted.", HttpStatus.BAD_REQUEST);

        String Response = String.format("%s Feedback Deleted", checkFeedback.getDescription());

        return new ResponseEntity<>(checkFeedback, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Object> countFeedback(){
        long count = feedbackService.count();
        if(count <=0)
            throw new ResourceNotFoundException();

            Map<String, Object> totalFeedback = new HashMap<>();
            totalFeedback.put("total",count);
            return new ResponseEntity<>(totalFeedback, HttpStatus.OK);

    }

    //Delete the feedback that belongs to a customer
    //1. Find the customer first OR you find the feedback first
    //2. Delete the feedback

}
