package com.telegram.bot.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "m_cities")
@Data
@RequiredArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(exclude = {"id"})
@ToString
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_name")
    private String name;

    @Column(name = "description")
    private String description;
}
