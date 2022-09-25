package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Model.Empresa;
import Util.Conexao;
import inteface.Dao;

public class EmpresaDao implements Dao<Empresa> {

	public void inserir(Empresa empresa) {
		String sql = "INSERT INTO empresa (razao_social, nome_fantasia, cnpj) values (?,?,?);";
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, empresa.getRazaoSocial());
			ps.setString(2, empresa.getNomeFantasia());
			ps.setLong(3, empresa.getCnpj());
			ps.execute();
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Empresa> buscar(Empresa entidade, int offset, int limit, String sortField, int sortOrder) {
		List<Empresa> lista = new ArrayList<>();
		if (sortField == null) sortField = "id";
		
		String sort = "DESC";
		if(sortOrder == 1)
			sort = "ASC";
		
		String sql = " SELECT id, razao_social, nome_fantasia, cnpj "
				+ " FROM empresa "
				+ pesquisa(entidade)
				+ " ORDER BY " + sortField + " " + sort
				+ " OFFSET ? LIMIT ? ";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, offset);
			ps.setInt(2, limit);
			ResultSet rs = ps.executeQuery();
			Empresa empresa = null;
			while (rs.next()) {
				empresa = new Empresa();
				empresa.setId(rs.getLong("id"));
				empresa.setRazaoSocial(rs.getString("razao_social"));
				empresa.setNomeFantasia(rs.getString("nome_fantasia"));
				empresa.setCnpj(rs.getLong("cnpj"));
				
				lista.add(empresa);
			}
			
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return lista;
	}
	
	public int total(Empresa entidade) {
		int total = 0;
		
		String sql = " SELECT count(id) as total "
				+ " FROM empresa "
				+ pesquisa(entidade);
		
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
	
	private String pesquisa(Empresa empresa) {
		String retorno = "";
		
		retorno += " AND ativo ";
		
		if(empresa.getId() != null)
			retorno += " AND id = "+empresa.getId();
		if(empresa.getRazaoSocial() != null)
			retorno += " AND razao_social ILIKE '%"+empresa.getRazaoSocial()+"%'";
		
		if(!retorno.isEmpty()) retorno = " WHERE " + retorno.substring(5);
		
		return retorno;
	}

	@Override
	public void alterar(Empresa t) {
		String sql =  "UPDATE empresa SET razao_social = ?, nome_fantasia = ?, cnpj = ? WHERE id = ?";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, t.getRazaoSocial());
			ps.setString(2, t.getNomeFantasia());
			ps.setLong(3, t.getCnpj());
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
		String sql =  "UPDATE empresa SET ativo = FALSE WHERE id = ?";
		
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
	
	public Empresa selecionar(Long id) {
		Empresa empresa = new Empresa();
		
		String sql = " SELECT id, razao_social, nome_fantasia, cnpj "
				+ " FROM empresa "
				+ " WHERE id = ?";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				empresa.setId(rs.getLong("id"));
				empresa.setRazaoSocial(rs.getString("razao_social"));
				empresa.setNomeFantasia(rs.getString("nome_fantasia"));
				empresa.setCnpj(rs.getLong("cnpj"));
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return empresa;
	}

	public List<Empresa> completarEmpresa(String query) {
		List<Empresa> lista = new ArrayList<>();
		
		String sql = " SELECT id, razao_social "
				+ " FROM empresa "
				+ " WHERE ativo "
				+ " AND razao_social ILIKE '"+query+"%'"
				+ " LIMIT 10";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Empresa t = null;
			while (rs.next()) {
				t = new Empresa();
				t.setId(rs.getLong("id"));
				t.setRazaoSocial(rs.getString("razao_social"));
				
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
