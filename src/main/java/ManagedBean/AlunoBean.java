package ManagedBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import Dao.AlunoDao;
import Model.Aluno;
import Util.Mensagem;

@ManagedBean
@ViewScoped
public class AlunoBean extends GenericBean<Aluno, AlunoDao> {
	
	@PostConstruct
	public void carregar() {
		limpar();
		buscar();
	}
	
	public void limpar() {
		this.entidade = new Aluno();
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
		
		Mensagem.addInfo("Sucesso", "Aluno gravado com sucesso");
		
		limpar();
		voltar();
		buscar();
	}
	
	public void excluir() {
		this.dao.deletar(this.entidade.getId());
		Mensagem.addInfo("Sucesso", "Aluno deletado com sucesso");
		this.limpar();
		this.buscar();
	}

	
	
}
