package jkind.certificate;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;

import jkind.ExitCodes;
import jkind.Main;
import jkind.Output;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.VarDecl;

import org.antlr.v4.runtime.ANTLRInputStream;

public class CertificateInput {
	private final List<VarDecl> varDecls;
	private final List<Expr> invariants;

	public CertificateInput(String inputFilename) {
		try (InputStream in = new GZIPInputStream(new FileInputStream(inputFilename))) {
			Program program = Main.parseLustre(new ANTLRInputStream(in));
			Node main = program.getMainNode();
			this.varDecls = main.locals;
			this.invariants = main.assertions;
		} catch (Exception e) {
			Output.fatal(ExitCodes.INVALID_OPTIONS,
					"Unable to parse certificate: " + e.getMessage());
			throw new IllegalStateException();
		}
	}

	public List<VarDecl> getVarDecls() {
		return varDecls;
	}

	public List<Expr> getInvariants() {
		return invariants;
	}
}
