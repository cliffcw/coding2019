package com.coderising.jvm.assignment.cmd;

import com.coderising.jvm.assignment.clz.ClassFile;
import com.coderising.jvm.assignment.constant.ConstantInfo;
import com.coderising.jvm.assignment.constant.ConstantPool;
import com.coderising.jvm.assignment.constant.StringInfo;
import com.coderising.jvm.assignment.engine.ExecutionResult;
import com.coderising.jvm.assignment.engine.Heap;
import com.coderising.jvm.assignment.engine.JavaObject;
import com.coderising.jvm.assignment.engine.StackFrame;

public class LdcCmd extends OneOperandCmd {

	public LdcCmd(ClassFile clzFile, String opCode) {
		super(clzFile,opCode);		
	}
	
	@Override
	public String toString() {
		
		ConstantInfo info = getConstantInfo(this.getOperand());
		
		String value = "TBD";
		if(info instanceof StringInfo){
			StringInfo strInfo = (StringInfo)info;
			value = strInfo.toString();
		}
		
		return this.getOffset()+":"+this.getOpCode()+" " + this.getReadableCodeText() + " "+  value;
		
	}
	public void  execute(StackFrame frame, ExecutionResult result){
		
		ConstantPool pool = this.getConstantPool();
		ConstantInfo info = (ConstantInfo)pool.getConstantInfo(this.getOperand());
		
		if(info instanceof StringInfo){
			StringInfo strInfo = (StringInfo)info;
			String value = strInfo.toString();
			JavaObject jo = Heap.getInstance().newString(value);
			frame.getOprandStack().push(jo);
		}
		else{
			//TBD 处理其他类型
			throw new RuntimeException("Only support StringInfo constant");
		}
		
		
	}
}
