package com.unear.userservice.place.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "franchise")
@Getter
@Setter
public class Franchise {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long franchiseId;

    private String franchiseName;
    private String imageUrl;

    @OneToMany(mappedBy = "franchise")
    private List<Place> places = new ArrayList<>();
}

