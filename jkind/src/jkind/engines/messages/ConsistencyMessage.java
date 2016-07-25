package jkind.engines.messages;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.engines.messages.Message;
import jkind.engines.messages.MessageHandler;
import jkind.lustre.Expr;
import jkind.results.Counterexample;
import jkind.util.Tuple;
import jkind.util.Util;

public class ConsistencyMessage extends Message {
	public ValidMessage vm;
	private ConsistencyResults consResult;

	public ConsistencyMessage(ValidMessage vm) {
		this.vm = vm;
	}
	
	public void setConsistencyMsgWithCex (Counterexample cex){
		this.consResult = new ConsistencyResults(cex);
	}
	
	public void setConsistencyMsgWithUc (Set<String> uc){
		this.consResult = new ConsistencyResults(uc);
	}
	
	public void setNoInConsistency (){
		this.consResult = new ConsistencyResults();
	}
	
	public Collection getConsistencyMsg(){
		return consResult.getConsistencyResult();
	}
	
	public ConsistencyStatus getStatus(){
		return consResult.status;
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
	
	public enum ConsistencyStatus {NotSetYet, CEX, UC, CONSISTENT;};
	
	private class ConsistencyResults {
		private final Counterexample cex;
		private final Set<String> uc;
		private ConsistencyStatus status = ConsistencyStatus.NotSetYet;
		
		private ConsistencyResults(Counterexample cex){
			this.status = ConsistencyStatus.CEX;
			this.cex = cex;
			this.uc = null;
		}
		
		private ConsistencyResults (Set<String> uc){
			this.status = ConsistencyStatus.UC;
			this.uc = uc;
			this.cex = null;
		}
		
		private ConsistencyResults(){
			this.status = ConsistencyStatus.CONSISTENT;
			cex = null;
			uc = null;
		}
		
		private Collection getConsistencyResult(){
			switch(this.status){
				case NotSetYet: 
					return null;
				case CEX:
					List<Counterexample> ret = new ArrayList<Counterexample>();
					ret.add(cex);
					return ret;
				case UC:
					return uc;
				case CONSISTENT:
					return null;
				}
			return null;
		}
	}
}
