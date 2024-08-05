package com.bankM.clients.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "t_person", schema = "bank")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @SequenceGenerator(name = "person_seq", schema = "bank", sequenceName = "bank.s_person", allocationSize = 1)
    @Column(name = "id")
    private Integer id;
    private String name;
    private String ci;
    private String gender;
    private Integer age;
    private String address;
    private String phone;
}
