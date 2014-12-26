package org.anair.billing.disruptor.eventprocessor;

import org.anair.billing.model.BillingRecord;
import org.anair.billing.service.BillingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.lmax.disruptor.EventHandler;

public class BillingBusinessEventProcessor implements EventHandler<BillingRecord> {

	private static final Logger LOG = Logger.getLogger(BillingBusinessEventProcessor.class);
	private BillingService billingService;
	
	@Override
	public void onEvent(BillingRecord billingRecord, long sequence, boolean endOfBatch)
			throws Exception {
		LOG.trace("Sequence: " + sequence + ". Going to process "+ billingRecord.toString());
		billingService.processBillingRecord(billingRecord);
		if(sequence%10==0){
			LOG.info("Sequence: " + sequence + ". "+ billingRecord.toString());
		}
	}

	@Required
	public void setBillingService(BillingService billingService) {
		this.billingService = billingService;
	}

}
