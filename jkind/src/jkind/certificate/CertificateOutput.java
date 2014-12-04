package jkind.certificate;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

import jkind.ExitCodes;
import jkind.JKindException;
import jkind.Output;
import jkind.lustre.Expr;
import jkind.lustre.Program;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder;

public class CertificateOutput {
	private Writer out;
	private Set<String> seenInvariants = new HashSet<>();
	private NodeBuilder builder = new NodeBuilder("cert");

	public CertificateOutput(String outputFilename) {
		try {
			OutputStream stream = new GZIPOutputStream(new FileOutputStream(outputFilename));
			this.out = new BufferedWriter(new OutputStreamWriter(stream));
		} catch (IOException e) {
			Output.fatal(ExitCodes.INVALID_OPTIONS,
					"unable to open certificate file for writing: " + e.getMessage());
		}
	}

	public void addVarDecls(List<VarDecl> varDecls) {
		builder.addLocals(varDecls);
	}

	public void addInvariants(List<Expr> invariants) {
		for (Expr inv : invariants) {
			String str = inv.toString();
			if (seenInvariants.add(str)) {
				builder.addAssertion(inv);
			}
		}
	}

	public void write() {
		try {
			out.write(new Program(builder.build()).toString());
			out.close();
		} catch (IOException e) {
			throw new JKindException("Unable to write certificate", e);
		}
	}
}
