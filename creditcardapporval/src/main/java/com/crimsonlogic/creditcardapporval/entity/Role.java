package com.crimsonlogic.creditcardapporval.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="role")
public class Role {
	
	@Id
	@Column(name = "role_id")
	private String roleID;
	
	@Column(name="role_type")
	private String roleType;
	
}
