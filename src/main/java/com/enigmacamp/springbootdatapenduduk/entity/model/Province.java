package com.enigmacamp.springbootdatapenduduk.entity.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "provinces")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 2,nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

}
