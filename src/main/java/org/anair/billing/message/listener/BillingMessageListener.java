package org.anair.billing.message.listener;

import org.anair.billing.model.BillingRecord;
import org.anair.disruptor.publisher.EventPublisher;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BillingMessageListener {
	private static final Logger LOG = LoggerFactory.getLogger(BillingMessageListener.class);
	
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
		LOG.debug("Processed {} inbound billing records", inboundMessageCount);
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
