package com.omnirio.assignment.accountservice.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.omnirio.assignment.accountservice.domain.types.AccountType;

import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Account {

	@Id
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
	private String accountId;
	private AccountType accType;
	
	@CreatedDate
	private LocalDate openDate;
	
	@Column(nullable = false)
	private String customerId;
	
	private String customerName;
	private String branch;
	private boolean isMinor;
	
	@PrePersist
	public void setIsMinor() {
		
	}
}