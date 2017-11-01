package binaryNaiveMath;

import java.util.List;

import binaryNaive.IEvent;
import binaryNaive.IFeature;

public interface IDataTable {
	
	int getEventCount(IEvent event);
	int getFeatureCount(IFeature feature);
	int getFeatureCountGivenEvent(IEvent event, IFeature feature);
	int getTotalDataSize();
	void addDataSet(List<IEvent> events, List<IFeature> features,int valueToAdd);
}
