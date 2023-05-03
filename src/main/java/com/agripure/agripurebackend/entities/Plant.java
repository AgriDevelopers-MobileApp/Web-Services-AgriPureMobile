package com.agripure.agripurebackend.entities;

import com.agripure.agripurebackend.security.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="plants")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="image", nullable = false)
    private String image;
    @Column(name="scientifist_name", nullable = false, length = 100)
    private String scientifist_name;
    @Column(name="variety", nullable = false, length = 100)
    private String variety;
    @Column(name="info_land_type", nullable = false, length = 2500)
    private String info_land_type;
    @Column(name="info_distance_between", nullable = false, length = 2500)
    private String info_distance_between;
    @Column(name="distance_between", nullable = false)
    private String distance_between;
    @Column(name="info_ideal_depth", nullable = false, length = 2500)
    private String info_ideal_depth;
    @Column(name="depth", nullable = false)
    private String depth;
    @Column(name="info_fert_fumig", nullable = false, length = 2500)
    private String info_fert_fumig;
    @Column(name="intervale_fert", nullable = false)
    private Integer intervale_fert;
    @Column(name="intervale_fumig", nullable = false)
    private Integer intervale_fumig;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_plants",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "plant_id"))
    private List<User> users;
}
