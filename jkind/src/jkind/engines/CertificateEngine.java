package jkind.engines;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import jkind.JKindSettings;
import jkind.certificate.CertificateInput;
import jkind.certificate.VariableUsageChecker;
import jkind.engines.invariant.AbstractInvariantGenerationEngine;
import jkind.engines.invariant.Invariant;
import jkind.engines.invariant.ListInvariant;
import jkind.lustre.Expr;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.translation.Specification;
import jkind.util.Util;

public class CertificateEngine extends AbstractInvariantGenerationEngine {
	private final List<VarDecl> usableVarDecls;
	private final List<Expr> potentialInvariants;
	
	public CertificateEngine(Specification spec, JKindSettings settings, Director director,
			CertificateInput certificateInput) {
		super("certificate", spec, settings, director);
		
		this.usableVarDecls = new ArrayList<>(certificateInput.getVarDecls());
		this.potentialInvariants = new ArrayList<>(certificateInput.getInvariants());
		
		scrubVarDecls();
		scrubInvariants();
	}

	private void scrubVarDecls() {
		Map<String, Type> map = Util.getTypeMap(spec.node);
		ListIterator<VarDecl> iterator = usableVarDecls.listIterator();
		while (iterator.hasNext()) {
			VarDecl vd = iterator.next();
			Type type = map.get(vd.id);
			if (type == null || !Util.getName(type).equals(Util.getName(vd.type))) {
				iterator.remove();
			}
		}
	}

	private void scrubInvariants() {
		ListIterator<Expr> iterator = potentialInvariants.listIterator();
		VariableUsageChecker checker = new VariableUsageChecker(usableVarDecls);
		while (iterator.hasNext()) {
			Expr inv = iterator.next();
			if (!checker.check(inv)) {
				iterator.remove();
			}
		}
	}

	@Override
	protected Invariant createInitialInvariant() {
		return new ListInvariant(potentialInvariants);
	}
}
