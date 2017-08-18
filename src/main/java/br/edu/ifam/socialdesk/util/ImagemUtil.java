package br.edu.ifam.socialdesk.util; 

public final class ImagemUtil {
	
	private ImagemUtil() {}
	
	
	public static String normalizaBase64(String base64) {
		final String BASE_64_DELIMITADOR = "base64,";
		//TODO criar regex para avaliar se o parâmetro base64 possui o padrão data:image/{mimetype};base64,
		return base64.split(BASE_64_DELIMITADOR)[1];
	}
	
	

}
