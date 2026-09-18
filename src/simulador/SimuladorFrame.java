package simulador;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Tela do simulador de financiamento.
 *
 * LayoutManagers utilizados:
 * - BorderLayout: janela (título no topo, formulário no centro)
 * - BoxLayout: empilha os painéis verticalmente
 * - GridBagLayout: painéis de formulário (rótulo + campo)
 * - FlowLayout: opções de tipo (Novo/Usado)
 * - GridLayout: botões e painel de resultado
 */
public class SimuladorFrame extends JFrame {

    private static final String[] MARCAS = {
            "Chevrolet", "Fiat", "Ford", "Honda", "Hyundai",
            "Jeep", "Nissan", "Renault", "Toyota", "Volkswagen"
    };
    private static final Integer[] PARCELAS = {12, 24, 36, 48, 60};
    private static final int ANO_INICIAL = 2000;
    private static final int ANO_FINAL = 2026;

    private static final NumberFormat MOEDA = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));

    // Dados do veículo
    private final JComboBox<String> cbMarca = new JComboBox<>(MARCAS);
    private final JTextField txtModelo = new JTextField(20);
    private final JComboBox<Integer> cbAno = new JComboBox<>(anosDecrescentes());
    private final JTextField txtValor = new JTextField(20);

    // Tipo
    private final JRadioButton rbNovo = new JRadioButton("Novo");
    private final JRadioButton rbUsado = new JRadioButton("Usado");

    // Veículo usado
    private final JTextField txtQuilometragem = new JTextField(20);
    private final JTextField txtProprietarios = new JTextField(20);
    private JPanel painelUsado;

    // Financiamento
    private final JCheckBox chkPossuiEntrada = new JCheckBox("Possui entrada?");
    private final JLabel lblEntrada = new JLabel("Entrada:");
    private final JTextField txtEntrada = new JTextField(20);
    private final JComboBox<Integer> cbParcelas = new JComboBox<>(PARCELAS);

    // Resultado
    private final JLabel lblValorFinanciado = new JLabel();
    private final JLabel lblValorParcela = new JLabel();
    private final JLabel lblTotalPagar = new JLabel();
    private JPanel painelResultado;

    public SimuladorFrame() {
        super("Financiamento de Carros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JLabel titulo = new JLabel("Financiamento de Carros", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));

        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        painelUsado = criarPainelUsado();
        painelResultado = criarPainelResultado();

        adicionar(conteudo, criarPainelVeiculo());
        adicionar(conteudo, criarPainelTipo());
        adicionar(conteudo, painelUsado);
        adicionar(conteudo, criarPainelFinanciamento());
        adicionar(conteudo, criarPainelBotoes());
        adicionar(conteudo, painelResultado);

        setLayout(new BorderLayout());
        add(titulo, BorderLayout.NORTH);
        add(conteudo, BorderLayout.CENTER);

        limpar();
        setLocationRelativeTo(null);
    }

    private JPanel criarPainelVeiculo() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(tituloBorda("Dados do Veículo", false));
        adicionarLinha(painel, 0, new JLabel("Marca:"), cbMarca);
        adicionarLinha(painel, 1, new JLabel("Modelo:"), txtModelo);
        adicionarLinha(painel, 2, new JLabel("Ano:"), cbAno);
        adicionarLinha(painel, 3, new JLabel("Valor (R$):"), txtValor);
        return painel;
    }

    private JPanel criarPainelTipo() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbNovo);
        grupo.add(rbUsado);

        rbNovo.addActionListener(e -> atualizarVisibilidade());
        rbUsado.addActionListener(e -> atualizarVisibilidade());

        painel.add(new JLabel("Tipo:"));
        painel.add(rbNovo);
        painel.add(rbUsado);
        return painel;
    }

    private JPanel criarPainelUsado() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(tituloBorda("Dados do Veículo Usado", true));
        adicionarLinha(painel, 0, new JLabel("Quilometragem:"), txtQuilometragem);
        adicionarLinha(painel, 1, new JLabel("Proprietários:"), txtProprietarios);
        return painel;
    }

    private JPanel criarPainelFinanciamento() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(tituloBorda("Financiamento", false));

        chkPossuiEntrada.addActionListener(e -> atualizarVisibilidade());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(4, 5, 4, 5);
        painel.add(chkPossuiEntrada, gbc);

        adicionarLinha(painel, 1, lblEntrada, txtEntrada);
        adicionarLinha(painel, 2, new JLabel("Parcelas:"), cbParcelas);
        return painel;
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new GridLayout(1, 2, 15, 0));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        JButton btnCalcular = new JButton("Calcular");
        JButton btnLimpar = new JButton("Limpar");
        btnCalcular.addActionListener(e -> calcular());
        btnLimpar.addActionListener(e -> limpar());

        painel.add(btnCalcular);
        painel.add(btnLimpar);
        getRootPane().setDefaultButton(btnCalcular);
        return painel;
    }

    private JPanel criarPainelResultado() {
        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 6));
        painel.setBorder(tituloBorda("Resultado", false));

        painel.add(new JLabel("Valor financiado:"));
        painel.add(lblValorFinanciado);
        painel.add(new JLabel("Valor da parcela:"));
        painel.add(lblValorParcela);
        painel.add(new JLabel("Total a pagar:"));
        painel.add(lblTotalPagar);

        Font negrito = lblValorFinanciado.getFont().deriveFont(Font.BOLD);
        lblValorFinanciado.setFont(negrito);
        lblValorParcela.setFont(negrito);
        lblTotalPagar.setFont(negrito);
        return painel;
    }

    private void atualizarVisibilidade() {
        painelUsado.setVisible(rbUsado.isSelected());
        lblEntrada.setVisible(chkPossuiEntrada.isSelected());
        txtEntrada.setVisible(chkPossuiEntrada.isSelected());
        painelResultado.setVisible(false);
        pack();
    }

    private void limpar() {
        cbMarca.setSelectedIndex(0);
        txtModelo.setText("");
        cbAno.setSelectedIndex(0);
        txtValor.setText("");
        rbNovo.setSelected(true);
        txtQuilometragem.setText("");
        txtProprietarios.setText("");
        chkPossuiEntrada.setSelected(false);
        txtEntrada.setText("");
        cbParcelas.setSelectedIndex(0);
        atualizarVisibilidade();
        txtModelo.requestFocusInWindow();
    }

    private void calcular() {
        List<String> erros = new ArrayList<>();
        JComponent primeiroInvalido = null;

        if (txtModelo.getText().isBlank()) {
            erros.add("Informe o modelo do veículo.");
            primeiroInvalido = txtModelo;
        }

        Double valor = lerValor(txtValor.getText());
        if (valor == null || valor <= 0) {
            erros.add("Informe um valor do veículo válido e maior que zero.");
            if (primeiroInvalido == null) primeiroInvalido = txtValor;
        }

        if (rbUsado.isSelected()) {
            Integer km = lerInteiro(txtQuilometragem.getText());
            if (km == null || km < 0) {
                erros.add("Informe uma quilometragem válida (número inteiro, zero ou maior).");
                if (primeiroInvalido == null) primeiroInvalido = txtQuilometragem;
            }
            Integer proprietarios = lerInteiro(txtProprietarios.getText());
            if (proprietarios == null || proprietarios < 1) {
                erros.add("Informe a quantidade de proprietários (número inteiro, 1 ou maior).");
                if (primeiroInvalido == null) primeiroInvalido = txtProprietarios;
            }
        }

        double entrada = 0;
        if (chkPossuiEntrada.isSelected()) {
            Double valorEntrada = lerValor(txtEntrada.getText());
            if (valorEntrada == null || valorEntrada <= 0) {
                erros.add("Informe um valor de entrada válido e maior que zero.");
                if (primeiroInvalido == null) primeiroInvalido = txtEntrada;
            } else if (valor != null && valorEntrada >= valor) {
                erros.add("A entrada deve ser menor que o valor do veículo.");
                if (primeiroInvalido == null) primeiroInvalido = txtEntrada;
            } else {
                entrada = valorEntrada;
            }
        }

        if (!erros.isEmpty()) {
            painelResultado.setVisible(false);
            pack();
            JOptionPane.showMessageDialog(this, String.join("\n", erros),
                    "Dados inválidos", JOptionPane.WARNING_MESSAGE);
            primeiroInvalido.requestFocusInWindow();
            return;
        }

        int parcelas = (Integer) cbParcelas.getSelectedItem();
        CalculadoraFinanciamento.Resultado resultado =
                CalculadoraFinanciamento.calcular(valor, entrada, parcelas);

        lblValorFinanciado.setText(MOEDA.format(resultado.valorFinanciado()));
        lblValorParcela.setText(parcelas + "x de " + MOEDA.format(resultado.valorParcela()));
        lblTotalPagar.setText(MOEDA.format(resultado.totalPagar()));
        painelResultado.setVisible(true);
        pack();
    }


    /**
     * Converte texto em número aceitando os formatos "45000", "45000.50",
     * "45.000" e "45.000,50". Retorna null se o texto for inválido.
     */
    private static Double lerValor(String texto) {
        String t = texto.replace("R$", "").trim();
        if (t.isEmpty()) return null;
        if (t.contains(",")) {
            t = t.replace(".", "").replace(",", ".");
        } else if (t.matches("\\d{1,3}(\\.\\d{3})+")) {
            t = t.replace(".", "");
        }
        try {
            return Double.parseDouble(t);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Integer lerInteiro(String texto) {
        String t = texto.trim().replace(".", "");
        try {
            return Integer.parseInt(t);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Integer[] anosDecrescentes() {
        Integer[] anos = new Integer[ANO_FINAL - ANO_INICIAL + 1];
        for (int i = 0; i < anos.length; i++) {
            anos[i] = ANO_FINAL - i;
        }
        return anos;
    }

    private static void adicionarLinha(JPanel painel, int linha, JLabel rotulo, JComponent campo) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = linha;
        gbc.insets = new Insets(4, 5, 4, 5);

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(rotulo, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painel.add(campo, gbc);
    }

    private static Border tituloBorda(String titulo, boolean tracejada) {
        Border linha = tracejada
                ? BorderFactory.createDashedBorder(Color.GRAY, 4, 3)
                : BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        return BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(linha, titulo),
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private static void adicionar(JPanel conteudo, JComponent componente) {
        componente.setAlignmentX(Component.LEFT_ALIGNMENT);
        conteudo.add(componente);
        conteudo.add(Box.createVerticalStrut(8));
    }
}
