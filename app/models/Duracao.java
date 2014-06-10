package models;

public enum Duracao {
	UMA_SEMANA(0, "1 semana"), DUAS_SEMANAS(1, "2 semanas"), TRES_SEMANAS(2, "3 semanas"),
	QUATRO_SEMANAS(3, "4 semanas"), CINCO_SEMANAS(4, "5 semanas"), SEIS_SEMANAS(5, "6 semanas");

	private final Integer tipo;

	private final String descricao;

	private Duracao(Integer tipo, String descricao) {
		this.tipo = tipo;
		this.descricao = descricao;
	}

	public Integer getTipo() {
		return tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return descricao;
	}
}
