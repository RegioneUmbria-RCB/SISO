package it.umbriadigitale.soclav.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TEST")
public class Test {

	 
    //@GeneratedValue(strategy = GenerationType.AUTO)
	 
	@Id
	@SequenceGenerator(name="TEST_ID_GENERATOR", sequenceName="SQ_ID", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TEST_ID_GENERATOR")
    private Long id;
	
	@Column(name="NOME")
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
