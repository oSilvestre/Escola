//package Lazy;
//
//import java.util.List;
//import java.util.Map;
//
//import org.primefaces.model.FilterMeta;
//import org.primefaces.model.LazyDataModel;
//import org.primefaces.model.SortMeta;
//
//import Dao.AlunoDao;
//import Model.Aluno;
//
//public class AlunoLazyList extends LazyDataModel<Aluno>{
//
//	private List<Aluno> datasource;
//	private Aluno aluno;
//	 
//    public AlunoLazyList(List<Aluno> datasource) {
//        this.datasource = datasource;
//    }
// 
//    @Override
//    public Aluno getRowData(String rowKey) {
//        for (Aluno car : datasource) {
//            if (car.getId().equals(rowKey)) {
//                return car;
//            }
//        }
// 
//        return null;
//    }
// 
//    @Override
//    public String getRowKey(Aluno car) {
//        return car.getId()+"";
//    }
//	
//	@Override
//	public List<Aluno> load(int arg0, int arg1, Map<String, SortMeta> arg2, Map<String, FilterMeta> arg3) {
//		AlunoDao dao = new AlunoDao();
//		System.out.println("buscando");
//		
//		setRowCount(dao.total(aluno));
//		if(getRowCount() > 0)
//			return dao.buscar(aluno, arg0, arg1);
//		return null;
//	}
//	
//}
