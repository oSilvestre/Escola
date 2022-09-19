package inteface;

import java.util.List;

public interface Dao<T> {

	public void inserir(T t);
	
	public void alterar(T t);
	
	public List<T> buscar(T t);
	
	public void deletar(Long id);
	
	public T selecionar(Long id);
}
