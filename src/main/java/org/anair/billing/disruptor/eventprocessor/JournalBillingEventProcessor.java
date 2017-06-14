package org.anair.billing.disruptor.eventprocessor;

import org.anair.billing.model.BillingRecord;
import org.anair.billing.service.BillingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.lmax.disruptor.EventHandler;

public class JournalBillingEventProcessor implements EventHandler<BillingRecord> {

	private static final Logger LOG = LoggerFactory.getLogger(JournalBillingEventProcessor.class);
	private BillingService billingService;
	
	@Override
	public void onEvent(BillingRecord billingRecord, long sequence, boolean endOfBatch)
			throws Exception {
		LOG.trace("Sequence: {}. Going to process {}",sequence, billingRecord.toString());
		billingService.journalBillingRecord(billingRecord);
		if(sequence%10==0){
			LOG.info("Sequence: {}. {}",sequence, billingRecord.toString());
		}
	}

	@Required
	public void setBillingService(BillingService billingService) {
		this.billingService = billingService;
	}

}
