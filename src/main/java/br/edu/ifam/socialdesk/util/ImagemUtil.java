package br.edu.ifam.socialdesk.util;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

public final class ImagemUtil {
	
	private ImagemUtil() {}
	
	
	public static String normalizaBase64(String base64) {
		final String BASE_64_DELIMITADOR = "base64,";
		//TODO criar regex para avaliar se o parâmetro base64 possui o padrão data:image/{mimetype};base64,
		return base64.split(BASE_64_DELIMITADOR)[1];
	}
	
	
	public static String geraImagemReduzida(String imagemBase64, String nomeArquivo) {
		final String BASE_64_DELIMITADOR = "base64,";
		String mimeType = imagemBase64.split(BASE_64_DELIMITADOR)[0];
		String base64String = imagemBase64.split(BASE_64_DELIMITADOR)[1];
		byte[] imagem = Base64.decodeBase64(base64String);

		String imagemReduzida = null;
		try {
			String extensao = nomeArquivo.split("\\.")[1];
			imagemReduzida = UtilDomain.redimensionaImagem(imagem, 100, 100, extensao);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mimeType + BASE_64_DELIMITADOR + imagemReduzida;
	}
	
	

}
