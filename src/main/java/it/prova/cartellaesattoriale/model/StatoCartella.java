package it.prova.cartellaesattoriale.model;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;


public enum StatoCartella {
	
	 CREATA, IN_VERIFICA, CONCLUSA, IN_CONTENZIOSO;


	@JsonCreator
	public static StatoCartella getStatoFromCode(String input) {
		if(StringUtils.isBlank(input))
			return null;
		
		for (StatoCartella statoItem : StatoCartella.values()) {
			if (statoItem.equals(StatoCartella.valueOf(input))) {
				return statoItem;
			}
		}
		return null;
	}

}
