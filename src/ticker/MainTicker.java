package ticker;

import java.util.Calendar;

public class MainTicker {

	private static Double initialInvestment = 1000D; // in USD

	public static void main(String[] args) {

		System.out.println(" Ticker Starting up : ");

		MainTicker mainTicker = new MainTicker();
		mainTicker.infiniteMethod();

		System.out.println("\n Ticker Shutting Down \n\n");

	}

	private Double arbitrageExecutor(Double previousSentProfit) {

		ApiGet apiGet = new ApiGet();
		ProfitCalulator profitCalculator = new ProfitCalulator();
		PostMail postMail = new PostMail();

		Double cexPriceUsd = apiGet.getCexPrice();
		Double zebpayPriceInr = apiGet.getZebpayPrice();
		Double usdToInr = apiGet.getUsdForex();

		Double profit = profitCalculator.calculateProfit(initialInvestment, cexPriceUsd, zebpayPriceInr, usdToInr);
		Double breakEven = profitCalculator.calculateBreakevenAmount(initialInvestment, cexPriceUsd, zebpayPriceInr,
				usdToInr);

		SendGridEntity mailEntity = new SendGridEntity(profit, breakEven, zebpayPriceInr, cexPriceUsd, usdToInr);

		if (ifSendMail(profit, previousSentProfit)) {
			postMail.sendGet(profit.toString(), mailEntity);
			System.out.println("\n Email sent");
			return profit;
		} else {
			System.out.println("\n Email not sent");
		}

		return previousSentProfit;
	}

	private synchronized void infiniteMethod() {

		Double previousSentProfit = 1D;

		while (true) {
			System.out.println("always running program ==> " + Calendar.getInstance().getTime());
			try {
				previousSentProfit = this.arbitrageExecutor(previousSentProfit);
				this.wait(5*60*1000); //5 min wait
			} catch (InterruptedException e) {
				System.out.println(" Wait execpion : "+e.getMessage());
				
			}
		}

	}

	private boolean ifSendMail(Double profit, Double previousSentProfit) {

		Double profitDiffForMail = 15D; // in percentage
		Double lossDiffForMail = 10D; // in percent

		if (previousSentProfit > 4500) {
			Double presentDiff = ((profit - previousSentProfit) / previousSentProfit) * 100D;
			if (profit > 4500) {
				if (presentDiff > 0 && presentDiff > profitDiffForMail)
					return true;
				else if (presentDiff < 0 && Math.abs(presentDiff) > lossDiffForMail) {
					return true;
				}
			} else {
				return true;
			}
		}
		else {
			if (profit > 4500) {
				return true;
			}
		}

		return false;
	}

}
