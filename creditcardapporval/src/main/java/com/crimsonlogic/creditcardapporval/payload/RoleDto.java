package com.crimsonlogic.creditcardapporval.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RoleDto {

	private String roleID;
	
	private String roleType;
}
