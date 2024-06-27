
package br.com.unipar.trabalho_sub.model;

public class Filme {
    private String titulo;
    private String ano;
    private String tipo;
    private String sinopse;
    private boolean assistido; 

    public Filme(String titulo, String ano, String tipo, String sinopse) {
        this.titulo = titulo;
        this.ano = ano;
        this.tipo = tipo;
        this.sinopse = sinopse;
        this.assistido = false; 
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAno() {
        return ano;
    }

    public String getTipo() {
        return tipo;
    }

    public String getSinopse() {
        return sinopse;
    }

    public boolean isAssistido() {
        return assistido;
    }

    public void setAssistido(boolean assistido) {
        this.assistido = assistido;
    }

    @Override
    public String toString() {
        return "Título: " + titulo + "\n" +
                "Ano: " + ano + "\n" +
                "Tipo: " + tipo + "\n" +
                "Sinopse: " + sinopse;
    }
}
