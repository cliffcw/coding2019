package com.coderising.jvm.assignment.cmd;

import com.coderising.jvm.assignment.clz.ClassFile;
import com.coderising.jvm.assignment.constant.FieldRefInfo;
import com.coderising.jvm.assignment.engine.ExecutionResult;
import com.coderising.jvm.assignment.engine.Heap;
import com.coderising.jvm.assignment.engine.JavaObject;
import com.coderising.jvm.assignment.engine.StackFrame;


public class GetStaticFieldCmd extends TwoOperandCmd {

	public GetStaticFieldCmd(ClassFile clzFile, String opCode) {
		super(clzFile,opCode);
		
	}

	@Override
	public String toString() {
		
		return super.getOperandAsField();
	}
	
	@Override	
	public void execute(StackFrame frame, ExecutionResult result) {
		FieldRefInfo info = (FieldRefInfo)this.getConstantInfo(this.getIndex());
		String className = info.getClassName();
		String fieldName = info.getFieldName();
		String fieldType = info.getFieldType();
		
		if("java/lang/System".equals(className) 
				&& "out".equals(fieldName) 
				&& "Ljava/io/PrintStream;".equals(fieldType)){
			JavaObject jo = Heap.getInstance().newObject(className);
			frame.getOprandStack().push(jo);
		}
		//TODO 处理非System.out的情况
	}
}
