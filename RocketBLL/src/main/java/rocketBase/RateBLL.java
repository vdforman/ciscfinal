package rocketBase;


import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.*;

import rocketDomain.RateDomainModel;
import exceptions.RateException;
public class RateBLL 
{

	private static RateDAL _RateDAL = new RateDAL();
	
	public static double getRate(int GivenCreditScore) throws RateException 
	{
		ArrayList<RateDomainModel> Rates = RateDAL.getAllRates();
		for(int i = 0; i < Rates.size(); i++)
		{
			if(Rates.get(i).getiMinCreditScore() <= GivenCreditScore)
			{
				return(Rates.get(i).getdInterestRate());
			}
		}
		throw new RateException(null);
	}

	
	public static double getPayment(double r, double n, double p, double f, boolean t)
	{	
		return FinanceLib.pmt(r, n, p, f, t);
	}
}