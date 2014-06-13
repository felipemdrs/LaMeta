package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import models.exceptions.MetaInvalidaException;
import play.data.validation.Constraints;

@Entity
@Table(name = "Meta")
public class Meta {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "descricao", length = 80)
	@Constraints.Required
	private String descricao;

	@Column(name = "alcancada")
	private boolean alcancada;

	@Column(name = "prioridade")
	@Constraints.Required
	private Prioridade prioridade;

	@Column(name = "duracao")
	@Constraints.Required
	private Duracao duracao;

	@Column(name = "data_de_criacao")
	private Date dataDeCriacao;

	public Meta() {

	}

	public Meta(String descricao, Prioridade prioridade, Duracao duracao)
			throws MetaInvalidaException {
		setDescricao(descricao);
		setPrioridade(prioridade);
		setDuracao(duracao);
		setDataDeCriacao(new Date());
	}

	public long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) throws MetaInvalidaException {
		if (descricao == null)
			throw new MetaInvalidaException("Parametro nulo");
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

	public void setPrioridade(Prioridade prioridade)
			throws MetaInvalidaException {
		if (prioridade == null)
			throw new MetaInvalidaException("Parametro nulo");
		this.prioridade = prioridade;
	}

	public Duracao getDuracao() {
		return duracao;
	}

	public void setDuracao(Duracao duracao) throws MetaInvalidaException {

		if (duracao == null)
			throw new MetaInvalidaException("Parametro nulo");

		this.duracao = duracao;
	}

	public Date getDataDeCriacao() {
		return dataDeCriacao;
	}

	public void setDataDeCriacao(Date dataDeCriacao)
			throws MetaInvalidaException {

		if (dataDeCriacao == null)
			throw new MetaInvalidaException("Parametro nulo");

		this.dataDeCriacao = dataDeCriacao;
	}
}
