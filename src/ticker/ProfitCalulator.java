package ticker;

public class ProfitCalulator {

	private Double markUpFees = 4.0; // %

	private Double cexChargePercent = 3.5; // in%
	private Double cexDepositCharge = 0.25; // USD
	private Double cexLimitChargePercent = 0.2; // in%

	private Double btcTransCharge = 0.001; // in btc

	private Double zpSellCharge = 0.2; // in%
	private Double zpWithdrawalCharge = 10.0; // rs

	/*
	 * Input in USD , CP in USD ZP in INR
	 * 
	 */

	public Double calculateProfit(Double input, Double cp, Double zp, Double usdToInr) {

		Double afterMarkUp = input * (1 - (markUpFees / 100));
		Double afterCexDepositCharges = afterMarkUp * (1 - (cexChargePercent / 100)) - cexDepositCharge;
		Double afterLimitCharge = afterCexDepositCharges * (1 - (cexLimitChargePercent / 100));
		Double btcBought = afterLimitCharge / cp;

		Double finalCexBtc = btcBought - btcTransCharge;

		// -----------------------------AFTER TRANSFER

		Double zpINR = finalCexBtc * zp;

		Double zpInrAfterTransCharge = zpINR * (1 - (zpSellCharge / 100));

		Double finalBankAmount = zpInrAfterTransCharge - zpWithdrawalCharge;

		return (finalBankAmount - input * usdToInr);

	}

	public Double calculateBreakevenAmount(Double input, Double cp, Double zp, Double usdToInr) {

		Double afterMarkUp = input * (1 - (markUpFees / 100));
		Double afterCexDepositCharges = afterMarkUp * (1 - (cexChargePercent / 100)) - cexDepositCharge;
		Double afterLimitCharge = afterCexDepositCharges * (1 - (cexLimitChargePercent / 100));
		Double btcBought = afterLimitCharge / cp;

		Double finalCexBtc = btcBought - btcTransCharge;

		// -----------------------------AFTER TRANSFER

		Double zpInrAfterTransCharge = ((input * usdToInr) + zpWithdrawalCharge);

		Double reqZpInr = zpInrAfterTransCharge / (1 - (zpSellCharge / 100));

		Double reqZp = reqZpInr / finalCexBtc;

		return (reqZp);

	}

}
