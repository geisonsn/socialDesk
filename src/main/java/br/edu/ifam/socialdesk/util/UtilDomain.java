package br.edu.ifam.socialdesk.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Id;

import br.gov.frameworkdemoiselle.util.Reflections;

public class UtilDomain {

	public static final Integer ZERO = Integer.valueOf(0);

	private UtilDomain() {
		super();
	}

	public static boolean isNull(Number valor) {
		return ((valor == null) || (ZERO.equals(valor)));
	}

	public static boolean isNotNull(Number valor) {
		return !isNull(valor);
	}

	public static boolean isNull(String valor) {
		return ((valor == null) || (valor.trim().isEmpty()));
	}

	public static boolean isNotNull(String valor) {
		return !isNull(valor);
	}

	public static boolean isNull(Character valor) {
		return ((valor == null) || (Character.isWhitespace(valor.charValue())));
	}

	public static boolean isNotNull(Character valor) {
		return !isNull(valor);
	}

	public static boolean isNull(Date valor) {
		return valor == null;
	}

	public static boolean isNotNull(Date valor) {
		return !isNull(valor);
	}

	public static boolean isNull(Object valor) {
		return valor == null;
	}

	public static String gerarString(String str, int vezes) {

		if (str == null) {
			throw new IllegalArgumentException("Parametro 'str' e obrigatorio");
		}

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < vezes; i++) {
			builder.append(str);
		}

		return builder.toString();
	}

	public static String gerarString(char ch, int vezes) {
		return gerarString(String.valueOf(ch), vezes);
	}

	public static String normalizeWhitespace(String texto) {
		if (texto == null) {
			return null;
		}

		return texto.trim().replaceAll("( +)", " ");
	}

	public static String removeNaoNumericos(String texto) {
		if (isNull(texto)) {
			return null;
		}
		return texto.replaceAll("[^\\d]", "");
	}

	public static boolean isNull(Collection<?> values) {
		return values == null || values.isEmpty();
	}

	public static boolean isNotNull(Collection<?> values) {
		return !isNull(values);
	}

	public static boolean isNullCPF(String cpf) {
		return isNull(cpf) || "___.___.___-__".equals(cpf);
	}

	/**
	 * recupera o campo que possui a anotacao @Id, porem nao aplica
	 * recursividade para recuperar atributos
	 * 
	 * @param obj
	 * @return
	 */
	public static Field getAttributeId(Object obj) {
		Field[] declaredFields = obj.getClass().getDeclaredFields();

		for (Field field : declaredFields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Id.class)) {
				return field;
			}
		}
		return null;
	}

	public static Object getFieldValue(String name, Object obj) {
		return Reflections.getFieldValue(getFieldByName(obj, name), obj);
	}

	public static Field getFieldByName(Object obj, String name) {
		Class<? extends Object> clazz = obj.getClass();
		return getFieldByName(clazz, name);
	}

	public static Field getFieldByName(Class<?> clazz, String name) {
		Field[] fieldArray = Reflections.getNonStaticDeclaredFields(clazz);
		for (Field field : fieldArray) {
			if (field.getName().equals(name)) {
				return field;
			}
		}

		throw new IllegalArgumentException("O atributo " + name + " nÃ£o foi encontrado na classe " + clazz.getName());
	}

	/**
	 * recupera o valor do campo que possui a anotacao @Id, porem nao aplica
	 * recursividade para recuperar atributos
	 * 
	 * @param obj
	 * @return
	 */
	public static Object getIdValue(Object obj) {
		try {
			return getAttributeId(obj).get(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * public static <S, D> List<D> mapList(List<S> lista, Class<D>
	 * destinationType, PropertyMap<S, D> propertyMap) {
	 * 
	 * ArrayList<D> listaDTO = new ArrayList<>();
	 * 
	 * if (!lista.isEmpty()) { for (S source : lista) { listaDTO.add(map(source,
	 * destinationType, propertyMap)); } }
	 * 
	 * return listaDTO; }
	 * 
	 * public static <S, D> List<D> mapList(Collection<S> lista, Class<D>
	 * destinationType, PropertyMap<S, D> propertyMap) { if (lista == null)
	 * return null; return mapList(new ArrayList<>(lista), destinationType,
	 * propertyMap); }
	 * 
	 * public static <S, D> List<D> mapList(Collection<S> lista, Class<D>
	 * destinationType) { if (lista == null) return null; return mapList(new
	 * ArrayList<>(lista), destinationType, null); }
	 * 
	 * public static <S, D> List<D> mapList(List<S> lista, Class<D>
	 * destinationType) { return mapList(lista, destinationType, null); }
	 * 
	 * public static <S, D> D map(S source, Class<D> destinationType,
	 * PropertyMap<S, D> propertyMap) { if (source == null) { return null; }
	 * 
	 * ModelMapper mapper = new ModelMapper(); if (propertyMap != null) {
	 * mapper.addMappings(propertyMap); }
	 * 
	 * return mapper.map(source, destinationType); }
	 * 
	 * public static <S, D> D map(S source, Class<D> destinationType) { return
	 * map(source, destinationType, null); }
	 */
}
