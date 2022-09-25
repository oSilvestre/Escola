package ManagedBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import Dao.ProdutoDao;
import Model.Produto;
import Util.Mensagem;

@ManagedBean
@ViewScoped
public class ProdutoBean extends GenericBean<Produto, ProdutoDao> {
	
	@PostConstruct
	public void carregar() {
		limpar();
		buscar();
	}
	
	public void limpar() {
		this.entidade = new Produto();
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
		
		Mensagem.addInfo("Sucesso", "Produto gravado com sucesso");
		
		limpar();
		voltar();
		buscar();
	}
	
	public void excluir() {
		this.dao.deletar(this.entidade.getId());
		Mensagem.addInfo("Sucesso", "Produto deletado com sucesso");
		this.limpar();
		this.buscar();
	}

	
	
}
