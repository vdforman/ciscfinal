package rocket.app.view;

import java.awt.Button;
import java.awt.TextField;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import com.sun.xml.rpc.encoding.Initializable;
import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
import com.sun.xml.ws.org.objectweb.asm.Label;

import eNums.eAction;
import exceptions.RateException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import rocket.app.MainApp;
import rocketCode.Action;
import rocketData.LoanRequest;

public class MortgageController implements Initializable
{


	private MainApp mainApp;
	
	public MortgageController()
	{
		
	}

	@FXML
	private TextField txtIncome;
	
	@FXML
	private TextField txtExpenses;
	
	@FXML
	private TextField txtCreditScore;
	
	@FXML
	private TextField txtHouseCost;
	
	@FXML
	private TextField txtDownPayment;
	
	@FXML
	private ComboBox<String> loanTerm;
	
	@FXML
	private Label labelIncome;
	
	@FXML
	private Label labelExpenses;
	
	@FXML
	private Label labelCreditScore;
	
	@FXML
	private Label labelHouseCost;
	
	@FXML
	private Label labelTerm;
	
	@FXML
	private Label labelDownPayment;
	
	@FXML
	private Button btnPayment;
	
	@FXML
	public Label labelError;
	
	
	
	
	public void setMainApp(MainApp mainApp) 
	{
		this.mainApp = mainApp;
	}
	
	
	
	@FXML
	public void btnCalculatePayment(ActionEvent event)
	{
		Object message = null;
		
		
		Action a = new Action(eAction.CalculatePayment);
		LoanRequest lq = new LoanRequest();
		

		a.setLoanRequest(lq);
		
		lq.setdRate(Double.parseDouble(txtIncome.getText()));
		lq.setdExpenses(Double.parseDouble(txtExpenses.getText()));
		lq.setdAmount(Double.parseDouble(txtHouseCost.getText())-Double.parseDouble(txtDownPayment.getText()));
		if(loanTerm.getSelectionModel().getSelectedItem().toString() == "15 Yrs")
			lq.setiTerm(180);
		else
			lq.setiTerm(360);
		lq.setiCreditScore(Integer.parseInt(txtCreditScore.getText()));
		
			
		mainApp.messageSend(lq);
	}
	
	public void HandleLoanRequestDetails(LoanRequest lRequest)
	{
		
		double PaymentPossible = lRequest.getdRate()*.28;
		double OtherPaymentPossible = (lRequest.getdRate() - lRequest.getdExpenses())*.36;
		double FinalPaymentPossible;
		if(PaymentPossible < OtherPaymentPossible)
		{
			FinalPaymentPossible = PaymentPossible;
		}
		else 
		{
			FinalPaymentPossible = OtherPaymentPossible;
		}
	

		double payment = lRequest.getdPayment();
		String output;
		
		TextField labelError = null;
		
		if(FinalPaymentPossible > payment)
		{
			output = new DecimalFormat("#.##").format(payment);
			String APR = String.valueOf(lRequest.getdRate());
			labelError.setText("Mortgage payment: $" + output + " APR: " + APR + "%");
		}
		else
		{
			output = "House Cost is Too High.";
			labelError.setText(output);
		}

	}
	
	

	ObservableList<String> list = FXCollections.observableArrayList("15 Yrs","30 Yrs");
	
	@Override
	
	public void initialize(URL location, ResourceBundle resources) 
	{
		loanTerm.setItems(list);	
	}
	
	
	public void HandleRateException(RateException e)
	{
		labelError.setText("Your Credit Score is WAAAAY Too Low son!");
		
	}



	@Override
	public void initialize(InternalTypeMappingRegistry arg0) throws Exception 
	{
	
		
	}
}
