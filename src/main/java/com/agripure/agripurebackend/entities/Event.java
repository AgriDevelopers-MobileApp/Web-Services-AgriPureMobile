package com.agripure.agripurebackend.entities;

import com.agripure.agripurebackend.security.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "event")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@With
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date", nullable = false)
    private String date;
    @Column(name = "title", nullable = false, length = 500)
    private String title;
}