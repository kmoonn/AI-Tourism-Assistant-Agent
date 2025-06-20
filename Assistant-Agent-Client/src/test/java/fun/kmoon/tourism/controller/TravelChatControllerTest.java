package fun.kmoon.tourism.controller;

import fun.kmoon.tourism.service.ToolAiAssistant;
import fun.kmoon.tourism.service.TravelService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import jakarta.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TravelChatController.class)
@Import(TravelChatControllerTest.MockServiceConfig.class)  // 手动导入 mock 的配置
public class TravelChatControllerTest {

    @Resource
    private MockMvc mockMvc;

    @Resource
    private ToolAiAssistant toolAiAssistant;

    @Resource
    private TravelService travelService;

    @Test
    public void testToolChat() throws Exception {
        Mockito.when(toolAiAssistant.chat("你好")).thenReturn("你好，我是助手");

        mockMvc.perform(get("/test").param("content", "你好"))
                .andExpect(status().isOk())
                .andExpect(content().string("你好，我是助手"));
    }

    @TestConfiguration
    static class MockServiceConfig {
        @Bean
        public ToolAiAssistant toolAiAssistant() {
            return Mockito.mock(ToolAiAssistant.class);
        }

        @Bean
        public TravelService travelService() {
            return Mockito.mock(TravelService.class);
        }
    }
}
