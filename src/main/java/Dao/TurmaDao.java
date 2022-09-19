package Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import Enumered.TurnoEnum;
import Model.Aluno;
import Model.Turma;
import Util.Conexao;
import inteface.Dao;

public class TurmaDao implements Dao<Turma> {

	@Override
	public void inserir(Turma turma) {
		String sql = "INSERT INTO turma (turno, dt_inicio, dt_fim, horario, curso) values (?,?,?,?,?) RETURNING id;";
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, turma.getTurno().ordinal());
			ps.setDate(2, new Date(turma.getDataInicio().getTime()));
			ps.setDate(3, new Date(turma.getDataFim().getTime()));
			ps.setTime(4, new Time(turma.getHorario().getTime()));
			ps.setString(5, turma.getCurso());
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				turma.setId(rs.getLong("id"));
			
			sql = "INSERT INTO turma_aluno (id_turma, id_aluno) VALUES (?,?);";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, turma.getId());
			for(Aluno aluno : turma.getAlunos()) {
				ps.setLong(2, aluno.getId());
				ps.execute();
			}
			
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Turma> buscar(Turma entidade) {
		List<Turma> lista = new ArrayList<>();
		
		String sql = " SELECT id, curso, dt_inicio, dt_fim "
				+ " FROM turma "
				+ pesquisa(entidade);
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Turma turma = null;
			while (rs.next()) {
				turma = new Turma();
				turma.setId(rs.getLong("id"));
				turma.setCurso(rs.getString("curso"));
				turma.setDataInicio(rs.getDate("dt_inicio"));
				turma.setDataFim(rs.getDate("dt_fim"));
				
				lista.add(turma);
			}
			
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return lista;
	}
	
	private String pesquisa(Turma turma) {
		String retorno = "";
		retorno += " AND ativo ";
		
		if(!retorno.isEmpty()) retorno = " WHERE " + retorno.substring(5);
		
		return retorno;
	}

	@Override
	public void alterar(Turma t) {
		String sql = "UPDATE turma SET turno = ?, dt_inicio = ?, dt_fim = ?, horario = ?, curso = ? WHERE id = ?";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, t.getTurno().ordinal());
			ps.setDate(2, new Date(t.getDataInicio().getTime()));
			ps.setDate(3, new Date(t.getDataFim().getTime()));
			ps.setTime(4, new Time(t.getHorario().getTime()));
			ps.setString(5, t.getCurso());
			ps.setLong(6, t.getId());
			ps.execute();
			
			sql = "DELETE FROM turma_aluno WHERE id_turma = ?;";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, t.getId());
			ps.execute();
			
			sql = "INSERT INTO turma_aluno (id_turma, id_aluno) VALUES (?,?);";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, t.getId());
			for(Aluno aluno : t.getAlunos()) {
				ps.setLong(2, aluno.getId());
				ps.execute();
			}
			
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deletar(Long id) {
		String sql =  "UPDATE turma SET ativo = FALSE WHERE id = ?";
		
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

	@Override
	public Turma selecionar(Long id) {
		Turma turma = new Turma();
		
		String sql = " SELECT id, curso, dt_inicio, dt_fim, horario, turno "
				+ " FROM turma "
				+ " WHERE id = ?";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				turma.setId(rs.getLong("id"));
				turma.setCurso(rs.getString("curso"));
				turma.setDataInicio(rs.getDate("dt_inicio"));
				turma.setDataFim(rs.getDate("dt_fim"));
				turma.setHorario(rs.getTime("horario"));
				turma.setTurno(TurnoEnum.values()[rs.getInt("turno")]);
				turma.setAlunos(this.listarAlunos(turma.getId()));
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return turma;
	}
	
	private List<Aluno> listarAlunos(Long idTurma){
		List<Aluno> lista = new ArrayList<>();
		
		String sql = " SELECT a.id, a.nome, a.matricula "
				+ " FROM turma_aluno ta "
				+ " JOIN aluno a ON a.id = ta.id_aluno "
				+ " WHERE ta.id_turma = ?";
		
		try {
			Connection conn = Conexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, idTurma);
			ResultSet rs = ps.executeQuery();
			Aluno aluno = null;
			while (rs.next()) {
				aluno = new Aluno();
				aluno.setId(rs.getLong("id"));
				aluno.setNome(rs.getString("nome"));
				aluno.setMatricula(rs.getInt("matricula"));
				
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
