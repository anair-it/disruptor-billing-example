package org.anair.billing.disruptor.eventtranslator;

import org.anair.billing.model.BillingRecord;
import org.apache.log4j.Logger;

import com.lmax.disruptor.EventTranslator;

public class BillingEventTranslator implements EventTranslator<BillingRecord>{

	private static final Logger LOG = Logger.getLogger(BillingEventTranslator.class);
	private BillingRecord billingRecord;

	public BillingEventTranslator(BillingRecord billingRecord) {
		this.billingRecord = billingRecord;
	}

	@Override
	public void translateTo(BillingRecord billingRecord, long sequence) {
		billingRecord.setBillingId(this.billingRecord.getBillingId());
		billingRecord.setBillable(this.billingRecord.isBillable());
		billingRecord.setQuantity(this.billingRecord.getQuantity());
		billingRecord.setBillableArtifactName(this.billingRecord.getBillableArtifactName());
		billingRecord.setCustomerName(this.billingRecord.getCustomerName());
		
		if(sequence%10==0){
			LOG.info("Published " + billingRecord.toString() + " to sequence: " + sequence);
		}
	}
	
}
