package com.enigmacamp.springbootdatapenduduk.entity.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "regencies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Regency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 2,nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "province_id", referencedColumnName = "id")
    private Province province;
}
