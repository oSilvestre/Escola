package ManagedBean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import Dao.AlunoDao;
import Model.Aluno;
import Util.Mensagem;

@ManagedBean
@ViewScoped
public class AlunoBean extends GenericBean<Aluno, AlunoDao> {
	
	private Aluno entidade;
	private AlunoDao dao;
	private List<Aluno> lista;

	@PostConstruct
	public void carregar() {
		System.out.println("carregando aluno");
		limpar();
		this.dao = new AlunoDao();
		buscar();
	}
	
	public void limpar() {
		this.entidade = new Aluno();
	}
	
	public void buscar() {
		this.lista = this.dao.buscar(this.entidade);
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

	public Aluno getEntidade() {
		return entidade;
	}

	public void setEntidade(Aluno entidade) {
		this.entidade = entidade;
	}

	public List<Aluno> getLista() {
		return lista;
	}

	public void setLista(List<Aluno> lista) {
		this.lista = lista;
	}
	
}
