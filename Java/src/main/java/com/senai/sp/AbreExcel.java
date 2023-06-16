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
    private static final String fileName = "C:\\Users\\37076111837\\Desktop\\website-integrador\\WebScraping-ProjetoIntegrador\\Excel-Produtos.xlsx";

    static List<Produto> listaProdutos = new ArrayList<>();

//    Função para ler o arquivo exel

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
                                double preco = cell.getNumericCellValue();
                                produto.setPreco(preco);
                            }
                        }
                    }
                }
            arquivo.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Excel not found!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


//    Função que mostra todos os produtos

    public static void mostrarProdutos(){
        Iterator<Produto> itr = listaProdutos.iterator();

        while(itr.hasNext())
        {
            Produto produto = itr.next();
            System.out.printf("%s - R$ %.2f\n", produto.getNome(), produto.getPreco());
        }
    }


//    Função que valida o maior preço

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
        System.out.printf("\nMore expensive:\n%s - $ %.2f\n", nome, maiorPreco);
    }


//    Função que valida menor preco

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
        System.out.printf("\nMore cheap:\n%s - $ %.2f\n", nome, menorPreco);
    }


//    Função que faz a média do preço de todos os produtos

    public static void mediaPrecos(){
        double soma = 0, media = 0;
        for (Produto produto:listaProdutos) {
            soma = soma + produto.getPreco();
        }
        media = soma / listaProdutos.size();
        System.out.printf("\n The price average is: $ %.2f\n", media);
    }


//    Função principal que chama todas as outras

    public static void main(String[] args) {
        lerExcel();
        Scanner entrada = new Scanner(System.in);
        System.out.print("What's your name: ");
        String nomee = entrada.next();
        System.out.println("Welcome " + nomee + "!!!");
        while (true) {
            try {
                System.out.print("\n-=-=-=-=-=BOT ORNAMENTUM PRODUCTS-=-=-=-=-=\n[1] - All Products\n[2] - More Expensive\n[3] - More cheap\n[4] - Price average\n[5] - Exit\n");
                System.out.print("\nOption: ");
                int opcao = entrada.nextInt();
                switch (opcao) {
                    case 1 -> mostrarProdutos();
                    case 2 -> maiorPreco();
                    case 3 -> menorPreco();
                    case 4 -> mediaPrecos();
                    case 5 -> System.out.println("Bye! Check back often");
                    default -> System.out.println("That option doesn't exist.");
                }
                if (opcao == 5) {
                    System.out.println("Finishing!!");
                    entrada.close();
                    break;
                }
            } catch (Exception e) {
                System.out.println("Erro " + e);
            }
        }
    }
}
