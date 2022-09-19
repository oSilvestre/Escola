package ManagedBean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import Dao.AlunoDao;
import Dao.TurmaDao;
import Model.Aluno;
import Model.Turma;
import Util.Mensagem;

@ManagedBean
@SessionScoped
public class TurmaBean extends GenericBean<Turma, TurmaDao> {
	
	private Turma entidade;
	private TurmaDao dao;
	private List<Turma> lista;
	private List<Aluno> alunos;
	private Aluno alunoSelecionado;

	@PostConstruct
	public void carregar() {
		limpar();
		this.dao = new TurmaDao();
		buscar();
		this.alunos = new AlunoDao().listarAlunos();
	}
	
	public void limpar() {
		this.entidade = new Turma();
	}
	
	public void buscar() {
		this.lista = this.dao.buscar(this.entidade);
	}
	
	public void gravar() {
		if(entidade.getId() != null)
			this.dao.alterar(entidade);
		else
			this.dao.inserir(entidade);
		
		Mensagem.addInfo("Sucesso", "Turma gravada com sucesso");
		
		limpar();
		voltar();
		buscar();
	}
	
	@Override
	public void voltar() {
		super.voltar();
		this.limpar();
	}
	
	@Override
	public void selecionar() {
		this.entidade = this.dao.selecionar(this.entidade.getId());
	}
	
	public void excluir() {
		this.dao.deletar(this.entidade.getId());
		Mensagem.addInfo("Sucesso", "Turma deletada com sucesso");
		this.limpar();
		this.buscar();
	}
	
	public void adicionarAluno() {
		if(alunoSelecionado == null) {
			Mensagem.addAviso("O aluno não pode ser em branco", "");
			return;
		}
		
		if(this.entidade.getAlunos().contains(alunoSelecionado)) {
			Mensagem.addErro("Erro", "O aluno selecionado já foi adicionado");
			return;
		}
		
		this.entidade.getAlunos().add((Aluno) alunoSelecionado);
		Mensagem.addInfo("Sucesso", "Aluno adicionado com sucesso");
		this.alunoSelecionado = null;
	}
	
	public void removerAluno(Aluno a) {
		this.entidade.getAlunos().remove(a);
		Mensagem.addInfo("Sucesso", "Aluno removido com sucesso");
	}

	public Turma getEntidade() {
		return entidade;
	}

	public void setEntidade(Turma entidade) {
		this.entidade = entidade;
	}

	public List<Turma> getLista() {
		return lista;
	}

	public void setLista(List<Turma> lista) {
		this.lista = lista;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Aluno getAlunoSelecionado() {
		return alunoSelecionado;
	}

	public void setAlunoSelecionado(Aluno alunoSelecionado) {
		this.alunoSelecionado = alunoSelecionado;
	}
	
	
	
	
}
