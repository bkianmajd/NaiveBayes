package binaryNaive;

import java.util.ArrayList;
import java.util.List;
import binaryNaiveMath.DataTable;

// Coding is a personal interaction with the brain and the strokes of your finger tips

//This class is responsible for making a table of data
//http://blog.aylien.com/naive-bayes-for-dummies-a-simple-explanation/
public class NaiveBayesManager {
	
	public NaiveBayesManager(List<IEvent> fullEventList, List<IFeature> fullFeatureList){
		myDataTable = new DataTable(fullEventList, fullFeatureList);
	}
	
	
	public void addOneToDataSet(List<IEvent> events, List<IFeature> features){
		final int valueToAdd = 1;
		myDataTable.addDataSet(events, features, valueToAdd);
	}
	
	public void removeOneFromDataSet(List<IEvent> events, List<IFeature> features){
		final int valueToAdd = -1;
		myDataTable.addDataSet(events, features, valueToAdd);
	}
	
	// Bernoulli naive Bayes (Wikipedia)
	// What is the probability of the event is there or not given these features
	public double getProbablity(List<IFeature> features, IEvent eventClass, boolean eventClassIsTrue){
		List<IFeature> featureList = shedFeatures(features);
		
		double numberOfEvents = myDataTable.getEventCount(eventClass);
		double probability = 0;
		if(numberOfEvents > 0) {
			//this is incorrect
			if(eventClassIsTrue){
				probability = this.getPriorEventGivenTrue(eventClass) * getLikelihoodGivenEventTrue(featureList, eventClass) / 
						getEvidence(featureList, eventClass);
			} else {
				probability = this.getPriorEventGivenFalse(eventClass) * getLikelihoodGivenEventFalse(featureList, eventClass) /
						getEvidence(featureList, eventClass);
			}
		}else
			probability = 0;
		return probability;
	}

	private List<IFeature> shedFeatures(List<IFeature> featureList){
		List<IFeature> retFeatures = new ArrayList<IFeature>();
		for(int i = 0 ; i < featureList.size(); ++i)
			if(featureList.get(i).isTriggered())
				retFeatures.add(featureList.get(i));
		return retFeatures;
	}
		
	private double getEvidence(List<IFeature> featureList, IEvent event){
		double numberOfEvents = myDataTable.getEventCount(event);
		double probabilityOfEventTrue = numberOfEvents / myDataTable.getTotalDataSize();
		
		double evidence = probabilityOfEventTrue * getLikelihoodGivenEventTrue(featureList, event) + 
		((1 - probabilityOfEventTrue) * getLikelihoodGivenEventFalse(featureList, event));
		return evidence;
	}
	
	private double getLikelihoodGivenEventTrue(List<IFeature> featureList, IEvent eventClass){
		double likelihoodGivenEventTrue = 1;
		double laplaceSmoother = 1/myDataTable.getTotalDataSize();
		
		for( int i = 0; i < featureList.size(); ++i){
			IFeature feature = featureList.get(i);
			double featureOccuranceGivenEvent = myDataTable.getFeatureCountGivenEvent(eventClass, feature);
			double numberOfEvents = myDataTable.getEventCount(eventClass);
			double observance = featureOccuranceGivenEvent/((double)numberOfEvents); //(80% of bananas are long)
			likelihoodGivenEventTrue = likelihoodGivenEventTrue * (observance + laplaceSmoother);
		}
		return likelihoodGivenEventTrue;
	}

	//What is the probability that these feature are there given the event did not happen
	private double getLikelihoodGivenEventFalse(List<IFeature> featureList, IEvent eventClass){
		double likelihoodGivenEventFalse = 1;
		double laplaceSmoother = 1/myDataTable.getTotalDataSize();
		
		for( int i = 0; i < featureList.size(); ++i){
			IFeature feature = featureList.get(i);
			double featureOccuranceGivenEventFalse = 
					myDataTable.getFeatureCount(feature) - myDataTable.getFeatureCountGivenEvent(eventClass, feature);
			double numberOfEventsFalse = myDataTable.getTotalDataSize() - myDataTable.getEventCount(eventClass);
			double observance = featureOccuranceGivenEventFalse/(numberOfEventsFalse); //(80% of bananas are long)
			likelihoodGivenEventFalse = likelihoodGivenEventFalse * (observance + laplaceSmoother);
		}
		return likelihoodGivenEventFalse;
	}
	
	// P(Event happining) = #observed / total
	private double getPriorEventGivenTrue(IEvent eventClass){
		double eventObserved = myDataTable.getEventCount(eventClass)/
				((double)myDataTable.getTotalDataSize());		return eventObserved;
	}
	
	private double getPriorEventGivenFalse(IEvent eventClass){
		double eventDidntHappen = (myDataTable.getTotalDataSize() - myDataTable.getEventCount(eventClass))/
				((double)myDataTable.getTotalDataSize());
		return eventDidntHappen;
	}
	
	private DataTable myDataTable;
}
