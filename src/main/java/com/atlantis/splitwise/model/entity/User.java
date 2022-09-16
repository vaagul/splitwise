package com.atlantis.splitwise.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    Long id;

    @Column
    String name;

//    @ManyToMany
//    @JoinTable(
//        joinColumns = @JoinColumn(name = "group_id"),
//        inverseJoinColumns = @JoinColumn(name = "user_id")
//    )
//    @JsonBackReference
//    Set<Group> groups;
}
