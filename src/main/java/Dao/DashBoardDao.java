package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Dto.ChaveValorDTO;
import Enumered.StatusPedidoEnum;
import Util.Conexao;

public class DashBoardDao {

	public List<ChaveValorDTO> listarPorEmpresa(){
		List<ChaveValorDTO> lista = new ArrayList<>();
		String sql = "select e.razao_social, sum(pi.quantidade * p2.valor) as total "
				+ "from pedido p "
				+ "join pedido_item pi on pi.id_pedido = p.id "
				+ "join produto p2 on p2.id = pi.id_produto "
				+ "join empresa e on e.id = p.id_empresa "
				+ "where p.status = ? "
				+ "group  by 1 order by 2 desc ";
		
		Connection conn = Conexao.getConexao();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, StatusPedidoEnum.FINALIZADO.ordinal());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ChaveValorDTO dto = new ChaveValorDTO();
				dto.setChaveString(rs.getString("razao_social"));
				dto.setValorInt(rs.getInt("total"));
				lista.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	public List<ChaveValorDTO> listarPorProduto(){
		List<ChaveValorDTO> lista = new ArrayList<>();
		String sql = "select p.nome, sum(pi.quantidade) as total "
				+ "from pedido_item pi "
				+ "join produto p on p.id = pi.id_produto "
				+ "join pedido pe on pe.id = pi.id_pedido "
				+ "where pe.status = ?"
				+ "group  by 1 order by 2 desc ";
		
		Connection conn = Conexao.getConexao();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, StatusPedidoEnum.FINALIZADO.ordinal());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ChaveValorDTO dto = new ChaveValorDTO();
				dto.setChaveString(rs.getString("nome"));
				dto.setValorInt(rs.getInt("total"));
				lista.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	}
} 

