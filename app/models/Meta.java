package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "META")
public class Meta {
	
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "alcancada")
	private boolean alcancada;
	
	@Column(name = "prioridade")
	private Prioridade prioridade;
	
	@Column(name = "data_de_criacao")
	private Date dataDeCriacao;
	
	@Column(name = "data_de_finalizacao")
	private Date dataDeFinalizacao;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAlcancada() {
		return alcancada;
	}

	public void setAlcancada(boolean alcancada) {
		this.alcancada = alcancada;
	}

	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}

	public Date getDataDeCriacao() {
		return dataDeCriacao;
	}

	public void setDataDeCriacao(Date dataDeCriacao) {
		this.dataDeCriacao = dataDeCriacao;
	}

	public Date getDataDeFinalizacao() {
		return dataDeFinalizacao;
	}

	public void setDataDeFinalizacao(Date dataDeFinalizacao) {
		this.dataDeFinalizacao = dataDeFinalizacao;
	}
}
