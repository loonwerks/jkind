package jkind;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import jkind.analysis.StaticAnalyzer;
import jkind.faultseeder.ComputeFaultSitesVisitor;
import jkind.faultseeder.FaultReplacementLocations;
import jkind.faultseeder.FaultSites;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.builders.ProgramBuilder;
import jkind.lustre.visitors.TypeReconstructor;
import jkind.slicing.DependencyMap;
import jkind.slicing.LustreSlicer;
import jkind.translation.RemoveEnumTypes;
import jkind.translation.Translate;
import jkind.util.Util;

public class JFaultSeeder {

/*
	private static void calcFaultRatios(JFaultSeederSettings settings, FaultSites fs) {
		int totalFaults = fs.totalFaults();
		double doubleTotalFaults = totalFaults;
		double arithPct = fs.arithmeticOp / doubleTotalFaults;
		double relationalPct = fs.relationalOp / doubleTotalFaults;
		double equalityPct = fs.equalityOp / doubleTotalFaults; 
		double booleanPct = fs.booleanOp / doubleTotalFaults;
		double negationPct = fs.negationOp / doubleTotalFaults; 
		double delayPct = fs.delayOp / doubleTotalFaults; 
		double constantPct = fs.constantOp / doubleTotalFaults;
		double nodeCallPct = fs.nodeCallParameter / doubleTotalFaults;
		double variableReplacementPct = fs.variableReplacement / doubleTotalFaults; 	
	}
*/
	
	/*
	 * TODO: MWW - this is currently a hack, but good enough to do what we want for the Jaroslav paper.
	 * It should be extended.
	 */
	private final JFaultSeederSettings settings;
	
	
	public JFaultSeeder(String [] args) {
		settings = JFaultSeederArgumentParser.parse(args);
		String filename = settings.filename;

		if (!filename.toLowerCase().endsWith(".lus")) {
			StdErr.error("input file must have .lus extension");
		}
	}
	
	public void execute() throws Throwable {
		if (settings.directory != null) {
			File theDir = new File(settings.directory);
			if (!theDir.exists()) {
				theDir.mkdir();
			}
		}
		
		Program program = Main.parseLustre(settings.filename);
		StaticAnalyzer.check(program, SolverOption.Z3);

		program = Translate.translate(program);
		program = RemoveEnumTypes.program(program);
		Node main = program.getMainNode();
		DependencyMap dependencyMap = new DependencyMap(main, main.properties, program.functions);
		main = LustreSlicer.slice(main, dependencyMap);

		// Create the new program & get types of elements.
		Program transformed = new ProgramBuilder(program).clearNodes().addNode(main).build();
		TypeReconstructor typeReconstructor = new TypeReconstructor(transformed);
		
		// Find fault sites.
		ComputeFaultSitesVisitor visitor = new ComputeFaultSitesVisitor(typeReconstructor, settings.linearOnly, new FaultReplacementLocations());
		transformed.accept(visitor);
		FaultSites fs = visitor.getFaultSites(); 
		
		// Emit faulty programs.
		String fileRoot = settings.filename.substring(0, settings.filename.length() - 4); 
		FaultReplacementLocations locs = createFaultReplacementLocations(fs);
		createMutantFiles(typeReconstructor, fileRoot, transformed, locs); 
		
		
	}
	
	private int faultInRange(int max) {
		return ThreadLocalRandom.current().nextInt(0, max+1);
	}
	
	private void populateFaults(int faultsToAdd, int max, Set<Integer> elements) {
		if (faultsToAdd > max) {
			throw new IllegalArgumentException("More desired faults than fault locations.");
		}
		while (faultsToAdd > 0) {
			int newFaultLocation = faultInRange(max);
			if (!elements.contains(newFaultLocation)) {
				elements.add(newFaultLocation);
				faultsToAdd--;
			}
		}
	}
	
	private FaultReplacementLocations createFaultReplacementLocations(FaultSites fs) {
		
		int desiredFaults = settings.totalFaults;
		
		int arithmeticFaults = Integer.min(desiredFaults / 4, fs.arithmeticOp);
		int relationalFaults = Integer.min(desiredFaults / 4, fs.relationalOp);
		int booleanFaults = Integer.min(desiredFaults / 4, fs.booleanOp);
		int constantFaults = Integer.min(desiredFaults / 8, fs.constantOp);
		int equalityFaults = Integer.min(desiredFaults / 8, fs.equalityOp);
		
		FaultReplacementLocations locations = new FaultReplacementLocations();
		
		populateFaults(arithmeticFaults, fs.arithmeticOp, locations.arithmeticOp);
		populateFaults(relationalFaults, fs.relationalOp, locations.relationalOp);
		populateFaults(booleanFaults, fs.booleanOp, locations.booleanOp);
		populateFaults(constantFaults, fs.constantOp, locations.constantOp);
		populateFaults(equalityFaults, fs.equalityOp, locations.equalityOp);
		
		System.out.println(locations.toString());
		
		return locations;
	}
	
