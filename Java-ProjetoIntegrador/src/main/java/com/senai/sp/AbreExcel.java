package com.senai.sp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.*;

public class AbreExcel {
    private static final String fileName = "C:\\Users\\50714069850\\Desktop\\integrador\\web-scrapping-hs\\HQs.xlsx";

    static List<Produto> listaProdutos = new ArrayList<>();
    public static void lerExcel(){
        try {
            FileInputStream arquivo = new FileInputStream(new File(AbreExcel.fileName));

            XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
            Sheet sheetProdutos = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheetProdutos.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0){
                    continue;
                } else {
                    Iterator<Cell> cellIterator = row.cellIterator();

                    Produto produto = new Produto();
                    listaProdutos.add(produto);
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (cell.getColumnIndex()) {
                            case 0 -> produto.setNome(cell.getStringCellValue());
                            case 1 -> {
                                String precoStr = cell.getStringCellValue();
                                String precoStr2 = precoStr.replace("R$", "").replace(",", ".");
                                double preco = Double.parseDouble(precoStr2);
                                produto.setPreco(preco);
                            }
                        }
                    }
                }
            arquivo.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Arquivo Excel não encontrado!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mostrarProdutos(){
        Iterator<Produto> itr = listaProdutos.iterator();

        while(itr.hasNext())
        {
            Produto produto = itr.next();
            System.out.printf("%s - R$ %.2f\n", produto.getNome(), produto.getPreco());
        }
    }

    public static void maiorPreco(){
        double maiorPreco = -1;
        String nome = null;
        for (Produto produto:listaProdutos) {
            if (maiorPreco == -1){
                maiorPreco = produto.getPreco();
                nome = produto.getNome();
            } else if (produto.getPreco() > maiorPreco){
                maiorPreco = produto.getPreco();
                nome = produto.getNome();
            }
        }
        System.out.printf("\nO maior preço de um produto é:\n%s - R$ %.2f\n", nome, maiorPreco);
    }

    public static void menorPreco(){
        double menorPreco = -1;
        String nome = null;
        for (Produto produto:listaProdutos) {
            if (menorPreco == -1) {
                menorPreco = produto.getPreco();
                nome = produto.getNome();
            } else if (produto.getPreco() < menorPreco) {
                menorPreco = produto.getPreco();
                nome = produto.getNome();
            }
        }
        System.out.printf("\nO menor preço de um produto é:\n%s - R$ %.2f\n", nome, menorPreco);
    }

    public static void mediaPrecos(){
        double soma = 0, media = 0;
        for (Produto produto:listaProdutos) {
            soma = soma + produto.getPreco();
        }
        media = soma / listaProdutos.size();
        System.out.printf("\nA média de preço dos produtos é: R$ %.2f\n", media);
    }

    public static void main(String[] args) {
        lerExcel();

        while (true) {
            try {
                Scanner entrada = new Scanner(System.in);
                System.out.println("\n-----CHAT XLSX PRODUTOS-----\n1 - Lista de produtos\n2 - Maior preço entre os produtos\n3 - Menor preço entre os produtos\n4 - Média de Preços\n5 - Sair\n");
                int opcao = entrada.nextInt();
                switch (opcao) {
                    case 1 -> mostrarProdutos();
                    case 2 -> maiorPreco();
                    case 3 -> menorPreco();
                    case 4 -> mediaPrecos();
                    case 5 -> System.out.println("Finalizando Chat");
                    default -> System.out.println("Opção não existente");
                }
                if (opcao == 5) {
                    entrada.close();
                    break;
                }
            } catch (Exception e) {
                System.out.println("Erro " + e);
            }
        }
    }
}
