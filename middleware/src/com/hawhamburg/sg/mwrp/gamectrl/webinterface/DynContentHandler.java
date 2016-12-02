package com.hawhamburg.sg.mwrp.gamectrl.webinterface;

@FunctionalInterface
interface DynContentHandler {
	String invoke(String key);
}
