package lda.services.market.e2e.product;

import lda.services.market.application.api.rest.product.model.ProductChangeQuantityRequest;
import lda.services.market.application.api.rest.product.model.ProductCreateRequest;
import lda.services.market.application.api.rest.product.model.ProductDetalResponse;
import lda.services.market.application.api.rest.product.model.ProductResponse;
import lda.services.market.infra.persistence.read.product.entity.ProductReadEntity;
import lda.services.market.infra.persistence.read.product.repository.ProductReadRepository;
import lda.services.market.infra.persistence.write.product.entity.ProductWriteEntity;
import lda.services.market.infra.persistence.write.product.repository.ProductWriteRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.UUID;
import java.util.random.RandomGenerator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductCommandEndToEndApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductReadRepository productReadRepository;

    @Autowired
    private ProductWriteRepository productWriteRepository;

    private UUID currentProductId = null;

    @BeforeEach
    void beforeAll() {
        final var name = "FakedProduct";
        final var quantity = 50;

        final var productWriteEntity = ProductWriteEntity.builder()
                .name(name)
                .quantity(quantity)
                .build();

        productWriteRepository.deleteAll();
        final var writeSaved = productWriteRepository.save(productWriteEntity);

        final var productReadEntity = ProductReadEntity.builder()
                .id(writeSaved.getId())
                .name(writeSaved.getName())
                .quantity(writeSaved.getQuantity())
                .detail(writeSaved.getDetail())
                .pictureId(writeSaved.getPictureId())
                .build();
        productReadRepository.deleteAll();
        productReadRepository.save(productReadEntity);

        currentProductId = productReadEntity.getId();
    }

    @AfterEach
    void afterEach() {
        productReadRepository.deleteAll();
        productWriteRepository.deleteAll();
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
    void givenUpdateQuantity_WhenOK_ThenOk() {
        final var change = RandomGenerator.getDefault().nextInt(100);
        final var changeQuantityRequest = ProductChangeQuantityRequest.builder()
                .quantity(change)
                .build();

        RestTestClient client = RestTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
        client.put().uri("/product/" + currentProductId + "/quantity")
                .body(changeQuantityRequest)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDetalResponse.class)
                .value(product -> {
                    assertThat(product).isNotNull();
                    assert product != null;
                    assertThat(product.quantity()).isEqualTo(change);
                });
    }

    @Test
    void givenUpdateQuantity_WhenNotFound_ThenNotFound() {
        final var changeQuantityRequest = ProductChangeQuantityRequest.builder()
                .quantity(10)
                .build();

        RestTestClient client = RestTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
        client.put().uri("/product/" + UUID.randomUUID() + "/quantity")
                .body(changeQuantityRequest)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }


}
