package fun.kmoon.tourism.controller;

import fun.kmoon.tourism.service.ToolAiAssistant;
import fun.kmoon.tourism.service.TravelService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class TravelChatController {

    @Resource
    private ToolAiAssistant toolAiAssistant;

    @Autowired
    private TravelService travelService;

    @GetMapping("/travel/chat")
    public void travelChat(@RequestParam String content) {
        log.debug("travel chat request: {}", content);
        travelService.getTravelGuide(content);
    }

    @RequestMapping("/test")
    public void test(@RequestParam String content) {
        String chat = toolAiAssistant.chat(content);
        System.out.println("test chat response: " + chat);
    }


}
