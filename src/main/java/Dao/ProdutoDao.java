package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Model.Produto;
import Util.Conexao;
import inteface.Dao;

public class ProdutoDao implements Dao<Produto> {

	public void inserir(Produto t) {
		String sql = "INSERT INTO produto (nome, descricao, valor) values (?,?,?);";
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, t.getNome());
			ps.setString(2, t.getDescricao());
			ps.setDouble(3, t.getValor());
			ps.execute();
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Produto> buscar(Produto entidade, int offset, int limit, String sortField, int sortOrder) {
		List<Produto> lista = new ArrayList<>();
		if (sortField == null) sortField = "id";
		
		String sort = "DESC";
		if(sortOrder == 1)
			sort = "ASC";
		
		String sql = " SELECT id, nome, valor "
				+ " FROM produto "
				+ pesquisa(entidade)
				+ " ORDER BY " + sortField + " " + sort
				+ " OFFSET ? LIMIT ? ";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, offset);
			ps.setInt(2, limit);
			ResultSet rs = ps.executeQuery();
			Produto t = null;
			while (rs.next()) {
				t = new Produto();
				t.setId(rs.getLong("id"));
				t.setNome(rs.getString("nome"));
				t.setValor(rs.getDouble("valor"));
				
				lista.add(t);
			}
			
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return lista;
	}
	
	public int total(Produto t) {
		int total = 0;
		
		String sql = " SELECT count(id) as total "
				+ " FROM produto "
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
	
	private String pesquisa(Produto t) {
		String retorno = "";
		
		retorno += " AND ativo ";
		
		if(t.getId() != null)
			retorno += " AND id = "+t.getId();
		
		if(!retorno.isEmpty()) retorno = " WHERE " + retorno.substring(5);
		
		return retorno;
	}

	@Override
	public void alterar(Produto t) {
		String sql =  "UPDATE produto SET nome = ?, descricao = ?, valor = ? WHERE id = ?";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, t.getNome());
			ps.setString(2, t.getDescricao());
			ps.setDouble(3, t.getValor());
			ps.setLong(4, t.getId());
			ps.execute();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deletar(Long id) {
		String sql =  "UPDATE produto SET ativo = FALSE WHERE id = ?";
		
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
	
	public Produto selecionar(Long id) {
		Produto t = new Produto();
		
		String sql = " SELECT id, nome, descricao, valor "
				+ " FROM produto "
				+ " WHERE id = ?";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				t.setId(rs.getLong("id"));
				t.setNome(rs.getString("nome"));
				t.setDescricao(rs.getString("descricao"));
				t.setValor(rs.getDouble("valor"));
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return t;
	}
	
	public List<Produto> listarProdutos() {
		List<Produto> lista = new ArrayList<>();
		
		String sql = " SELECT id, nome, valor "
				+ " FROM produto ";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Produto t = null;
			while (rs.next()) {
				t = new Produto();
				t.setId(rs.getLong("id"));
				t.setNome(rs.getString("nome"));
				t.setValor(rs.getDouble("valor"));
				
				lista.add(t);
			}
			
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	}

	public List<Produto> completarProduto(String query) {
		List<Produto> lista = new ArrayList<>();
		
		String sql = " SELECT id, nome, valor "
				+ " FROM produto "
				+ " WHERE ativo "
				+ " AND nome ILIKE '"+query+"%'"
				+ " LIMIT 10";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Produto t = null;
			while (rs.next()) {
				t = new Produto();
				t.setId(rs.getLong("id"));
				t.setNome(rs.getString("nome"));
				t.setValor(rs.getDouble("valor"));
				
				lista.add(t);
			}
			
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	}

}
