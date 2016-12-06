package user_main_ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
public class MainController {

	/*
	 * 左侧菜单栏
	 */
	@FXML
	private Button mainUIButton;
	
	@FXML
	private Button personalInfoButton;
	
	@FXML
	private Button creditUIButton;
	
	@FXML
	private Button orderInfoButton;
	
	@FXML
	private Button pastHotelInfoButton;
	
	
	@FXML
	private AnchorPane mainUIPane;
	
	@FXML
	private AnchorPane personalInfoPane;
	
	@FXML
	private AnchorPane creditUIPane;
	
	@FXML
	private AnchorPane orderInfoPane;
	
	@FXML
	private AnchorPane pastHotelInfoPane;
	
	
	
	
	
	@FXML
	private Label idLabel;  
	
	@FXML
	private Label userNameLabel; 
	
	@FXML
	private Label nameLabel; 
	
	
	@FXML
	private Label birthdayLabel; 
	
	@FXML
	private Label BusiLabel;  
	
	@FXML
	private Label phoneLabel; 
	
	@FXML
	private Label memberLevelLabel; 
	

	
	



    // Reference to the main application.
    private UserMainApp mainApp;
    
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
	@FXML
	public void initialize(){
		mainUIPane.setVisible(true);
		
	}
	@FXML
	public void showMainUI(){
		personalInfoPane.setVisible(false);
		creditUIPane.setVisible(false);
		orderInfoPane.setVisible(false);
		pastHotelInfoPane.setVisible(false);
		mainUIPane.setVisible(true);
	}
	@FXML
	public void showPersonalInfo(){
		mainUIPane.setVisible(false);
		creditUIPane.setVisible(false);
		orderInfoPane.setVisible(false);
		pastHotelInfoPane.setVisible(false);
		personalInfoPane.setVisible(true);
	}
	
	@FXML
	public void showCreditUIPane(){
		mainUIPane.setVisible(false);
		personalInfoPane.setVisible(false);
		orderInfoPane.setVisible(false);
		pastHotelInfoPane.setVisible(false);
		creditUIPane.setVisible(true);
	}
	
	@FXML
	public void showOrderInfoPane(){
		mainUIPane.setVisible(false);
		personalInfoPane.setVisible(false);
		creditUIPane.setVisible(false);
		pastHotelInfoPane.setVisible(false);		
		orderInfoPane.setVisible(true);
	}
	
	@FXML
	public void showPastHotelInfoPane(){
		mainUIPane.setVisible(false);
		personalInfoPane.setVisible(false);
		creditUIPane.setVisible(false);
		orderInfoPane.setVisible(false);		
		pastHotelInfoPane.setVisible(true);
	}
	
	
	
	
	
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(UserMainApp mainApp) {
        this.mainApp = mainApp;
    }
    
}
