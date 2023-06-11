package com.example.cybersociety.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Articles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @Column(length = 10000)
    private String content;
    private LocalDate localDate;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    private Media articlePhoto;
}
