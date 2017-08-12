package br.edu.ifam.socialdesk.domain.enums;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifam.socialdesk.domain.dto.SexoDTO;

public enum Sexo {

	M("M", "Masculino"), F("F", "Feminino");

	private String codigo;
	private String descricao;

	private Sexo(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Sexo getByCodigo(String sexo) {
		for (Sexo s : values()) {
			if (s.getCodigo().equals(sexo)) {
				return s;
			}
		}
		return null;
	}

	public static List<SexoDTO> toDTO() {
		final List<SexoDTO> lista = new ArrayList<>();
		for (final Sexo sexo : Sexo.values()) {
			lista.add(new SexoDTO(sexo.getCodigo(), sexo.getDescricao()));
		}

		return lista;
	}
}
