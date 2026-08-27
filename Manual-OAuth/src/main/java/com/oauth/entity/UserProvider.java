package com.oauth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "user_providers2",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"provider", "provider_id"}
        )
    }
)
@Getter
@Setter
public class UserProvider {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String provider;

	    @Column(name = "provider_id", nullable = false)
	    private String providerId;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;
}
