package com.unear.userservice.user.entity;

import com.unear.userservice.benefit.entity.RouletteResult;
import com.unear.userservice.coupon.entity.UserCoupon;
import com.unear.userservice.place.entity.FavoritePlace;
import com.unear.userservice.stamp.entity.Stamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name= "name")
    private String username;
    private String password;
    private String email;
    private String tel;
    private LocalDateTime birthdate;
    private String gender;
    private String membershipCode;


    @Column(name = "provider")
    @Builder.Default
    private String provider = "EMAIL";

    @Column(name = "provider_id")
    private String providerId;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private boolean isProfileComplete;

    @OneToMany(mappedBy = "user")
    private List<UserCoupon> userCoupons = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<FavoritePlace> favoritePlaces = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Stamp> stamps = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RouletteResult> rouletteResults = new ArrayList<>();

    @Column(name = "barcode_number", unique = true, nullable = false)
    private String barcodeNumber;

}
