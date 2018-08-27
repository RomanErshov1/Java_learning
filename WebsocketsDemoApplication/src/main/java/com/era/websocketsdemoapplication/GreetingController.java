package com.era.websocketsdemoapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.Random;

@Controller
public class GreetingController {

    private Random random = new Random();
    private static final int GARBAGE_COUNT = 20;

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/spam")
    public void enableGarbageGenerator(){
        Thread thread = new Thread(()->{
            for (int i = 0; i < GARBAGE_COUNT; i++){
                garbage();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @SendTo("/topic/garbage")
    public void garbage(){
        System.out.println("garbage is generated");
        this.template.convertAndSend("/topic/garbage",
                new Greeting("Garbage #" + random.nextInt()));
    }

}
