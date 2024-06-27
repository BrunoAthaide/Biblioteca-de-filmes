
package br.com.unipar.trabalho_sub.views;

import br.com.unipar.trabalho_sub.model.Filme;
import br.com.unipar.trabalho_sub.controller.FilmeController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import mmcorej.org.json.JSONException;

public class FilmeApp {
    private JFrame frame;
    private JTextField campoTextoFilme;
    private JTextArea areaDetalhesFilme;
    private JCheckBox caixaAssistido;
    private FilmeController filmeController;

    public FilmeApp() {
        filmeController = new FilmeController();

        frame = new JFrame("Aplicativo de Busca de Filmes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel painelBusca = new JPanel();
        campoTextoFilme = new JTextField(20);
        JButton botaoBuscar = new JButton("Buscar");

        botaoBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeFilme = campoTextoFilme.getText();
                try {
                    buscarFilme(nomeFilme);
                } catch (JSONException ex) {
                    Logger.getLogger(FilmeApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        painelBusca.add(new JLabel("Nome do Filme:"));
        painelBusca.add(campoTextoFilme);
        painelBusca.add(botaoBuscar);

        areaDetalhesFilme = new JTextArea();
        areaDetalhesFilme.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaDetalhesFilme);

        caixaAssistido = new JCheckBox("Assistido");

        JButton botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    salvarFilme();
                } catch (JSONException ex) {
                    Logger.getLogger(FilmeApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        JButton botaoListar = new JButton("Listar Filmes Assistidos");
        botaoListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarFilmesAssistidos();
            }
        });

        JPanel painelInferior = new JPanel();
        painelInferior.add(caixaAssistido);
        painelInferior.add(botaoSalvar);
        painelInferior.add(botaoListar);

        frame.add(painelBusca, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(painelInferior, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void buscarFilme(String nomeFilme) throws JSONException {
        Filme filme = filmeController.buscarFilme(nomeFilme);
        if (filme != null) {
            areaDetalhesFilme.setText(filme.toString());
        } else {
            areaDetalhesFilme.setText("Filme não encontrado.");
        }
    }

    private void salvarFilme() throws JSONException {
        String detalhesFilme = areaDetalhesFilme.getText();
        if (!detalhesFilme.isEmpty()) {
            Filme filme = filmeController.buscarFilme(campoTextoFilme.getText());

            if (caixaAssistido.isSelected()) {
                if (!filme.isAssistido()) {
                    filme.setAssistido(true);
                    filmeController.salvarFilmeAssistido(filme);
                    JOptionPane.showMessageDialog(frame, "Filme marcado como assistido.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Este filme já está marcado como assistido.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Marque a opção 'Assistido' para salvar o filme.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Nenhum filme encontrado para salvar.");
        }
    }

    private void listarFilmesAssistidos() {
        new FilmesAssistidosView(filmeController.listarFilmesAssistidos());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FilmeApp::new);
    }
}
