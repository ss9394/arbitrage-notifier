package ticker;

public class MainTicker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(" TICKER STARTING : ");
		
		ApiGet apiGet = new ApiGet();
		ProfitCalulator pc = new ProfitCalulator();
		PostMail pm = new PostMail();
		
		Double cp = 17349.51D;//apiGet.getCexPrice();
		Double zp = apiGet.getZebpayPrice();
		Double usdToInr = apiGet.getUsdForex();
		
		Double profit = pc.calculateProfit(830D,cp,zp,usdToInr);
		Double breakEven = pc.calculateBreakevenAmount(830D,cp,zp,usdToInr);

		SendGridEntity mailEntity = new SendGridEntity(profit,breakEven,zp,cp,usdToInr);
		
		if(profit > 4500) {
			//pm.sendGet(profit.toString(),mailEntity);
		}
		else {
			System.out.println("\n Email not sent");
		}
		
		System.out.println("\n Shutting Down \n\n");
		
	}

}
