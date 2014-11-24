package assembler;

import instructions.Instruction;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Assembler {
	public static Instruction parseInstruction(String s) {
		// return new Instruction();
		String[] tokens = s.split("\\s");
		String[] params = new String[tokens.length - 1];
		for (int i = 0; i < tokens.length - 1; i++)
			params[i] = tokens[i + 1];
		String name = tokens[0].substring(0, 1).toUpperCase()
				+ tokens[0].substring(1).toLowerCase();

		Class<?> instructionClass = null;
		Constructor<?> constructor = null;
		Instruction result = null;

		try {
			instructionClass = Class.forName("instructions.isa." + name);
			constructor = instructionClass.getConstructor(String[].class);
			result = (Instruction) constructor.newInstance((Object) params);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			// throw new UnkownInstructionException();
		} finally {
			System.out.println("Assembling: " + s);
		}

		return result;
	}

	public static Instruction[] assembleProgram(String[] program) {
		ArrayList<Instruction> result = new ArrayList<Instruction>();
		for (int i = 0; i < program.length; i++) {
			if (program[i].length() == 0)
				continue;
			result.add(parseInstruction(program[i]));
		}
		return result.toArray(new Instruction[1]);
	}
}
