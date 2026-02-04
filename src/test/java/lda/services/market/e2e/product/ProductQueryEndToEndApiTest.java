package lda.services.market.e2e.product;

import lda.services.market.infra.persistence.read.product.entity.ProductReadEntity;
import lda.services.market.infra.persistence.read.product.repository.ProductReadRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductQueryEndToEndApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductReadRepository productReadRepository;

    private UUID currentProductId = null;

    @BeforeEach
    void beforeAll() {
        final var productReadEntity = ProductReadEntity.builder()
                .id(UUID.randomUUID())
                .name("FakedProduct")
                .quantity(50)
                .detail("Detail product")
                .pictureId(UUID.randomUUID().toString())
                .build();
        productReadRepository.deleteAll();
        productReadRepository.save(productReadEntity);

        currentProductId = productReadEntity.getId();
    }

    @AfterEach
    void afterEach() {
        productReadRepository.deleteAll();
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

    @Test
    void givenProductById_WhenFound_thenOk() {
        RestTestClient client = RestTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        client.get().uri("/product/" + currentProductId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(body -> assertThat(body).isNotEmpty());
    }
}
