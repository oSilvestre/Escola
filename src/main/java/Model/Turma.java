package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Enumered.TurnoEnum;

public class Turma {

	private Long id;
	private TurnoEnum turno;
	private Date dataInicio;
	private Date dataFim;
	private Date horario;
	private String curso;
	private List<Aluno> alunos;
	
	public Turma() {
		this.alunos = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TurnoEnum getTurno() {
		return turno;
	}
	public void setTurno(TurnoEnum turno) {
		this.turno = turno;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public List<Aluno> getAlunos() {
		return alunos;
	}
	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}
}
