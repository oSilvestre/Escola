package ManagedBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;

import Dao.DashBoardDao;
import Dto.ChaveValorDTO;

@ManagedBean
@ViewScoped
public class DashboardBean {

	private DonutChartModel graficoEmpresas;
	private DonutChartModel graficoProdutos;
	private DashBoardDao dao;
	
	@PostConstruct
	public void carregar() {
		this.dao = new DashBoardDao();
		montarGraficoEmpresa();
		montarGraficoProduto();
	}
	
	public void montarGraficoEmpresa() {
		List<ChaveValorDTO> lista = dao.listarPorEmpresa();
        graficoEmpresas = new DonutChartModel();
        ChartData data = new ChartData();

        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        List<String> bgColors = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<String> cores = Arrays.asList("#660066", "#ff3300", "#99ccff", "#0099cc", "#663300");
        int index = 0;
        
        for(ChaveValorDTO dto : lista) {
        	values.add(dto.getValorInt());
        	bgColors.add(cores.get(index));
        	labels.add(dto.getChaveString());
        	index++;
        	if(index == cores.size())
        		index = 0;
        	
        }
        dataSet.setData(values);
        dataSet.setBackgroundColor(bgColors);
        data.addChartDataSet(dataSet);
        data.setLabels(labels);

        graficoEmpresas.setData(data);
    }
	
	public void montarGraficoProduto() {
		List<ChaveValorDTO> lista = dao.listarPorProduto();
        graficoProdutos = new DonutChartModel();
        ChartData data = new ChartData();

        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        List<String> bgColors = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<String> cores = Arrays.asList("#000066", "#00ff00", "#ff0000", "#cc0099", "#006666");
        int index = 0;
        
        for(ChaveValorDTO dto : lista) {
        	values.add(dto.getValorInt());
        	bgColors.add(cores.get(index));
        	labels.add(dto.getChaveString());
        	index++;
        	if(index == cores.size())
        		index = 0;
        	
        }
        dataSet.setData(values);
        dataSet.setBackgroundColor(bgColors);
        data.addChartDataSet(dataSet);
        data.setLabels(labels);

        graficoProdutos.setData(data);
    }

	public DonutChartModel getGraficoEmpresas() {
		return graficoEmpresas;
	}

	public void setGraficoEmpresas(DonutChartModel graficoEmpresas) {
		this.graficoEmpresas = graficoEmpresas;
	}

	public DonutChartModel getGraficoProdutos() {
		return graficoProdutos;
	}

	public void setGraficoProdutos(DonutChartModel graficoProdutos) {
		this.graficoProdutos = graficoProdutos;
	}

}
