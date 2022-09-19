package Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Model.Aluno;
import Util.Conexao;
import inteface.Dao;

public class AlunoDao implements Dao<Aluno> {

	public void inserir(Aluno aluno) {
		String sql = "INSERT INTO aluno (nome, matricula, dt_nascimento) values (?,?,?);";
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, aluno.getNome());
			ps.setInt(2, aluno.getMatricula());
			ps.setDate(3, new Date(aluno.getDataNascimento().getTime()));
			ps.execute();
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Aluno> buscar(Aluno entidade) {
		List<Aluno> lista = new ArrayList<>();
		
		String sql = " SELECT id, nome, matricula, dt_nascimento "
				+ " FROM aluno "
				+ pesquisa(entidade)
				+ " ORDER BY id DESC";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Aluno aluno = null;
			while (rs.next()) {
				aluno = new Aluno();
				aluno.setId(rs.getLong("id"));
				aluno.setNome(rs.getString("nome"));
				aluno.setMatricula(rs.getInt("matricula"));
				aluno.setDataNascimento(rs.getDate("dt_nascimento"));
				
				lista.add(aluno);
			}
			
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return lista;
	}
	
	private String pesquisa(Aluno aluno) {
		String retorno = "";
		
		retorno += " AND ativo ";
		
		if(aluno.getId() != null)
			retorno += " AND id = "+aluno.getId();
		if(aluno.getNome() != null)
			retorno += " AND nome ILIKE '%"+aluno.getNome()+"%'";
		if(aluno.getMatricula() != null)
			retorno += " AND matricula = "+aluno.getMatricula();
		
		if(!retorno.isEmpty()) retorno = " WHERE " + retorno.substring(5);
		
		return retorno;
	}

	@Override
	public void alterar(Aluno t) {
		String sql =  "UPDATE aluno SET nome = ?, matricula = ?, dt_nascimento = ? WHERE id = ?";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, t.getNome());
			ps.setInt(2, t.getMatricula());
			ps.setDate(3, new Date(t.getDataNascimento().getTime()));
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
		String sql =  "UPDATE aluno SET ativo = FALSE WHERE id = ?";
		
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
	
	public Aluno selecionar(Long id) {
		Aluno aluno = new Aluno();
		
		String sql = " SELECT id, nome, matricula, dt_nascimento "
				+ " FROM aluno "
				+ " WHERE id = ?";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				aluno.setId(rs.getLong("id"));
				aluno.setNome(rs.getString("nome"));
				aluno.setMatricula(rs.getInt("matricula"));
				aluno.setDataNascimento(rs.getDate("dt_nascimento"));
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return aluno;
	}

	public List<Aluno> listarAlunos() {
		List<Aluno> lista = new ArrayList<>();
		
		String sql = " SELECT id, nome "
				+ " FROM aluno ";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Aluno aluno = null;
			while (rs.next()) {
				aluno = new Aluno();
				aluno.setId(rs.getLong("id"));
				aluno.setNome(rs.getString("nome"));
				
				lista.add(aluno);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
}
