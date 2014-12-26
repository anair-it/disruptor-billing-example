package org.anair.billing.message.listener;

import org.anair.billing.model.BillingRecord;
import org.anair.disruptor.publisher.EventPublisher;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

public class BillingMessageListener {
	private static final Logger LOG = Logger.getLogger(BillingMessageListener.class);
	
	private EventPublisher<BillingRecord> billingEventPublisher;
	
	public void inMessage(String number){
		if(StringUtils.isBlank(number) || !NumberUtils.isDigits(number.trim())){
			LOG.error("Enter only integer number");
			return;
		}
		int inboundMessageCount = Integer.parseInt(number.trim());
		for(int i=0;i<inboundMessageCount;i++){
			BillingRecord billingRecord = unmarshallBillingRecord(i);
			billingEventPublisher.publish(billingRecord);
		}
		LOG.debug("Processed "+ inboundMessageCount + " inbound billing records");
	}
	
	private BillingRecord unmarshallBillingRecord(int i){
		BillingRecord billingRecord = new BillingRecord();
		billingRecord.setBillable(true);
		billingRecord.setBillableArtifactName("artifact name " + i);
		billingRecord.setBillingId(i);
		if(i%10==0){
			billingRecord.setCustomerName("anair" + i);
		}else{
			billingRecord.setCustomerName("customer name " + i);
		}
		billingRecord.setQuantity(i+10);
		return billingRecord;
	}

	public void setEventPublisher(EventPublisher<BillingRecord> billingEventPublisher) {
		this.billingEventPublisher = billingEventPublisher;
	}
}
