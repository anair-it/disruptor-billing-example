package org.anair.datastream.disruptor.eventprocessor;

import org.anair.datastream.model.DataStream;
import org.anair.datastream.service.DatastreamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.lmax.disruptor.EventHandler;

public class ProcessADataStreamEventProcessor implements EventHandler<DataStream> {

	private static final Logger LOG = LoggerFactory.getLogger(ProcessADataStreamEventProcessor.class);
	private DatastreamService dataStreamService;
	
	@Override
	public void onEvent(DataStream dataStream, long sequence, boolean endOfBatch)
			throws Exception {
		LOG.trace("Sequence: {}. Going to process {}",sequence, dataStream.toString());
		dataStreamService.processDatastream_A(dataStream);
		if(sequence%10==0){
			LOG.info("Sequence: {}. {}",sequence, dataStream.toString());
		}
	}

	@Required
	public void setDataStreamService(DatastreamService dataStreamService) {
		this.dataStreamService = dataStreamService;
	}

}
