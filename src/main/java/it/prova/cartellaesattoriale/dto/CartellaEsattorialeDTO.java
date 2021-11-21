package it.prova.cartellaesattoriale.dto;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.cartellaesattoriale.model.CartellaEsattoriale;
import it.prova.cartellaesattoriale.model.StatoCartella;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartellaEsattorialeDTO {

	private Long id;

	@NotBlank(message = "{descrizione.notblank}")
	@Size(min = 4, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String descrizione;

	@NotNull(message = "{importo.notnull}")
	@Min(1)
	private Integer importo;

	private StatoCartella stato;

	@JsonIgnoreProperties(value = { "cartelle" })
	@NotNull(message = "{contribuente.notnull}")
	private ContribuenteDTO contribuente;

	public CartellaEsattorialeDTO(Long id,
			@NotBlank(message = "{descrizione.notblank}") @Size(min = 4, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String descrizione,
			@NotNull(message = "{importo.notnull}") @Min(1) Integer importo,
			@NotNull(message = "{contribuente.notnull}") ContribuenteDTO contribuente) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.importo = importo;
		this.contribuente = contribuente;
	}

	public CartellaEsattorialeDTO() {
		super();
	}

	public CartellaEsattorialeDTO(Long id,
			@NotBlank(message = "{descrizione.notblank}") @Size(min = 4, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String descrizione,
			@NotNull(message = "{importo.notnull}") @Min(1) Integer importo) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.importo = importo;
	}

	public CartellaEsattorialeDTO(Long id,
			@NotBlank(message = "{descrizione.notblank}") @Size(min = 4, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String descrizione,
			@NotNull(message = "{importo.notnull}") @Min(1) Integer importo,
			@NotNull(message = "{stato.notblank}") StatoCartella stato,
			@NotNull(message = "{contribuente.notnull}") ContribuenteDTO contribuente) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.importo = importo;
		this.stato = stato;
		this.contribuente = contribuente;
	}

	public CartellaEsattorialeDTO(Long id,
			@NotBlank(message = "{descrizione.notblank}") @Size(min = 4, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String descrizione,
			@NotNull(message = "{importo.notnull}") @Min(1) Integer importo,
			@NotNull(message = "{stato.notblank}") StatoCartella stato) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.importo = importo;
		this.stato = stato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Integer getImporto() {
		return importo;
	}

	public void setImporto(Integer importo) {
		this.importo = importo;
	}

	public ContribuenteDTO getContribuente() {
		return contribuente;
	}

	public void setContribuente(ContribuenteDTO contribuente) {
		this.contribuente = contribuente;
	}

	public CartellaEsattoriale buildCartellaEsattorialeModel() {
		return new CartellaEsattoriale(this.id, this.descrizione, this.importo, this.stato,
				this.contribuente.buildContribuenteModel());
	}

	public static CartellaEsattorialeDTO buildCartellaEsattorialeDTOFromModel(
			CartellaEsattoriale cartellaEsattorialeModel, boolean includeContribuenti) {
		CartellaEsattorialeDTO result = new CartellaEsattorialeDTO(cartellaEsattorialeModel.getId(),
				cartellaEsattorialeModel.getDescrizione(), cartellaEsattorialeModel.getImporto(),
				cartellaEsattorialeModel.getStatoCartella());

		if (includeContribuenti)
			result.setContribuente(
					ContribuenteDTO.buildContribuenteDTOFromModel(cartellaEsattorialeModel.getContribuente(), false));

		return result;
	}
	
	public static List<CartellaEsattorialeDTO> createCartellaEsattorialeDTOListFromModelList(List<CartellaEsattoriale> modelListInput, boolean includeContribuenti) {
		return modelListInput.stream().map(filmEntity -> {
			return CartellaEsattorialeDTO.buildCartellaEsattorialeDTOFromModel(filmEntity, includeContribuenti);
		}).collect(Collectors.toList());
	}
	
	public static Set<CartellaEsattorialeDTO> createCartellaEsattorialeDTOSetFromModelSet(Set<CartellaEsattoriale> modelListInput, boolean includeContribuenti) {
		return modelListInput.stream().map(cartellaEntity -> {
			return CartellaEsattorialeDTO.buildCartellaEsattorialeDTOFromModel(cartellaEntity, includeContribuenti);
		}).collect(Collectors.toSet());
	}
}
