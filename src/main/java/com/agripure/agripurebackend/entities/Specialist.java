package com.agripure.agripurebackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="specialists")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Specialist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contact_email", nullable = false, length = 200)
    private String contact_email;

    @Column(name = "telephone_number", nullable = true, length = 30)
    private String telephone_number;

    @Column(name = "cellphone_number", nullable = true,length = 30)
    private String cellphone_number;

    @Column(name = "whatsapp_number", nullable = true, length = 30)
    private String whatsapp_number;

    @JsonIgnore
    @OneToMany(mappedBy = "specialist")
    private List<Query> users;
}
