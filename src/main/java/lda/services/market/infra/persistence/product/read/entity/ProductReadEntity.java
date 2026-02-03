package lda.services.market.infra.persistence.product.read.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "PRODUCT_READ")
public class ProductReadEntity {

    @Id
    private UUID id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String detail;

    @Column(nullable = false)
    private Integer quantity;

    private String pictureId;

//    @ManyToMany
//    @JoinTable(
//            name = "product_tags",
//            joinColumns = @JoinColumn(name = "product_id"),
//            inverseJoinColumns = @JoinColumn(name = "tag_id")
//    )
//    private Set<TagReadEntity> tags = new HashSet<>();
}
