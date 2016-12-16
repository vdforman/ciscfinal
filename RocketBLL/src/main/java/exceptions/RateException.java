package exceptions;


import rocketDomain.RateDomainModel;

public class RateException extends Exception 
{
	private RateDomainModel RDM;
	public RateException(RateDomainModel r)
	{
		this.RDM = r;
	
	}
	public RateDomainModel getRDM()
	{
		return this.RDM;
	
	}
}
