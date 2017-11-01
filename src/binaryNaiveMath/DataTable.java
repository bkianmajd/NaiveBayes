package binaryNaiveMath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import binaryNaive.IEvent;
import binaryNaive.IFeature;

public class DataTable implements IDataTable{

	//A Naive bayes data table is created that stores the number of occurance of events,
	//features, and feature&event (concatted string)
	public DataTable(List<IEvent> events, List<IFeature> features){
		myTable = new HashMap<String, Integer>();
		for(IEvent event : events){
			myTable.put(event.getName(), new Integer(0));
		}
		
		for(IFeature feature : features){
			myTable.put(feature.getName(), new Integer(0));
		}
		
		//probability of your feature given event is true
		for(IEvent event : events){
			for(IFeature feature : features){
				String stringName = event.getName().concat(feature.getName());
				myTable.put(stringName, new Integer(0));
			}			
		}
	}
	
	@Override
	public int getTotalDataSize(){
		return myTotalDataSize;
	}
	
	@Override
	public int getEventCount(IEvent event) {
		Integer count = myTable.get(event.getName());
		return count;
	}
	@Override
	public int getFeatureCount(IFeature feature) {
		Integer count = myTable.get(feature.getName());
		return count;
	}
	
	@Override
	public int getFeatureCountGivenEvent(IEvent event, IFeature feature) {
		String stringName = event.getName().concat(feature.getName());
		Integer count = myTable.get(stringName);
		return count;
	}	
	
	@Override
	public void addDataSet(List<IEvent> events, List<IFeature> features,int valueToAdd) {
		for(IEvent event : events){
			if(event.isTriggered())
				addToCount(event.getName(), valueToAdd);
		}
		for(IFeature feature : features){
			if(feature.isTriggered())
				addToCount(feature.getName(), valueToAdd);	
		}
		
		for(IEvent event : events){
			for(IFeature feature : features){
				if(feature.isTriggered() && event.isTriggered()){
					String stringName = event.getName().concat(feature.getName());
					addToCount(stringName, valueToAdd);				
				}
			}			
		}
		myTotalDataSize = valueToAdd + myTotalDataSize;
	}
	
	private void addToCount(String name, int valueToAdd){
		Integer count = myTable.get(name);
		count = count + valueToAdd;
		myTable.put( name, count);
	}
		
	private int myTotalDataSize = 0;
	private  Map<String, Integer> myTable;
}
