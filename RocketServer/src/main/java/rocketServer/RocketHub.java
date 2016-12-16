package rocketServer;

import java.io.IOException;

import netgame.common.Hub;
import rocketBase.RateBLL;
import rocketData.LoanRequest;


public class RocketHub extends Hub 
{

	private RateBLL _RateBLL = new RateBLL();
	
	public RocketHub(int port) throws IOException 
	{
		super(port);
		
	}

	@Override
	protected void messageReceived(int ClientID, Object message) 
	{
		System.out.println("Message Received by Hub");
		
		if (message instanceof LoanRequest) 
		{
			resetOutput();
			
			LoanRequest lq = (LoanRequest) message;
			
			try 
			{
				int CS = lq.getiCreditScore();
				lq.setdRate(RateBLL.getRate(CS));
				
			} 
			catch (RateException E) 
			{
				sendToAll(E);
				
			}

			
			double payment = RateBLL.getPayment(lq.getdAmount(), lq.getiTerm(), lq.getdAmount(), 0, false);
			lq.setdPayment(payment);
	
			sendToAll(lq);
		}
	}
}
