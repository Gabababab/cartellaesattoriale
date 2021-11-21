package it.prova.cartellaesattoriale.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.cartellaesattoriale.model.Contribuente;

//DTO esclusivamente per le operazioni di business
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContribuenteBusinessDTO {

	private Long id;

	//Variabili ad uso esclusivo delle operazioni di business
	private boolean daAttenzionare;

	private Integer importoCartelle;

	private Integer conclusoEPagato;

	private Integer inContenzioso;

	//variabili binding
	@NotBlank(message = "{nome.notblank}")
	private String nome;

	@NotBlank(message = "{cognome.notblank}")
	private String cognome;

	@NotBlank(message = "{codiceFiscale.notblank}")
	private String codiceFiscale;

	@NotNull(message = "{dataDiNascita.notnull}")
	private Date dataDiNascita;

	@NotBlank(message = "{indirizzo.notblank}")
	private String indirizzo;

	@JsonIgnoreProperties(value = { "contribuente" })
	private Set<CartellaEsattorialeDTO> cartelle = new HashSet<CartellaEsattorialeDTO>(0);

	public ContribuenteBusinessDTO() {
		super();
	}

	public ContribuenteBusinessDTO(Long id, @NotBlank(message = "{nome.notblank}") String nome,
			@NotBlank(message = "{cognome.notblank}") String cognome,
			@NotBlank(message = "{codiceFiscale.notblank}") String codiceFiscale,
			@NotNull(message = "{dataDiNascita.notnull}") Date dataDiNascita,
			@NotBlank(message = "{indirizzo.notblank}") String indirizzo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.dataDiNascita = dataDiNascita;
		this.indirizzo = indirizzo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isDaAttenzionare() {
		return daAttenzionare;
	}

	public void setDaAttenzionare(boolean daAttenzionare) {
		this.daAttenzionare = daAttenzionare;
	}

	public Integer getImportoCartelle() {
		return importoCartelle;
	}

	public void setImportoCartelle(Integer importoCartelle) {
		this.importoCartelle = importoCartelle;
	}

	public Integer getConclusoEPagato() {
		return conclusoEPagato;
	}

	public void setConclusoEPagato(Integer conclusoEPagato) {
		this.conclusoEPagato = conclusoEPagato;
	}

	public Integer getInContenzioso() {
		return inContenzioso;
	}

	public void setInContenzioso(Integer inContenzioso) {
		this.inContenzioso = inContenzioso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Date getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public Set<CartellaEsattorialeDTO> getCartelle() {
		return cartelle;
	}

	public void setCartelle(Set<CartellaEsattorialeDTO> cartelle) {
		this.cartelle = cartelle;
	}

	public Contribuente buildContribuenteModel() {
		return new Contribuente(this.id, this.nome, this.cognome, this.codiceFiscale, this.dataDiNascita,
				this.indirizzo);
	}

	public static ContribuenteBusinessDTO buildContribuenteBusinessDTOFromModel(Contribuente contribuenteModel,
			boolean includeCartelle) {
		ContribuenteBusinessDTO result = new ContribuenteBusinessDTO(contribuenteModel.getId(),
				contribuenteModel.getNome(), contribuenteModel.getCognome(), contribuenteModel.getCodiceFiscale(),
				contribuenteModel.getDataDiNascita(), contribuenteModel.getIndirizzo());
		if (includeCartelle)
			result.setCartelle(CartellaEsattorialeDTO
					.createCartellaEsattorialeDTOSetFromModelSet(contribuenteModel.getCartelle(), false));
		return result;
	}

	public static List<ContribuenteBusinessDTO> createContribuenteBusinessDTOListFromModelList(
			List<Contribuente> modelListInput, boolean includeCartelle) {
		return modelListInput.stream().map(contribuenteEntity -> {
			ContribuenteBusinessDTO result = ContribuenteBusinessDTO
					.buildContribuenteBusinessDTOFromModel(contribuenteEntity, includeCartelle);
			if (includeCartelle)
				result.setCartelle(CartellaEsattorialeDTO
						.createCartellaEsattorialeDTOSetFromModelSet(contribuenteEntity.getCartelle(), false));
			return result;
		}).collect(Collectors.toList());
	}
}
