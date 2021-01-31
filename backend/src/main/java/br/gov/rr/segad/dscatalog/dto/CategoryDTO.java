package br.gov.rr.segad.dscatalog.dto;

import java.io.Serializable;
import java.time.Instant;

import br.gov.rr.segad.dscatalog.entities.Category;

public class CategoryDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private Instant createdAt;
	
	public CategoryDTO() {
		
	}
	
	public CategoryDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	// Construtor que populará CategoryDTO, ao passar como parametro a entidade (Category)
	public CategoryDTO(Category entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.createdAt = entity.getCreatedAt();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
	
	
}
