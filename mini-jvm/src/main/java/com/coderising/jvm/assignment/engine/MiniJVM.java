package com.coderising.jvm.assignment.engine;

import com.coderising.jvm.assignment.loader.ClassFileLoader;

import java.io.FileNotFoundException;
import java.io.IOException;


public class MiniJVM {
	
	public void run(String[]classPaths , String className) throws FileNotFoundException, IOException{
		
		ClassFileLoader loader = new ClassFileLoader();
		for(int i=0;i<classPaths.length ; i++){
			loader.addClassPath(classPaths[i]);
		}
		
		MethodArea methodArea= MethodArea.getInstance();
		
		methodArea.setClassFileLoader(loader);
		
		ExecutorEngine engine = new ExecutorEngine();
		
		className = className.replace(".", "/");
		
		engine.execute(methodArea.getMainMethod(className));
	}
	
}
