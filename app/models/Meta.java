package models;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import models.exceptions.MetaInvalidaException;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Required;

@Entity
@Table(name = "Meta")
public class Meta {

	@Transient
	private final int MAX_DE_DIAS_DA_META = 42;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "descricao")
	@Constraints.Required
	private String descricao;

	@Column(name = "alcancada")
	private boolean alcancada;

	@Column(name = "prioridade")
	@Constraints.Required
	public Prioridade prioridade;

	@Column(name = "data_de_finalizacao")
	@Constraints.Required
	public Date dataDeFinalizacao;

	public Meta() {

	}

	public Meta(String descricao, Prioridade prioridade,
			Date dataDeFinalizacao) throws MetaInvalidaException {
		setDescricao(descricao);
		setPrioridade(prioridade);
		setDataDeFinalizacao(dataDeFinalizacao);
	}

	public int getId() {
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

	public Date getDataDeFinalizacao() {
		return dataDeFinalizacao;
	}

	public void setDataDeFinalizacao(Date dataDeFinalizacao)
			throws MetaInvalidaException {

		if (dataDeFinalizacao == null)
			throw new MetaInvalidaException("Parametro nulo");
		if (dataDeFinalizacao.compareTo(getDataMaximaDaMeta()) > 0)
			throw new MetaInvalidaException("Data inválida");

		dataDeFinalizacao = normalizarData(dataDeFinalizacao);
		this.dataDeFinalizacao = dataDeFinalizacao;
	}

	public Date getDataMaximaDaMeta() {
		Calendar maxDate = Calendar.getInstance();
		maxDate.add(Calendar.DATE, MAX_DE_DIAS_DA_META);

		return maxDate.getTime();
	}

	private Date normalizarData(Date date) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}
}
