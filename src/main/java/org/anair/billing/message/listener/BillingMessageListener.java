package org.anair.billing.message.listener;

import org.anair.billing.model.BillingRecord;
import org.anair.disruptor.publisher.EventPublisher;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class BillingMessageListener {
	private static final Logger LOG = Logger.getLogger(BillingMessageListener.class);
	
	private EventPublisher<BillingRecord> billingEventPublisher;
	
	public void inMessage(String number){
		if(StringUtils.isBlank(number) || !StringUtils.isNumeric(number.trim())){
			LOG.error("Enter a valid long number");
			return;
		}
		long inboundMessageCount = Long.parseLong(number.trim());
		for(long l=0;l<inboundMessageCount;l++){
			BillingRecord billingRecord = unmarshallBillingRecord(l);
			billingEventPublisher.publish(billingRecord);
		}
		LOG.debug("Processed "+ inboundMessageCount + " inbound billing records");
	}
	
	private BillingRecord unmarshallBillingRecord(long i){
		BillingRecord billingRecord = new BillingRecord();
		billingRecord.setBillable(true);
		billingRecord.setBillableArtifactName("artifact name " + i);
		billingRecord.setBillingId(i);
		if(i%10==0){
			billingRecord.setCustomerName("anair" + i);
		}else{
			billingRecord.setCustomerName("customer name " + i);
		}
		billingRecord.setQuantity(10);
		return billingRecord;
	}

	public void setEventPublisher(EventPublisher<BillingRecord> billingEventPublisher) {
		this.billingEventPublisher = billingEventPublisher;
	}
}
