package org.anair.datastream.service;

import org.anair.datastream.model.DataStream;

public interface DatastreamService {
	static final long delay = 555555L;
	
	void journalDatastream(DataStream dataStream);
	
	void processDatastream_A(DataStream dataStream);
	
	void processDatastream_B(DataStream dataStream);
	
	DataStream formatDatastream(DataStream dataStream);
}
