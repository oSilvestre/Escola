package Model;

import java.util.Date;
import java.util.List;

import Enumered.StatusPedidoEnum;

public class Pedido extends Entidade {

	private Empresa empresa;
	private StatusPedidoEnum status;
	private List<PedidoItem> itens;
	private Date dataHoraPedido;
	
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public StatusPedidoEnum getStatus() {
		return status;
	}
	public void setStatus(StatusPedidoEnum status) {
		this.status = status;
	}
	public List<PedidoItem> getItens() {
		return itens;
	}
	public void setItens(List<PedidoItem> itens) {
		this.itens = itens;
	}
	public Date getDataHoraPedido() {
		return dataHoraPedido;
	}
	public void setDataHoraPedido(Date dataHoraPedido) {
		this.dataHoraPedido = dataHoraPedido;
	}
	public double getValorTotal() {
		double valorTotal = 0d;
		if(this.itens != null) {
			for(PedidoItem pi : this.itens) {
				valorTotal += pi.getQuantidade()*pi.getProduto().getValor();
			}
		}
		return valorTotal;
	}
	
}
