package unittest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import binaryNaive.IEvent;
import binaryNaive.IFeature;
import binaryNaive.NaiveBayesManager;

// We think as a society servicing others with our skills to help the community. 

public class NaiveBayesManagerTest {

	@Test
	public void testGetProbablity() {
		NaiveBayesManager manager = new NaiveBayesManager(getEvents(), getFeatures());
		
		// Populate the data table
		List<IEvent> events = new ArrayList<IEvent>();
		List<IFeature> features = new ArrayList<IFeature>();
		
		events.add(new GenericEventOrFeature("Banana",100));
		// Add 1000 bananas
		for(int i = 0 ; i < 1000; ++i) {
			manager.addOneToDataSet(events , getFeatures());
		}
		// Add 1000 oranges
		events.clear();
		events.add(new GenericEventOrFeature("Orange",100));
		features.add(new GenericEventOrFeature("Sweet",80));
		features.add(new GenericEventOrFeature("Long",5));
		for(int i = 0 ; i < 1000; ++i) {
			manager.addOneToDataSet(events , features);
		}
		
		// we test that banana is greater than orange
		features.clear();
		features.add(new GenericEventOrFeature("Sweet",100));
		double orangeProbability = manager.getProbablity(features, new GenericEventOrFeature("Orange"), true);

		features.clear();
		features.add(new GenericEventOrFeature("Long",100));
		features.add(new GenericEventOrFeature("Yellow",100));
		double bananaProbability = manager.getProbablity(features, new GenericEventOrFeature("Banana"), true);
		
		assertTrue(bananaProbability > orangeProbability);
	}

	
	private List<IEvent> getEvents() {
		List<IEvent> testEvents = new ArrayList<IEvent>();
		testEvents.add(new GenericEventOrFeature("Banana",90));
		testEvents.add(new GenericEventOrFeature("Orange",10));
		
		return testEvents;
	}
	
	private List<IFeature> getFeatures() { 
		List<IFeature> testFeatures = new ArrayList<IFeature>();
		testFeatures.add(new GenericEventOrFeature("Long",90));
		testFeatures.add(new GenericEventOrFeature("Yellow",90));
		testFeatures.add(new GenericEventOrFeature("Sweet",20));
		
		return testFeatures;
	}
}
