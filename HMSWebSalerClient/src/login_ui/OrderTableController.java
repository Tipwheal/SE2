package login_ui;

import java.util.Iterator;

import VO.OrderVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import order_bl_serv.OrderBlServ;
import order_bl_servlmpl.OrderBlServImpl;

public class OrderTableController {
	@FXML
	private Button checkButton;
	@FXML
	private TableView<OrderTable> table;
	
	@FXML
	private TableColumn<OrderTable,String> idcol;
	
	@FXML
	private TableColumn<OrderTable,String> hotelnamecol;
	
	@FXML
	private TableColumn<OrderTable,String> intimecol;
	
	@FXML
	private TableColumn<OrderTable,String>  outtimecol;
	
	@FXML
	private TableColumn<OrderTable,String>  lasttimecol;
	
	@FXML
	private TableColumn<OrderTable,String> totalcol;
	
	@FXML
	private TableColumn<OrderTable,String>  statecol;
	
	private Iterator<OrderVO> orderList;
	
	private OrderBlServ orderBlServ=new OrderBlServImpl();
	
	private static final ObservableList<OrderTable> data = FXCollections.observableArrayList(); 

	private static MainApp mainApp;
	
	public static void setUp(MainApp mainApp){
		OrderTableController.mainApp=mainApp;
	}
	
	public static void setData(Iterator<OrderVO> orderList){
		data.removeAll(data);
		if(orderList!=null)
		while(orderList.hasNext()){
			OrderVO o=orderList.next();
			data.add(new OrderTable(o.getId(),o.getHotel().getHotelName(),o.getInTime().toString(),
					o.getOutTime().toString(),o.getExecTime().toString()
					,String.valueOf(o.getTotal()),o.getState().toString()));
		}
	}
	
	@FXML
	public void initialize(){
		data.clear();
		orderList=orderBlServ.getAllNotInOrderList();
		while(orderList.hasNext()){
			OrderVO o=orderList.next();
			data.add(new OrderTable(o.getId(),o.getHotel().getHotelName(),o.getInTime().toString(),
					o.getOutTime().toString(),o.getExecTime().toString()
					,String.valueOf(o.getTotal()),o.getState().toString()));
		}
		table.setItems(data);
		idcol.setCellValueFactory(cellData->cellData.getValue().idProperty());
		hotelnamecol.setCellValueFactory(cellData->cellData.getValue().hotelnameProperty());
		intimecol.setCellValueFactory(cellData->cellData.getValue().inttimeProperty());
		outtimecol.setCellValueFactory(cellData->cellData.getValue().outimeProperty());
		lasttimecol.setCellValueFactory(cellData->cellData.getValue().lasttimeProperty());
		totalcol.setCellValueFactory(cellData->cellData.getValue().totelProperty());
		statecol.setCellValueFactory(cellData->cellData.getValue().stateProperty());
	}
	
	@FXML
	public void check(){
		if(table.getSelectionModel().getSelectedItem()!=null)
			mainApp.showOrderDetailsUI(table.getSelectionModel().getSelectedItem().getOrderID());
		else{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Message");
	        alert.setHeaderText("错误");
	        alert.setContentText("请选择一条订单！");
	        alert.showAndWait();
		}
	}
}
