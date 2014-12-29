package org.anair.datastream.message.listener;

import org.anair.datastream.model.DataStream;
import org.anair.disruptor.publisher.EventPublisher;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class DataStreamMessageListener {
	private static final Logger LOG = Logger.getLogger(DataStreamMessageListener.class);
	
	private EventPublisher<DataStream> dataStreamEventPublisher;
	
	public void inMessage(String number){
		if(StringUtils.isBlank(number) || !StringUtils.isNumeric(number.trim())){
			LOG.error("Enter a valid long number");
			return;
		}
		long inboundMessageCount = Long.parseLong(number.trim());
		for(long l=0;l<inboundMessageCount;l++){
			DataStream dataStream = unmarshallDataStream(l);
			dataStreamEventPublisher.publish(dataStream);
		}
		LOG.debug("Processed "+ inboundMessageCount + " inbound data stream records");
	}
	
	private DataStream unmarshallDataStream(long i){
		DataStream dataStream = new DataStream();
		dataStream.setDataStreamId(i);
		dataStream.setDataStreamType("type");
		dataStream.setDataSource("mainframe");
		dataStream.setDataTarget("midrange");
		dataStream.setDataStream("blah blah blah blah data");
		return dataStream;
	}

	public void setEventPublisher(EventPublisher<DataStream> dataStreamEventPublisher) {
		this.dataStreamEventPublisher = dataStreamEventPublisher;
	}
}
