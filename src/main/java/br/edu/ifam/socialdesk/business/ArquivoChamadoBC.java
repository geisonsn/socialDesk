
package br.edu.ifam.socialdesk.business;

import java.io.IOException;
import java.util.List;

import org.jboss.resteasy.util.Base64;

import br.edu.ifam.socialdesk.domain.ArquivoChamado;
import br.edu.ifam.socialdesk.domain.Chamado;
import br.edu.ifam.socialdesk.persistence.ArquivoChamadoDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class ArquivoChamadoBC extends DelegateCrud<ArquivoChamado, Long, ArquivoChamadoDAO> {

	private static final long serialVersionUID = 1L;

	public List<ArquivoChamado> find(String query) {
		return getDelegate().find(query);
	}
	
	public ArquivoChamado getPorChamado(Long idChamado) {
		return getDelegate().getPorChamado(idChamado);
	}
	
	public void salvarArquivo(String base64Image, Chamado chamado) {
		
		if (base64Image == null) {
			return;
		}
		
		ArquivoChamado arquivo = this.getPorChamado(chamado.getId());
		
		if (arquivo == null) {
			arquivo = new ArquivoChamado();
			arquivo.setChamado(chamado);
			arquivo.setFoto(convertBase64ToByte(base64Image));
			this.insert(arquivo);
		} else {
			arquivo.setFoto(convertBase64ToByte(base64Image));
			this.update(arquivo);
		}
	}
	
	public byte[] convertBase64ToByte(String base64Image) {
		try {
			return Base64.decode(base64Image);
		} catch (IOException e) {
		}
		return null;
	}

	public List<ArquivoChamado> findPorChamado(Long idChamado) {

		List<ArquivoChamado> arquivos = getDelegate().findPorChamado(idChamado);

		return arquivos;
	}
	
	public void deletePorChamado(Long idChamado) {

		List<ArquivoChamado> arquivos = getDelegate().findPorChamado(idChamado);

		for (ArquivoChamado arquivoChamado : arquivos) {
			delete(arquivoChamado.getId());
		}

		getDelegate().flush();
	}
}