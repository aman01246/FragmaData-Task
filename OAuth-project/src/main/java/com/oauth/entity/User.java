package com.oauth.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "users3")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String name;

	    @Column(nullable = false, unique = true)
	    private String email;

	    private String profilePicture;
	    
	    private String phone;

	    private String department;

	    private String designation;

	    private LocalDateTime createdAt;

	    private LocalDateTime updatedAt;

	    private LocalDateTime lastLoginAt;
	    
	    // Runs automatically before INSERT
	    @PrePersist
	    protected void onCreate() {

	        LocalDateTime now = LocalDateTime.now();

	        this.createdAt = now;
	        this.updatedAt = now;
	    }

	    // Runs automatically before UPDATE
	    @PreUpdate
	    protected void onUpdate() {

	        this.updatedAt = LocalDateTime.now();
	    }
	    
	    public boolean isProfileCompleted() {

	        return phone != null && !phone.isBlank()
	                && department != null && !department.isBlank()
	                && designation != null && !designation.isBlank();
	    }
}
