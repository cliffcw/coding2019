package com.coderising.jvm.assignment.cmd;

import com.coderising.jvm.assignment.clz.ClassFile;
import com.coderising.jvm.assignment.constant.FieldRefInfo;
import com.coderising.jvm.assignment.engine.ExecutionResult;
import com.coderising.jvm.assignment.engine.JavaObject;
import com.coderising.jvm.assignment.engine.StackFrame;


public class GetFieldCmd extends TwoOperandCmd {

	public GetFieldCmd(ClassFile clzFile,String opCode) {
		super(clzFile,opCode);		
	}

	@Override
	public String toString() {
		
		return super.getOperandAsField();
	}

	@Override
	public void execute(StackFrame frame,ExecutionResult result) {
		
		FieldRefInfo fieldRef = (FieldRefInfo)this.getConstantInfo(this.getIndex());
		String fieldName = fieldRef.getFieldName();
		JavaObject jo = frame.getOprandStack().pop();
		JavaObject fieldValue = jo.getFieldValue(fieldName);
		
		frame.getOprandStack().push(fieldValue);
		
		
		
	}
	

}
