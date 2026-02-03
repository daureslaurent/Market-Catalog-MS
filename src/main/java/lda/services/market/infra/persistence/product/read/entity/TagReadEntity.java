//package lda.services.market.infra.persistence.product.read.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.HashSet;
//import java.util.Set;
//import java.util.UUID;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Builder
//@Entity
//@Table(name = "TAG_READ")
//public class TagReadEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID id;
//
//    @Column(nullable = false, unique = true)
//    private String name;
//
//    @ManyToMany(mappedBy = "tags")
//    private Set<ProductReadEntity> products = new HashSet<>();
//
//}
