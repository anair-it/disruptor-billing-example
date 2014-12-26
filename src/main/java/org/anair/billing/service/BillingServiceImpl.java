package org.anair.billing.service;

import org.anair.billing.model.BillingRecord;
import org.apache.log4j.Logger;

public class BillingServiceImpl implements BillingService {
	private static final Logger LOG = Logger.getLogger(BillingServiceImpl.class);
	
	@Override
	public void processBillingRecord(BillingRecord billingRecord) {
		for(long l=0;l<delay;l++){
			//Faking a long running billing business logic
		}
		LOG.debug("Billing record is processed: " + billingRecord.toString());
	}

	@Override
	public void processCorporateBillingRecord(BillingRecord billingRecord) {
		for(long l=0;l<delay;l++){
			//Faking a long running billing business logic
		}
		LOG.debug("Corporate Billing record is processed: " + billingRecord.toString());
	}

	@Override
	public void processCustomerSpecificBillingRecord(BillingRecord billingRecord) {
		for(int i=0;i<delay;i++){
			//Faking a long running billing business logic
		}
		LOG.debug("Customer specific Billing record is processed: " + billingRecord.toString());
	}

	@Override
	public void validateBillingRecord(BillingRecord billingRecord) {
		for(int i=0;i<delay;i++){
			//Faking a long running billing business logic
		}
		LOG.debug("Validate Billing record is processed: " + billingRecord.toString());		
	}

	@Override
	public void formatBillingRecord(BillingRecord billingRecord) {
		for(int i=0;i<delay;i++){
			//Faking a long running billing business logic
		}
		LOG.debug("Format Billing record is processed: " + billingRecord.toString());		
	}

	@Override
	public void journalBillingRecord(BillingRecord billingRecord) {
		for(int i=0;i<delay;i++){
			//Faking a long running billing business logic
		}
		LOG.debug("Journal Billing record is processed: " + billingRecord.toString());		
	}

}
