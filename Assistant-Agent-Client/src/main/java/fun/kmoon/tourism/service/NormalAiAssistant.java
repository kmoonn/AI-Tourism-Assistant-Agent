package fun.kmoon.tourism.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;


public interface NormalAiAssistant {

    String chat(String message);

    String chat(@MemoryId int memoryId, @UserMessage String userMessage);

    @SystemMessage(fromResource = "DoubleCheckHtml.txt")
    String chatByDoubleCheck(@MemoryId int memoryId, @UserMessage String userMessage);

    @SystemMessage(fromResource = "TravelHtmlPrompt.txt")
    String chatByTravelHtmlCreator(@MemoryId int memoryId, @UserMessage String userMessage);

}
