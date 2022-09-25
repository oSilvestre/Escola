package ManagedBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import Dao.EmpresaDao;
import Model.Empresa;
import Util.Mensagem;

@ManagedBean
@ViewScoped
public class EmpresaBean extends GenericBean<Empresa, EmpresaDao> {
	
	@PostConstruct
	public void carregar() {
		limpar();
		buscar();
	}
	
	public void limpar() {
		this.entidade = new Empresa();
	}
	
	@Override
	public void selecionar() {
		this.entidade = this.dao.selecionar(this.entidade.getId());
	}
	
	public void gravar() {
		if(entidade.getId() != null)
			this.dao.alterar(entidade);
		else
			this.dao.inserir(entidade);
		
		Mensagem.addInfo("Sucesso", "Empresa gravada com sucesso");
		
		limpar();
		voltar();
		buscar();
	}
	
	public void excluir() {
		this.dao.deletar(this.entidade.getId());
		Mensagem.addInfo("Sucesso", "Empresa deletada com sucesso");
		this.limpar();
		this.buscar();
	}

	
	
}
