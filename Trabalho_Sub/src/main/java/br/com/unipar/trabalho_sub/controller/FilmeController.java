
package br.com.unipar.trabalho_sub.controller;


import br.com.unipar.trabalho_sub.model.Filme;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import mmcorej.org.json.JSONException;
import mmcorej.org.json.JSONObject;



public class FilmeController {
    private static final String API_KEY = "7f85bc40"; // Coloque sua chave da OMDb API aqui
    private static final String API_URL = "http://www.omdbapi.com/";

    private List<Filme> filmesAssistidos;

    public FilmeController() {
        filmesAssistidos = new ArrayList<>();
        carregarFilmesAssistidos(); // Carrega os filmes assistidos ao iniciar o controller
    }

    public Filme buscarFilme(String nomeFilme) throws JSONException {
        Filme filme = null;
        String urlBusca = API_URL + "?apikey=" + API_KEY + "&t=" + nomeFilme;

        try {
            URL url = new URL(urlBusca);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String linha;

            while ((linha = reader.readLine()) != null) {
                response.append(linha);
            }

            reader.close();

            JSONObject json = new JSONObject(response.toString());
            String titulo = json.getString("Title");
            String ano = json.getString("Year");
            String tipo = json.getString("Type");
            String sinopse = json.getString("Plot");

            filme = new Filme(titulo, ano, tipo, sinopse);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return filme;
    }

    public void salvarFilmeAssistido(Filme filme) {
        filme.setAssistido(true);
        filmesAssistidos.add(filme);
        salvarFilmesAssistidos();
    }

    public List<Filme> listarFilmesAssistidos() {
        return filmesAssistidos;
    }

    private void salvarFilmesAssistidos() {
        String caminhoArquivo = "C:\\Faculdade\\Trabalho_Sub\\Trabalho_Sub\\trabalho.csv"; // Caminho completo onde será salvo o arquivo

        try (PrintWriter writer = new PrintWriter(new FileWriter(caminhoArquivo))) {
            for (Filme filme : filmesAssistidos) {
                writer.println(String.format("%s;%s;%s;%s;%s",
                        filme.getTitulo(), filme.getAno(), filme.getTipo(), filme.getSinopse(), filme.isAssistido()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Arquivo salvo em: " + new File(caminhoArquivo).getAbsolutePath());
    }

    private void carregarFilmesAssistidos() {
        String caminhoArquivo = "C:\\Faculdade\\Trabalho_Sub\\Trabalho_Sub\\trabalho.csv"; // Caminho completo onde está o arquivo

        filmesAssistidos.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                String titulo = dados[0];
                String ano = dados[1];
                String tipo = dados[2];
                String sinopse = dados[3];
                boolean assistido = Boolean.parseBoolean(dados[4]);

                Filme filme = new Filme(titulo, ano, tipo, sinopse);
                filme.setAssistido(assistido);
                filmesAssistidos.add(filme);
            }
        } catch (IOException e) {
            // Arquivo ainda não existe ou ocorreu um erro na leitura, trata de acordo com sua aplicação
            e.printStackTrace();
        }
    }

    public List<Filme> buscarFilmes(String nomeFilme) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

  }
    



