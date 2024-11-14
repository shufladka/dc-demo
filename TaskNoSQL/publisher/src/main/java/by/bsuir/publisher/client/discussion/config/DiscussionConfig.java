package by.bsuir.publisher.client.discussion.config;

import by.bsuir.publisher.client.discussion.DiscussionClient;
import by.bsuir.publisher.util.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class DiscussionConfig {

    @Autowired
    private ServerConfig serverConfig;

    @Bean
    RestClient restClient() {
        String host = "http://127.0.0.1";
        String url = host + ":" + serverConfig.getPort() + serverConfig.getServlet().getContextPath();
        return RestClient.builder()
                .baseUrl(url)
                .build();
    }

    @Bean
    DiscussionClient discussionClient(RestClient restClient) {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                        .build();

        return httpServiceProxyFactory.createClient(DiscussionClient.class);
    }
}