	private void createMutantFile(TypeReconstructor typeReconstructor,
								  String fileName, 
								  Program program, 
								  FaultReplacementLocations locations) throws IOException {
		
		ComputeFaultSitesVisitor visitor = new ComputeFaultSitesVisitor(typeReconstructor, settings.linearOnly, locations);
		program = visitor.visit(program);
		Util.writeToFile(program.toString(), new File(fileName));
		System.out.println("Wrote " + fileName);
	}
	
	interface ConstructFaultReplacementLocation {
        FaultReplacementLocations operation(int location);   
    }
	
	
	public void emitSingleFaultPrograms(TypeReconstructor typeReconstructor, 
			 							 String fileNameRoot,
			 							 Program program, 
			 							 Set<Integer> locations, 
			 							 ConstructFaultReplacementLocation locLambda) throws IOException {
		int index = 0;
		for (Integer elem: locations) {
			createMutantFile(typeReconstructor, fileNameRoot + index + ".lus", program, locLambda.operation(elem));
			index++; 
		}
	}
	
	/*
	 * 	public int arithmeticOp = 0;
	public int incrementOp = 0;
	public int relationalOp = 0;
	public int equalityOp = 0;
	public int booleanOp = 0;
	public int negationOp = 0; 
	public int delayOp = 0;
	public int constantOp = 0;
	public int nodeCallParameter = 0;
	public int variableReplacement = 0;

	 */	
	public void createMutantFiles(TypeReconstructor typeReconstructor, 
			 						String fileNameRoot,
			 						Program program, 
			 						FaultReplacementLocations locations) throws IOException {
		
		if (settings.faultPerFile) {
			emitSingleFaultPrograms(typeReconstructor, fileNameRoot + ".arithmeticFault", program, 
					locations.arithmeticOp, 
					loc -> { FaultReplacementLocations locs = new FaultReplacementLocations(); locs.arithmeticOp.add(loc); return locs;} );

			emitSingleFaultPrograms(typeReconstructor, fileNameRoot + ".incrementFault", program, 
					locations.incrementOp, 
					loc -> { FaultReplacementLocations locs = new FaultReplacementLocations(); locs.incrementOp.add(loc); return locs;} );

			emitSingleFaultPrograms(typeReconstructor, fileNameRoot + ".relationalFault", program, 
					locations.relationalOp, 
					loc -> { FaultReplacementLocations locs = new FaultReplacementLocations(); locs.relationalOp.add(loc); return locs;} );
			
			emitSingleFaultPrograms(typeReconstructor, fileNameRoot + ".equalityFault", program, 
					locations.equalityOp, 
					loc -> { FaultReplacementLocations locs = new FaultReplacementLocations(); locs.equalityOp.add(loc); return locs;} );
			
			emitSingleFaultPrograms(typeReconstructor, fileNameRoot + ".booleanFault", program, 
					locations.booleanOp, 
					loc -> { FaultReplacementLocations locs = new FaultReplacementLocations(); locs.booleanOp.add(loc); return locs;} );
			
			emitSingleFaultPrograms(typeReconstructor, fileNameRoot + ".negationFault", program, 
					locations.negationOp, 
					loc -> { FaultReplacementLocations locs = new FaultReplacementLocations(); locs.negationOp.add(loc); return locs;} );

			emitSingleFaultPrograms(typeReconstructor, fileNameRoot + ".delayFault", program, 
					locations.delayOp, 
					loc -> { FaultReplacementLocations locs = new FaultReplacementLocations(); locs.delayOp.add(loc); return locs;} );
			
			emitSingleFaultPrograms(typeReconstructor, fileNameRoot + ".constantFault", program, 
					locations.constantOp, 
					loc -> { FaultReplacementLocations locs = new FaultReplacementLocations(); locs.constantOp.add(loc); return locs;} );
			
			emitSingleFaultPrograms(typeReconstructor, fileNameRoot + ".nodeCallParameterFault", program, 
					locations.nodeCallParameter, 
					loc -> { FaultReplacementLocations locs = new FaultReplacementLocations(); locs.nodeCallParameter.add(loc); return locs;} );
			
			emitSingleFaultPrograms(typeReconstructor, fileNameRoot + ".variableReplacementFault", program, 
					locations.variableReplacement, 
					loc -> { FaultReplacementLocations locs = new FaultReplacementLocations(); locs.variableReplacement.add(loc); return locs;} );
		} else {
			createMutantFile(typeReconstructor, fileNameRoot + ".allFaults.lus", program, locations);
		}
	}
	
	public static void main(String[] args) {
		try {
			JFaultSeeder faultSeeder = new JFaultSeeder(args);
			faultSeeder.execute();
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}

	
}
