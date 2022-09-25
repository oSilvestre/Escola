package ManagedBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import Dao.EmpresaDao;
import Dao.PedidoDao;
import Dao.ProdutoDao;
import Enumered.StatusPedidoEnum;
import Model.Empresa;
import Model.Pedido;
import Model.PedidoItem;
import Model.Produto;
import Util.Mensagem;

@ManagedBean
@ViewScoped
public class Pedido2Bean extends GenericBean<Pedido, PedidoDao> {
	
	private List<Produto> produtos;
	private PedidoItem pedidoItem;

	@PostConstruct
	public void carregar() {
		limpar();
		buscar();
		this.produtos = new ProdutoDao().listarProdutos();
	}
	
	@Override
	public void limpar() {
		this.entidade = new Pedido();
		this.pedidoItem = new PedidoItem();
		this.pedidoItem.setProduto(new Produto());
		this.entidade.setItens(new ArrayList<>());
	}
	
	@Override
	public void prepareCadastrar() {
		super.prepareCadastrar();
		this.entidade.setStatus(StatusPedidoEnum.EM_DIGITACAO);
		this.entidade.setDataHoraPedido(new Date());
	}
	
	@Override
	public void selecionar() {
		System.out.println("selecionando");
		this.entidade = this.dao.selecionar(this.entidade.getId());
	}
	
	public void gravarCabecalho() {
		((PedidoDao) this.dao).gravarCabecalho(this.entidade);
		Mensagem.addInfo("Sucesso", "Cabeçalho gravado com sucesso");
	}
	
	public void finalizar() {
		((PedidoDao) this.dao).alterarStatus(this.entidade.getId(), StatusPedidoEnum.FINALIZADO);
		this.gravar();
	}
	
	public void cancelar() {
		((PedidoDao) this.dao).alterarStatus(this.entidade.getId(), StatusPedidoEnum.CANCELADO);
		Mensagem.addInfo("Sucesso", "Pedido cancelado com sucesso");
	}
	
	public void excluir() {
		this.dao.deletar(this.entidade.getId());
		Mensagem.addInfo("Sucesso", "Turma deletada com sucesso");
		this.limpar();
		this.buscar();
	}
	
	public void adicionarItem() {
		if(pedidoItem.getProduto() == null) {
			Mensagem.addAviso("O produto não pode ser em branco", "");
			return;
		}
		if(!(pedidoItem.getQuantidade() > 0)) {
			Mensagem.addAviso("Aviso", "A quantidade deve ser maior que 0");
			return;
		}
		
		for(PedidoItem pi : entidade.getItens()) {
			if(pi.getProduto().equals(pedidoItem.getProduto())) {
				Mensagem.addErro("Erro", "O produto selecionado já foi adicionado");
				return;
			}
		}
		
		((PedidoDao) this.dao).adicionarItem(this.entidade, this.pedidoItem);
		this.listarItens();
		Mensagem.addInfo("Sucesso", "Item adicionado com sucesso");
		this.pedidoItem = new PedidoItem();
	}
	
	public void listarItens () {
		this.entidade.setItens(((PedidoDao) this.dao).listarItens(this.entidade.getId()));
	}
	
	public void removerItem(PedidoItem pi) {
		((PedidoDao) this.dao).removerItem(pi.getId());
		this.listarItens();
		Mensagem.addInfo("Sucesso", "Item removido com sucesso");
	}
	
	public List<Produto> completarProduto(String query){
		return new ProdutoDao().completarProduto(query);
	}

	public List<Empresa> completarEmpresa(String query){
		return new EmpresaDao().completarEmpresa(query);
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public PedidoItem getPedidoItem() {
		return pedidoItem;
	}

	public void setPedidoItem(PedidoItem pedidoItem) {
		this.pedidoItem = pedidoItem;
	}

	
}
