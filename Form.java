package com.faculdade;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Form extends Frame {
    /* pesquisa start */
    private TextField txtNomePesquisa = new TextField();
    private Button btnPesquisar = new Button("Pesquisar");
    /* pesquisa end */

    /* insert start */
    private TextField txtNome = new TextField();
    private TextField txtSalario = new TextField();
    private TextField txtCargo = new TextField();

    private Button btnAnterior = new Button("Anterior");
    private Button btnProximo = new Button("Proximo");
    /* insert end */

    // database vars
    Connection con;
    Statement stmt;
    ResultSet rs;

    public Form(String title, int width, int height) {
        super(title);
        try {
            // criando conexão
            Class.forName("com.mysql.cj.jdbc.Driver"); /* registrando driver sql */

            con = DriverManager.getConnection("jdbc:mysql://localhost/aulajava", "root", "root");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        // criando Form
        setSize(width, height);

        addWindowListener(new FechaJanela());

        FlowLayout layoutSuperior = new FlowLayout();
        Panel painelSuperior = new Panel(layoutSuperior);

        GridLayout layoutInferior = new GridLayout(4, 2);
        Panel painelInferior = new Panel(layoutInferior);

        /* start painel superior */
        // name input
        txtNomePesquisa.setColumns(15);
        painelSuperior.add(new Label("Nome:"));
        painelSuperior.add(txtNomePesquisa);

        btnPesquisar.addActionListener(e -> pesquisar());
        painelSuperior.add(btnPesquisar);
        /* end painel superior */

        /* start painel inferior */
        painelInferior.add(new Label("Nome:"));
        painelInferior.add(txtNome);

        painelInferior.add(new Label("Salário:"));
        painelInferior.add(txtSalario);

        painelInferior.add(new Label("Cargo"));
        painelInferior.add(txtCargo);

        btnAnterior.addActionListener(e -> anterior());
        painelInferior.add(btnAnterior);

        btnProximo.addActionListener(e -> proximo());
        painelInferior.add(btnProximo);
        /* end painel inferior */

        add(painelSuperior, BorderLayout.PAGE_START);
        add(painelInferior, BorderLayout.PAGE_END);

    }

    public void pesquisar() {
        try {
            String likeName = txtNomePesquisa.getText();

            rs = stmt.executeQuery(
                    "SELECT tbfuncs.name_func as nome, tbfuncs.sal_func as salario, tbcargos.ds_cargo as cargo FROM tbfuncs, tbcargos WHERE tbfuncs.cd_cargo = tbcargos.cd_cargo AND tbfuncs.name_func LIKE '%" + likeName + "%'");


            if (rs.next()) {
                String nome = rs.getString("nome");
                double salario = rs.getDouble("salario");
                String cargo = rs.getString("cargo");
                System.out.println(nome + " - " + cargo + " - " + salario);

                txtNome.setText(nome);
                txtSalario.setText(Double.toString(salario));
                txtCargo.setText(cargo);
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum funcionário encontrado", "Erro!",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception exception) {
            System.out.println(exception.toString());

            JOptionPane.showMessageDialog(this, "Um erro ocorreu durante a pesquisa", "Erro!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void proximo() {
        try {
            if (rs.next()) {
                String nome = rs.getString("nome");
                double salario = rs.getDouble("salario");
                String cargo = rs.getString("cargo");
                System.out.println(nome + " - " + cargo + " - " + salario);

                txtNome.setText(nome);
                txtSalario.setText(Double.toString(salario));
                txtCargo.setText(cargo);
            } else {
                rs.previous();
                JOptionPane.showMessageDialog(this, "Último funcionário sendo mostrado", "Erro!",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Último funcionário já selecionado", "Erro!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void anterior() {
        try {
            if (rs.previous()) {
                String nome = rs.getString("nome");
                double salario = rs.getDouble("salario");
                String cargo = rs.getString("cargo");
                System.out.println(nome + " - " + cargo + " - " + salario);

                txtNome.setText(nome);
                txtSalario.setText(Double.toString(salario));
                txtCargo.setText(cargo);
            } else {
                rs.next();
                JOptionPane.showMessageDialog(this, "Primeiro funcionário sendo mostrado", "Erro!",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Erro!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
