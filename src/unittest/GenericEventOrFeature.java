package unittest;

import java.util.Random;

import binaryNaive.IEvent;
import binaryNaive.IFeature;

public class GenericEventOrFeature implements IEvent, IFeature{

	public GenericEventOrFeature(String name) {
		myName = name;
	}

	
	public GenericEventOrFeature(String name, int probabilityTrue) {
		this(name);
		myProbabilityTrue = probabilityTrue;
	}

	@Override
	public String getName() {
		return myName;
	}

	@Override
	public boolean isTriggered() {
		int randomValue = getRandom();
		if(myProbabilityTrue > randomValue)
			return true;
		
		return false;
	}
	
	private int getRandom(){
		final int lowerRange = 0;
		final int higherRange = 100;
		Random rand = new Random();
		int  n = rand.nextInt(higherRange) + lowerRange;
		return n;
	}
	
	private String myName;
	private int myProbabilityTrue;
}
