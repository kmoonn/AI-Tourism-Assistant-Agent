package fun.kmoon.tourism.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class TravelService {

    private static final String OUTPUT_HTML_FILE = "travel.html";

    @Resource
    private ToolAiAssistant toolAiAssistant;

    @Resource
    private NormalAiAssistant normalAiAssistant;


    public void getTravelGuide(String require) {
        //1.旅游攻略生成
        String response = toolAiAssistant.chatByTravelGuide(1, require);
        log.info("旅行规划表响应：{}", response);
        //2.html美化
        log.info("开始生成HTML代码...");

        String htmlCode = normalAiAssistant.chatByTravelHtmlCreator(2, response);
        log.info("生成的HTML代码：{}", htmlCode);
        //清理返回的非法格式
        htmlCode=clearHtmlCode(htmlCode);
        String fileName = OUTPUT_HTML_FILE;
        try {
            Path path = Paths.get(fileName);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            Files.write(path, htmlCode.getBytes());
            log.info("HTML代码已保存到文件：{}", fileName);
        } catch (IOException e) {
            log.error("保存HTML代码时出错：{}", e.getMessage());
        }
    }

    private String clearHtmlCode(String htmlCode) {
        if (htmlCode.startsWith("```html")) {
            htmlCode = htmlCode.substring(7);
        }
        if (htmlCode.endsWith("```")) {
            htmlCode = htmlCode.substring(0, htmlCode.length() - 3);
        }
        return htmlCode;
    }

}


