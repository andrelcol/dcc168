package jogodavida;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class JogoDaVidaApp extends Application {
    private Tabuleiro tabuleiro;
    private Button[][] celulas;
    private Timeline timeline;
    private Button btnIniciar;
    private Button btnParar;
    private Label lblStatus;
    private Label lblGeracao;
    private Slider sliderVelocidade;
    private int geracao;
    private static final int TAMANHO_CELULA = 30;
    private static final int LINHAS = 20;
    private static final int COLUNAS = 20;

    @Override
    public void start(Stage primaryStage) {
        tabuleiro = new Tabuleiro(LINHAS, COLUNAS);
        celulas = new Button[LINHAS][COLUNAS];
        geracao = 0;

        // Layout principal
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // Grid do jogo
        GridPane grid = new GridPane();
        grid.setHgap(1);
        grid.setVgap(1);
        grid.setStyle("-fx-background-color: lightgray;");

        // Criação dos botões/células
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                Button celula = new Button();
                celula.setPrefSize(TAMANHO_CELULA, TAMANHO_CELULA);
                celula.setStyle("-fx-background-color: white; -fx-border-color: gray;");
                
                final int linha = i;
                final int coluna = j;
                celula.setOnAction(e -> toggleCelula(linha, coluna));
                
                // Adiciona tooltip
                celula.setTooltip(new Tooltip("Clique para alternar estado da célula"));
                
                celulas[i][j] = celula;
                grid.add(celula, j, i);
            }
        }

        // Controle de velocidade
        sliderVelocidade = new Slider(100, 1000, 500);
        sliderVelocidade.setShowTickLabels(true);
        sliderVelocidade.setShowTickMarks(true);
        sliderVelocidade.setMajorTickUnit(200);
        sliderVelocidade.setBlockIncrement(100);
        
        Label lblVelocidade = new Label("Velocidade:");
        HBox velocidadeBox = new HBox(10);
        velocidadeBox.getChildren().addAll(lblVelocidade, sliderVelocidade);

        // Botões de controle
        btnIniciar = new Button("Iniciar");
        btnParar = new Button("Parar");
        Button btnLimpar = new Button("Limpar");
        
        // Labels de status
        lblStatus = new Label("Células vivas: 0");
        lblGeracao = new Label("Geração: 0");
        
        // Configuração da animação
        timeline = new Timeline(new KeyFrame(Duration.millis(500), e -> proximaGeracao()));
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Atualiza a velocidade quando o slider muda
        sliderVelocidade.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (timeline != null) {
                timeline.stop();
                timeline = new Timeline(new KeyFrame(Duration.millis(newVal.doubleValue()), e -> proximaGeracao()));
                timeline.setCycleCount(Timeline.INDEFINITE);
                if (btnParar.isDisabled()) {
                    timeline.play();
                }
            }
        });

        // Ações dos botões
        btnIniciar.setOnAction(e -> iniciarSimulacao());
        btnParar.setOnAction(e -> pararSimulacao());
        btnLimpar.setOnAction(e -> limparTabuleiro());

        // Adiciona tooltips aos botões
        btnIniciar.setTooltip(new Tooltip("Inicia a simulação"));
        btnParar.setTooltip(new Tooltip("Pausa a simulação"));
        btnLimpar.setTooltip(new Tooltip("Limpa o tabuleiro"));

        // Desabilita o botão Parar inicialmente
        btnParar.setDisable(true);

        // Barra de controles
        HBox controles = new HBox(10);
        controles.getChildren().addAll(btnIniciar, btnParar, btnLimpar, lblStatus, lblGeracao);

        // Montagem do layout
        root.getChildren().addAll(grid, velocidadeBox, controles);

        // Configuração da janela
        Scene scene = new Scene(root);
        primaryStage.setTitle("Jogo da Vida");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Atualiza o status inicial
        atualizarStatus();
    }

    private void iniciarSimulacao() {
        timeline.play();
        btnIniciar.setDisable(true);
        btnParar.setDisable(false);
    }

    private void pararSimulacao() {
        timeline.pause();
        btnIniciar.setDisable(false);
        btnParar.setDisable(true);
    }

    private void toggleCelula(int linha, int coluna) {
        boolean novoEstado = !tabuleiro.getCelula(linha, coluna);
        tabuleiro.setCelula(linha, coluna, novoEstado);
        atualizarCelula(linha, coluna);
        atualizarStatus();
    }

    private void atualizarCelula(int linha, int coluna) {
        boolean viva = tabuleiro.getCelula(linha, coluna);
        celulas[linha][coluna].setStyle(viva ? 
            "-fx-background-color: black; -fx-border-color: gray;" :
            "-fx-background-color: white; -fx-border-color: gray;");
    }

    private void proximaGeracao() {
        tabuleiro.proximaGeracao();
        geracao++;
        
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                atualizarCelula(i, j);
            }
        }
        atualizarStatus();
    }

    private int contarCelulasVivas() {
        int count = 0;
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                if (tabuleiro.getCelula(i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    private void atualizarStatus() {
        int vivas = contarCelulasVivas();
        lblStatus.setText("Células vivas: " + vivas);
        lblGeracao.setText("Geração: " + geracao);
    }

    private void limparTabuleiro() {
        pararSimulacao();
        geracao = 0;
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                tabuleiro.setCelula(i, j, false);
                atualizarCelula(i, j);
            }
        }
        atualizarStatus();
    }

    public static void main(String[] args) {
        launch(args);
    }
} 