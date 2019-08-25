package com.coderising.jvm.assignment.cmd;

import com.coderising.jvm.assignment.clz.ClassFile;
import com.coderising.jvm.assignment.engine.ExecutionResult;
import com.coderising.jvm.assignment.engine.Heap;
import com.coderising.jvm.assignment.engine.JavaObject;
import com.coderising.jvm.assignment.engine.StackFrame;

public class IncrementCmd extends TwoOperandCmd {

	public IncrementCmd(ClassFile clzFile, String opCode) {
		super(clzFile, opCode);
		
	}

	@Override
	public String toString() {
		
		return this.getOffset()+":"+this.getOpCode()+ " " +this.getReadableCodeText();
	}

	@Override
	public void execute(StackFrame frame, ExecutionResult result) {
		
		int index = this.getOprand1();
		
		int constValue = this.getOprand2();
		
		int currentValue = frame.getLocalVariableValue(index).getIntValue();
		
		JavaObject jo = Heap.getInstance().newInt(constValue+currentValue);
		
		frame.setLocalVariableValue(index, jo);
		

	}

}