package jkind.engines;

import java.util.ArrayList;
import java.util.List;
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
		usableVarDecls.removeIf(vd -> !baseTypesMatch(map.get(vd.id), vd.type));
	}
	
	private boolean baseTypesMatch(Type type1, Type type2) {
		if (type1 == null || type2 == null) {
			return false;
		}
		return Util.getName(type1).equals(Util.getName(type2));
	}

	private void scrubInvariants() {
		VariableUsageChecker checker = new VariableUsageChecker(usableVarDecls);
		potentialInvariants.removeIf(inv -> !checker.check(inv));
	}

	@Override
	protected Invariant createInitialInvariant() {
		return new ListInvariant(potentialInvariants);
	}
}
