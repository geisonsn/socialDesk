package br.edu.ifam.socialdesk.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Data {

	private Data() {
	}

	public static enum DatePattern {
		DD_MM_YYYY_FORMATTED("dd/MM/yyyy"),
		/**
		 * Data formatada no padão dd/MM/yyyy 'às' HH:mm:ss
		 */
		DD_MM_YYYY_HH_mm_ss_FORMATTED1("dd/MM/yyyy 'às' HH:mm:ss"),
		/**
		 * Data formatada no padrão dd/MM/yyyy HH:mm:ss
		 */
		DD_MM_YYYY_HH_mm_ss_FORMATTED2("dd/MM/yyyy HH:mm:ss"),
		/**
		 * Data formatada no padão dd/MM/yyyy HH:mm
		 */
		DD_MM_YYYY_HH_mm_ss_FORMATTED3("dd/MM/yyyy HH:mm"), HH_mm_ss_FORMATTED("HH:mm:ss"), DD_MM_YYYY_UNFORMATTED("ddMMyyyy");

		private String pattern;

		private DatePattern(String pattern) {
			this.pattern = pattern;
		}

		public String getPattern() {
			return pattern;
		}
	}

	/**
	 * Converte uma data em formato String para o formato Date
	 * 
	 * @param date
	 * @param pattern
	 * @return data convertida em date
	 * @throws caso
	 *             o formato da data seja diferente do padrão informado
	 */
	public static Date parse(String date, DatePattern pattern) {
		DateFormat format = getDateFormat(date, pattern);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException("Formato de data inválido para o padrão informado");
		}
	}

	/**
	 * Formata um Date com base no {@code pattern} informado
	 * 
	 * @param data
	 * @param pattern
	 * @return uma string contendo a data formatada
	 */
	public static String format(Date data, DatePattern pattern) {
		DateFormat format = getDateFormat(data, pattern);
		return format.format(data);
	}

	private static DateFormat getDateFormat(Object data, DatePattern pattern) {
		return buildDateFormat(data, pattern);
	}

	private static DateFormat buildDateFormat(Object data, DatePattern pattern) {
		if (UtilDomain.isNull(data) || pattern == null) {
			throw new IllegalArgumentException("Parâmetros informados incorretamente");
		}

		DateFormat format = new SimpleDateFormat(pattern.getPattern());
		return format;
	}
}