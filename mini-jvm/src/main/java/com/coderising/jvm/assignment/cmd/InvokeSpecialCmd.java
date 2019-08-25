package com.coderising.jvm.assignment.cmd;

import com.coderising.jvm.assignment.clz.ClassFile;
import com.coderising.jvm.assignment.constant.MethodRefInfo;
import com.coderising.jvm.assignment.engine.ExecutionResult;
import com.coderising.jvm.assignment.engine.MethodArea;
import com.coderising.jvm.assignment.engine.StackFrame;
import com.coderising.jvm.assignment.method.Method;


public class InvokeSpecialCmd extends TwoOperandCmd {

	public InvokeSpecialCmd(ClassFile clzFile,String opCode) {
		super(clzFile,opCode);
		
	}

	@Override
	public String toString() {
		
		return super.getOperandAsMethod();
	}
	@Override
	public void execute(StackFrame frame,ExecutionResult result) {		
		
				
		MethodRefInfo methodRefInfo = (MethodRefInfo)this.getConstantInfo(this.getIndex());
		
		// 我们不用实现jang.lang.Object 的init方法
		if(methodRefInfo.getClassName().equals("java/lang/Object") 
				&& methodRefInfo.getMethodName().equals("<init>")){
			return ;
			
		}
		Method nextMethod = MethodArea.getInstance().getMethod(methodRefInfo);
		
		
		result.setNextAction(ExecutionResult.PAUSE_AND_RUN_NEW_FRAME);
		result.setNextMethod(nextMethod);
		
		
		
	}
	

}
