package com.coderising.jvm.assignment.cmd;

import com.coderising.jvm.assignment.clz.ClassFile;
import com.coderising.jvm.assignment.constant.ClassInfo;
import com.coderising.jvm.assignment.engine.ExecutionResult;
import com.coderising.jvm.assignment.engine.Heap;
import com.coderising.jvm.assignment.engine.JavaObject;
import com.coderising.jvm.assignment.engine.StackFrame;

public class NewObjectCmd extends TwoOperandCmd{
	
	public NewObjectCmd(ClassFile clzFile, String opCode){
		super(clzFile,opCode);
	}

	@Override
	public String toString() {
		
		return super.getOperandAsClassInfo();
	}
	public void execute(StackFrame frame,ExecutionResult result){
		
		int index = this.getIndex();
		
		ClassInfo info = (ClassInfo)this.getConstantInfo(index);
		
		String clzName = info.getClassName();
		
		//在Java堆上创建一个实例
		JavaObject jo = Heap.getInstance().newObject(clzName);
		
		frame.getOprandStack().push(jo);
		
		
		
	}
	
}
