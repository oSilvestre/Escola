package ManagedBean;

import java.lang.reflect.ParameterizedType;

import javax.faces.bean.ViewScoped;

import Enumered.StateEnum;
import Lazy.LazyList;
import Model.Entidade;
import Util.Mensagem;
import inteface.Dao;
@ViewScoped
public class GenericBean<T extends Entidade, T2> {
	
	private StateEnum state = StateEnum.PESQUISAR;
	protected T entidade;
	protected Dao<T> dao;
	protected LazyList<?> lista;
	
	@SuppressWarnings("unchecked")
	public GenericBean(){
		try {
			entidade = ((Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
			dao = (Dao<T>) ((Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1]).newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}
	
	public void limpar() {
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void buscar() {
		this.lista = new LazyList((Entidade) entidade, dao);
	}
	
	public void prepareCadastrar() {
		System.out.println("prepare cadastrar");
		state = StateEnum.CADASTRAR;
	}
	
	public void gravar() {
		if(entidade.getId() != null)
			this.dao.alterar(entidade);
		else
			this.dao.inserir(entidade);
		
		Mensagem.addInfo("Sucesso", "Gravado com sucesso");
		
		limpar();
		voltar();
		buscar();
	}
	
	public void voltar() {
		System.out.println("voltar");
		state = StateEnum.PESQUISAR;
		limpar();
		buscar();
	}
	
	public void prepareEditar() {
		state = StateEnum.EDITAR;
		System.out.println("prepare editar");
		this.selecionar();
	}
	
	public void selecionar() {
		
	}
	

	public StateEnum getState() {
		return state;
	}

	public void setState(StateEnum state) {
		this.state = state;
	}

	public T getEntidade() {
		return entidade;
	}

	public void setEntidade(T entidade) {
		this.entidade = entidade;
	}

	public LazyList<?> getLista() {
		return lista;
	}

	public void setLista(LazyList<?> lista) {
		this.lista = lista;
	}


	
}
