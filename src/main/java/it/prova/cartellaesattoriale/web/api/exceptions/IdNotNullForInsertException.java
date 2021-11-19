package it.prova.cartellaesattoriale.web.api.exceptions;

public class IdNotNullForInsertException  extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public IdNotNullForInsertException(String message) {
		super(message);
	}
}
