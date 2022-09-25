package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Enumered.StatusPedidoEnum;
import Model.Pedido;
import Model.PedidoItem;
import Model.Produto;
import Util.Conexao;
import inteface.Dao;

public class PedidoDao implements Dao<Pedido> {

	public void inserir(Pedido t) {
		String sql = "INSERT INTO pedido (status, data_hora_pedido, id_empresa) values (?,?,?) RETURNING id";
		try {
			Connection conn = Conexao.getConexao(false);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, t.getStatus().ordinal());
			ps.setTimestamp(2, new Timestamp(t.getDataHoraPedido().getTime()));
			ps.setLong(3, t.getEmpresa().getId());
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				t.setId(rs.getLong("id"));
			
			sql = "INSERT INTO pedido_item (id_pedido, id_produto, quantidade) VALUES (?,?,?);";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, t.getId());
			for(PedidoItem pi : t.getItens()) {
				ps.setLong(2, pi.getProduto().getId());
				ps.setInt(3, pi.getQuantidade());
				ps.execute();
			}
			
			ps.close();
			conn.commit();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Pedido> buscar(Pedido entidade, int offset, int limit, String sortField, int sortOrder) {
		List<Pedido> lista = new ArrayList<>();
		if (sortField == null) sortField = "id";
		
		String sort = "DESC";
		if(sortOrder == 1)
			sort = "ASC";
		
		String sql = " SELECT id, status, data_hora_pedido "
				+ " FROM pedido "
				+ pesquisa(entidade)
				+ " ORDER BY " + sortField + " " + sort
				+ " OFFSET ? LIMIT ? ";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, offset);
			ps.setInt(2, limit);
			ResultSet rs = ps.executeQuery();
			Pedido t = null;
			while (rs.next()) {
				t = new Pedido();
				t.setId(rs.getLong("id"));
				t.setStatus(StatusPedidoEnum.values()[rs.getInt("status")]);
				t.setDataHoraPedido(rs.getTimestamp("data_hora_pedido"));
				
				lista.add(t);
			}
			
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return lista;
	}
	
	public int total(Pedido t) {
		int total = 0;
		
		String sql = " SELECT count(id) as total "
				+ " FROM pedido "
				+ pesquisa(t);
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				total = rs.getInt("total");
			}
			
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return total;
	}
	
	private String pesquisa(Pedido t) {
		String retorno = "";
		
		retorno += " AND ativo ";
		
		if(t.getId() != null)
			retorno += " AND id = "+t.getId();
		if(t.getEmpresa() != null && t.getEmpresa().getId() != null)
			retorno += " AND id_empresa = "+t.getEmpresa().getId();
		if(t.getStatus() != null)
			retorno += " AND status = "+t.getStatus().ordinal();
		
		if(!retorno.isEmpty()) retorno = " WHERE " + retorno.substring(5);
		
		return retorno;
	}

	@Override
	public void alterar(Pedido t) {
		String sql =  "UPDATE pedido SET id_empresa = ? WHERE id = ?";
		
		Connection conn = Conexao.getConexao(false);
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, t.getEmpresa().getId());
			ps.setLong(2, t.getId());
			ps.execute();
			
			sql = "DELETE FROM pedido_item WHERE id_pedido = ?;";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, t.getId());
			ps.execute();
			
			sql = "INSERT INTO pedido_item (id_pedido, id_produto, quantidade) VALUES (?,?,?);";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, t.getId());
			for(PedidoItem pi : t.getItens()) {
				ps.setLong(2, pi.getProduto().getId());
				ps.setInt(3, pi.getQuantidade());
				ps.execute();
			}
			
			ps.close();
			conn.commit();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deletar(Long id) {
		String sql =  "UPDATE pedido SET ativo = FALSE WHERE id = ?";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			ps.execute();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Pedido selecionar(Long id) {
		Pedido t = new Pedido();
		
		String sql = " SELECT id, status, data_hora_pedido, id_empresa "
				+ " FROM pedido "
				+ " WHERE id = ?";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				t.setId(rs.getLong("id"));
				t.setStatus(StatusPedidoEnum.values()[rs.getInt("status")]);
				t.setDataHoraPedido(rs.getTimestamp("data_hora_pedido"));
				t.setEmpresa(new EmpresaDao().selecionar(rs.getLong("id_empresa")));
				t.setItens(this.listarItens(t.getId()));
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return t;
	}

	public List<PedidoItem> listarItens(Long idPedido){
		List<PedidoItem> lista = new ArrayList<>();
		
		String sql = " SELECT pi.id, pi.quantidade, p.id as id_produto, p.nome, p.valor "
				+ " FROM pedido_item pi "
				+ " JOIN produto p ON p.id = pi.id_produto "
				+ " WHERE pi.id_pedido = ?";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, idPedido);
			ResultSet rs = ps.executeQuery();
			Produto p = null;
			PedidoItem pi = null;
			while (rs.next()) {
				p = new Produto();
				p.setId(rs.getLong("id_produto"));
				p.setNome(rs.getString("nome"));
				p.setValor(rs.getDouble("valor"));
				
				pi = new PedidoItem();
				pi.setId(rs.getLong("id"));
				pi.setProduto(p);
				pi.setQuantidade(rs.getInt("quantidade"));
				
				lista.add(pi);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public void alterarStatus(Long id, StatusPedidoEnum status) {
		String sql =  "UPDATE pedido SET status = ? WHERE id = ?";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, status.ordinal());
			ps.setLong(2, id);
			ps.execute();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void gravarCabecalho(Pedido t) {
		String sql = "INSERT INTO pedido (status, data_hora_pedido, id_empresa) values (?,?,?) RETURNING id";
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, t.getStatus().ordinal());
			ps.setTimestamp(2, new Timestamp(t.getDataHoraPedido().getTime()));
			ps.setLong(3, t.getEmpresa().getId());
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				t.setId(rs.getLong(1));
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adicionarItem(Pedido t, PedidoItem pi) {
		String sql = "INSERT INTO pedido_item (id_pedido, id_produto, quantidade) VALUES (?,?,?);";
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, t.getId());
			ps.setLong(2, pi.getProduto().getId());
			ps.setInt(3, pi.getQuantidade());
			ps.execute();
			
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removerItem(Long idPedidoItem) {
		String sql =  "DELETE FROM pedido_item WHERE id = ?";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, idPedidoItem);
			ps.execute();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
