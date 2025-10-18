package Ejemplo;
import java.io.*;
import java.util.*;

public class ListaInvertida {
    // DICCIONARIO DE TÉRMINOS: Término -> Lista de Documentos
    private Map<String, Set<Integer>> diccionarioTerminos;
    private Map<Integer, Documento> documentos;
    private int siguienteId;
    private final String ARCHIVO_INDICE = "lista_invertida.txt";

    public ListaInvertida() {
        this.diccionarioTerminos = new HashMap<>();
        this.documentos = new HashMap<>();
        this.siguienteId = 1;
        cargarIndice();
    }

    // --- INDEXACIÓN ---
    public void agregarDocumento(String contenido) {
        Documento documento = new Documento(siguienteId, contenido);
        documentos.put(siguienteId, documento);

        String[] terminos = contenido.toLowerCase().split("\\W+");

        for (String termino : terminos) {
            termino = termino.trim();
            if (!termino.isEmpty() && termino.length() > 1) {
                diccionarioTerminos.computeIfAbsent(termino, k -> new HashSet<>()).add(siguienteId);
            }
        }
        siguienteId++;
    }

    // --- BÚSQUEDA ---
    public Set<Integer> buscarTermino(String termino) {
        return diccionarioTerminos.getOrDefault(termino.toLowerCase(), new HashSet<>());
    }

    public List<Documento> buscarDocumentos(String termino) {
        Set<Integer> idsDocumentos = buscarTermino(termino);
        List<Documento> resultados = new ArrayList<>();
        for (Integer id : idsDocumentos) {
            resultados.add(documentos.get(id));
        }
        return resultados;
    }

    // --- ESTADÍSTICAS ---
    public int getTotalTerminos() {
        return diccionarioTerminos.size();
    }

    public int getTotalDocumentos() {
        return documentos.size();
    }

    public String obtenerResumenIndice() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== LISTA INVERTIDA ===\n");
        sb.append("Términos: ").append(getTotalTerminos()).append(" | Documentos: ").append(getTotalDocumentos()).append("\n\n");

        List<String> terminos = new ArrayList<>(diccionarioTerminos.keySet());
        Collections.sort(terminos);

        for (String termino : terminos) {
            Set<Integer> docs = diccionarioTerminos.get(termino);
            sb.append("'").append(termino).append("' -> ").append(docs).append("\n");
        }
        return sb.toString();
    }

    // --- PERSISTENCIA ---
    private void cargarIndice() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_INDICE))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(":", 2);
                if (partes.length == 2) {
                    String termino = partes[0];
                    String[] ids = partes[1].split(",");
                    Set<Integer> documentosIds = new HashSet<>();
                    for (String id : ids) {
                        if (!id.isEmpty()) {
                            documentosIds.add(Integer.parseInt(id));
                        }
                    }
                    diccionarioTerminos.put(termino, documentosIds);
                }
            }
        } catch (IOException e) {
            // Archivo no existe, empezar vacío
        }
    }

    public void guardarIndice() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_INDICE))) {
            for (Map.Entry<String, Set<Integer>> entry : diccionarioTerminos.entrySet()) {
                StringBuilder linea = new StringBuilder();
                linea.append(entry.getKey()).append(":");
                for (Integer docId : entry.getValue()) {
                    linea.append(docId).append(",");
                }
                if (linea.charAt(linea.length()-1) == ',') {
                    linea.deleteCharAt(linea.length()-1);
                }
                bw.write(linea.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error guardando índice: " + e.getMessage());
        }
    }
}