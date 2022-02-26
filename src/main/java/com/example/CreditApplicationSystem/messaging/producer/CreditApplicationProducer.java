package com.example.CreditApplicationSystem.messaging.producer;

import com.example.CreditApplicationSystem.config.RabbitMQConfig;
import com.example.CreditApplicationSystem.model.entity.CreditApplication;
import com.example.CreditApplicationSystem.service.CreditApplicationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Service
@RestController
@RequestMapping("/messaging/publishCreditApplication")
public class CreditApplicationProducer {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private CreditApplicationService creditApplicationService;

    @PostMapping("/creditApplication/{id}")
    public void sendCreditAppliation(@PathVariable int id) {
        CreditApplication creditApplication = creditApplicationService.getCreditApplicationById(id);
        template.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, creditApplication);

    }
}
