package com.crimsonlogic.creditcardapporval.payload;

import com.crimsonlogic.creditcardapporval.entity.ApplicationForm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
	
	private int messageID;
	
	private String Message;
	
	ApplicationForm applicationForm;
}
