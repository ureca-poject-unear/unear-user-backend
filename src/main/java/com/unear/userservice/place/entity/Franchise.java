package com.unear.userservice.place.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "franchise")
@Getter
@Setter
@NoArgsConstructor
public class Franchise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long franchiseId;

    private String franchiseName;

    private String imageUrl;

    private String vvipPolicy;
    private String vipPolicy;
    private String basicPolicy;

    private String categoryCode;

    @OneToMany(mappedBy = "franchise")
    private List<Place> places = new ArrayList<>();
}


