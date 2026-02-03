package lda.services.market.application.api.rest.product;

import lda.services.market.application.api.rest.product.model.ProductCreateRequest;
import lda.services.market.application.api.rest.product.model.ProductResponse;
import lda.services.market.infra.persistence.product.read.entity.ProductReadEntity;
import lda.services.market.infra.persistence.product.read.repository.ProductReadRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductEndToEndApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductReadRepository productReadRepository;

    @BeforeEach
    void beforeAll() {
        productReadRepository.deleteAll();
        productReadRepository.save(ProductReadEntity.builder()
                .id(UUID.randomUUID())
                .name("FakedProduct")
                .quantity(50)
                .build()
        );
    }

    @AfterEach
    void afterEach() {
        productReadRepository.deleteAll();
    }

    @Test
    void givenNewProduct_WhenOK_ThenOk() {
        final var productName = "My Product XY";
        final var productDetail = "Detail of the product";

        final var productPost = ProductCreateRequest.builder()
                .name(productName)
                .detail(productDetail)
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

        final var body = res
                .expectStatus().isOk()
                .returnResult(ProductResponse.class)
                .getResponseBody();

        assertThat(body).isNotNull();
        assert body != null;
        assertThat(body.name()).isEqualTo(productName);
        assertThat(body.pictureId()).isNull();
    }

    @Test
    void givenProductApi_WhenGet_thenExpectedOk() {
        RestTestClient client = RestTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        client.get().uri("/product")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content").isArray()
                .jsonPath("$.content").isNotEmpty()
                .jsonPath("$.totalElements").value(total ->
                        assertThat(((Number) total).longValue()).isGreaterThan(0)
                );
    }

    @Test
    void givenProductApi_WhenNotFound_thenExpectedNotFound() {
        RestTestClient client = RestTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        client.get().uri("/product/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }
}
