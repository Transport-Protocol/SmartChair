package com.hawhamburg.sg.debug;

import java.util.List;

import com.hawhamburg.sg.data.AbstractValue;

@FunctionalInterface
interface RandomDataGeneratorInterface {
	
	List<AbstractValue> invoke();
	
}
