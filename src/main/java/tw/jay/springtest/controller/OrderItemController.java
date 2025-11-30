package tw.jay.springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.jay.springtest.service.OrderItemService;

@RestController
@RequestMapping("/api/oredritem")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;
}
