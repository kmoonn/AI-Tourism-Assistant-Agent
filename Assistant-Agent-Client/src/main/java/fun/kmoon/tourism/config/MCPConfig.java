package fun.kmoon.tourism.config;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.HttpMcpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
public class MCPConfig {

    @Value("${mcp.gaoDe.url:}")
    private String gaoDeUrl;

    /**
     * 自建天气 SSE MCP服务
     * @return
     */
    @Bean
    public McpClient mcpClientWeather() {
        McpTransport transport = new HttpMcpTransport.Builder()
                .sseUrl("http://localhost:9000/sse")
                .timeout(Duration.ofMinutes(5))
                .logRequests(true) // if you want to see the traffic in the log
                .logResponses(true)
                .build();
        return new DefaultMcpClient.Builder()
                .transport(transport)
                .build();
    }


    /**
     * 高德地图 SSE MCP服务
     * @return
     */
    @Bean
    public McpClient mcpClientGaoDeMap() {
        McpTransport transport = new HttpMcpTransport.Builder()
                .sseUrl(gaoDeUrl)
                .timeout(Duration.ofMinutes(5))
                //.logRequests(true)
                //.logResponses(true)
                .build();
        return new DefaultMcpClient.Builder()
                .transport(transport)
                .build();
    }



    /**
     * 将 MCP 客户端注入 McpToolProvider
     * @param clients
     * @return
     */
    @Bean
    public McpToolProvider mcpToolProvider(List<McpClient> clients) {
        return McpToolProvider.builder()
                .mcpClients(clients)
                .build();
    }


}
