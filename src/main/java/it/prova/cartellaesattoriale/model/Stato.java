package it.prova.cartellaesattoriale.model;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;


public enum Stato {
	
	 CREATA, IN_VERIFICA, CONCLUSA, IN_CONTENZIOSO;


	@JsonCreator
	public static Stato getStatoFromCode(String input) {
		if(StringUtils.isBlank(input))
			return null;
		
		for (Stato statoItem : Stato.values()) {
			if (statoItem.equals(Stato.valueOf(input))) {
				return statoItem;
			}
		}
		return null;
	}

}
