import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GestorClientes {
    private List<Cliente> listaClientes;

    public GestorClientes() {
        listaClientes = new ArrayList<>();
    }

    public void agregarCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    public Cliente buscarClientePorID(short id) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    public List<Cliente> buscarClientePorNombre(String nombre) {
        List<Cliente> clientesEncontrados = new ArrayList<>();
        for (Cliente cliente : listaClientes) {
            if (cliente.getNombre().toString().toLowerCase().contains(nombre.toLowerCase())) {
                clientesEncontrados.add(cliente);
            }
        }
        return clientesEncontrados;
    }

    public static void main(String[] args) {
        GestorClientes gestor = new GestorClientes();

        gestor.agregarCliente(new Cliente((short) 1, "Juan Pérez", "López", 1000.0f));
        gestor.agregarCliente(new Cliente((short) 2, "María López", "González", 1500.0f));
        gestor.agregarCliente(new Cliente((short) 3, "Carlos González", "Pérez", 2000.0f));

        Cliente clientePorID = gestor.buscarClientePorID((short) 2);
        System.out.println("Cliente por ID: " + (clientePorID != null ? clientePorID.toString() : "No encontrado"));

        List<Cliente> clientesPorNombre = gestor.buscarClientePorNombre("carlos");
        System.out.println("Clientes por nombre: " + clientesPorNombre);
    }
}
    public int compactarFichero() {
        List<Cliente> clientesCompactados = new ArrayList<>();
        int registrosCompactados = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Cliente cliente = leerClienteDesdeTexto(linea);
                if (!cliente.estaBorrado()) {
                    clientesCompactados.add(cliente);
                } else {
                    registrosCompactados++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ahora escribimos la lista compactada en un fichero auxiliar
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("clientes_temp.txt"))) {
            for (Cliente cliente : clientesCompactados) {
                bw.write(cliente.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Renombrar el fichero auxiliar al original
        File ficheroAuxiliar = new File("clientes_temp.txt");
        File ficheroOriginal = new File(nombreFichero);
        ficheroOriginal.delete();
        ficheroAuxiliar.renameTo(ficheroOriginal);

        return registrosCompactados;
    }

    private Cliente leerClienteDesdeTexto(String linea) {
        // Implementa la lógica para convertir una línea de texto en un objeto Cliente
        // Aquí asumo que la línea sigue el formato toString() de la clase Cliente
        String[] partes = linea.split(", ");
        short id = Short.parseShort(partes[0].substring(partes[0].indexOf('=') + 1));
        String nombre = partes[1].substring(partes[1].indexOf('=') + 1);
        String apellidos = partes[2].substring(partes[2].indexOf('=') + 1);
        float saldo = Float.parseFloat(partes[3].substring(partes[3].indexOf('=') + 1, partes[3].length() - 1));
        return new Cliente(id, nombre, apellidos, saldo);
    }

    public static void main(String[] args) {
        GestorClientes gestor = new GestorClientes();

        // Agregar algunos clientes para probar
        gestor.agregarCliente(new Cliente((short) 1, "Juan Pérez", "López", 1000.0f));
        gestor.agregarCliente(new Cliente((short) 2, "María López", "González", 1500.0f));
        gestor.agregarCliente(new Cliente((short) 3, "Carlos González", "Pérez", 2000.0f));

        // Compactar el fichero y obtener la cantidad de registros compactados
        int registrosCompactados = gestor.compactarFichero();
        System.out.println("Registros compactados: " + registrosCompactados);
    }
}
