package raf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {
	RandomAccessFile raf;
	String nomArchivo;
	File f;

	public Persistencia(String nomArchivo) throws FileNotFoundException {
		this.nomArchivo = nomArchivo;
		this.f = new File(nomArchivo);
		raf = new RandomAccessFile(f, "rw");
	}
	
	public Persistencia(File archivo) throws FileNotFoundException {
		this.nomArchivo = archivo.getName();
		this.f = archivo;
		raf = new RandomAccessFile(f, "rw");
	}

	public void cerrar() throws IOException {
		raf.close();
	}

	public void guardar(Cliente cli) throws IOException {
		// completar. Se debe guardar en el orden correspondiente.
		raf.writeShort(cli.getId());
		raf.writeChars(cli.getNombre().toString());
		raf.writeChars(cli.getApellidos().toString());
		raf.writeFloat(cli.getSaldo());
	}

	public Cliente leer() throws IOException {
		return leerRegistro();
	}

	private Cliente leerRegistro() throws IOException {
		short id = raf.readShort();
		String nombre = "";
		for (int i = 0; i < Cliente.TAM_NOMBRE; i++) {
			nombre = nombre + raf.readChar();
		}
		String apellidos = "";
		for (int i = 0; i < Cliente.TAM_APELLIDOS; i++) {
			apellidos = apellidos + raf.readChar();
		}
		float saldo = raf.readFloat();
		Cliente cli = new Cliente(id, nombre, apellidos, saldo);
		return cli;
	}

	public void irInicio() throws IOException {
		raf.seek(0);
	}

	public long totalRegistros() throws IOException {
		return raf.length() / Cliente.TAM_REGISTRO;
	}

	public void irFinal() throws IOException {
		raf.seek(raf.length());
	}

	public void irRegistro(int reg) throws IOException {
		raf.seek(reg * Cliente.TAM_REGISTRO - Cliente.TAM_REGISTRO);
	}

	public void borrarRegistro() throws IOException {
		Cliente cli = leerRegistro();
		if (cli.getId() > 0)
			cli.setId((short) (cli.getId() * (-1)));
		raf.seek(raf.getFilePointer() - Cliente.TAM_REGISTRO);
		guardar(cli);
	}

	// Ejercicio1: buscará por id de cliente
	public Cliente buscarPorID(short id) throws IOException {
		return null;
	}

	// Ejercicio2: buscará por aproximación en nombre y apellidos
	public List<Cliente> buscarPorNombre(String nombre) throws IOException {
		return new ArrayList<Cliente>();
	}

	// Ejercicio3
	public long compactar1() throws IOException {
		return 0;
	}

	// Ejercicio4 (opcional)
	public long compactar2() throws IOException {
		return 0;
	}

}