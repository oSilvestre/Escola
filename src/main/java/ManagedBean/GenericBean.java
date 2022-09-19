package ManagedBean;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;

import Enumered.StateEnum;
import inteface.Dao;

public class GenericBean<T,T2> {
	
	private StateEnum state = StateEnum.PESQUISAR;
//	protected Class<T> entidade;
//	protected Dao<T> dao;// = (Dao<T>) entidade.newInstance();
//	protected List<T> lista;
	
	
//	public GenericBean(Class<T> clazz)  {
//		try {
////			this.entidade = T.newInstance();
//			this.entidade = (Class<T>) clazz.newInstance();
////	        Class<?> clazz = (Class<T>) ((ParameterizedType)  .getGenericSuperclass()).getActualTypeArguments()[0];
//			this.dao = (Dao<T>) entidade.newInstance();
//		} catch (IllegalAccessException | InstantiationException e) {
//			e.printStackTrace();
//		}
//		this.buscar();
//	}
	
//	public void limpar() {
//		try {
//			this.entidade = (Class<T>) entidade.newInstance();
//		} catch (IllegalAccessException | InstantiationException e) {
//			e.printStackTrace();
//		}
//	}
	
//	public void buscar() {
//		this.lista = this.dao.buscar((T) this.entidade);
//	}
	
	public void prepareCadastrar() {
		System.out.println("prepare cadastrar");
		state = StateEnum.CADASTRAR;
	}
	
	public void voltar() {
		System.out.println("voltar");
		state = StateEnum.PESQUISAR;
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


	
}
