package com.atlantis.splitwise.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

//@Data
//@Entity
public class Group {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    Long groupId;

    @Column
    String name;

    @ManyToMany
    @JsonManagedReference
    Set<User> members;
}
