package com.oauth.entity;

import java.util.List;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users2")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 1000)
    private String profilePicture;
    
    @Column(length = 15)
	private String phone;

	private String department;

	private String designation;
	
	@OneToMany(mappedBy = "user" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserProvider> providers;
}
