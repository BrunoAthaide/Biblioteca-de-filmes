
package br.com.unipar.trabalho_sub.views;
import br.com.unipar.trabalho_sub.model.Filme;
import java.awt.BorderLayout;
import javax.swing.*;
import java.util.List;

public class FilmesAssistidosView extends JFrame {
    private JPanel painelLista;

    public FilmesAssistidosView(List<Filme> filmesAssistidos) {
        setTitle("Filmes Assistidos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        painelLista = new JPanel();
        painelLista.setLayout(new BoxLayout(painelLista, BoxLayout.Y_AXIS));

        for (Filme filme : filmesAssistidos) {
            JLabel labelFilme = new JLabel(filme.getTitulo());
            painelLista.add(labelFilme);
        }

        JScrollPane scrollPane = new JScrollPane(painelLista);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
    
}


    

