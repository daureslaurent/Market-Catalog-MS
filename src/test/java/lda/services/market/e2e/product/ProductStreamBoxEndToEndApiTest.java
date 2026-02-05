package lda.services.market.e2e.product;

import lda.services.market.application.api.rest.product.model.ProductCreateRequest;
import lda.services.market.application.api.rest.product.model.ProductDetalResponse;
import lda.services.market.application.api.rest.product.model.ProductResponse;
import lda.services.market.infra.persistence.write.product.inbox.ProductInboxScheduler;
import lda.services.market.infra.persistence.write.product.inbox.consumer.ProductInboxConsumer;
import lda.services.market.infra.persistence.write.product.outbox.ProductOutboxScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.task.scheduling.enabled=false")
class ProductStreamBoxEndToEndApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductOutboxScheduler outboxScheduler;

    @Autowired
    private ProductInboxScheduler inboxScheduler;

    @Autowired
    private ProductInboxConsumer fakedMessaging;

    @Test
    void givenStreamBox_whenCreateProduct_ThenReadCopy() {
        final var productPost = ProductCreateRequest.builder()
                .name("My Product XY")
                .detail("Detail of the product")
                .quantity(5)
                .build();

        RestTestClient client = RestTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        final var res = client.post().uri("/product")
                .body(productPost)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        final var productResponse = res
                .expectStatus().isOk()
                .returnResult(ProductResponse.class)
                .getResponseBody();

        assertThat(productResponse).isNotNull();
        assert productResponse != null;
        assertThat(productResponse.name()).isEqualTo(productPost.name());
        assertThat(productResponse.id()).isNotNull();

        outboxScheduler.consumeOutbox();
        fakedMessaging.fakeMessaging();
        inboxScheduler.consumeInbox();

        client.get().uri("/product/" + productResponse.id())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDetalResponse.class)
                .value(body -> {
                    assertThat(body).isNotNull();
                    assert body != null;
                    assertThat(body.id()).isEqualTo(productResponse.id());
                    assertThat(body.name()).isEqualTo(productResponse.name());
                    assertThat(body.detail()).isEqualTo(productResponse.detail());
                    assertThat(body.pictureId()).isEqualTo(productResponse.pictureId());
                });
    }

}
