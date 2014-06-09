package models;

public enum Prioridade {

	ALTA(0, "Alta"), MEDIA(1, "Média"), BAIXA(2, "Baixa");

	private final Integer tipo;

	private final String descricao;

	private Prioridade(Integer tipo, String descricao) {
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
