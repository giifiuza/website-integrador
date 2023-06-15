from selenium import webdriver
from selenium.webdriver.common.by import By
from bancoDados import listar, inserir_modelos, inserir_modelos2, inserir_modelos3
import pandas as pd

class Web:
    def __init__(self):
        self.site = {
            'site': 'https://ornamentum.netlify.app/views/products.html',
            'titulo': '/html/body/section[1]/div[$num$]/div/p[1]',
            'titulo2': '/html/body/section[2]/div[$num$]/div/p[1]',
            'titulo3': '/html/body/section[3]/div[$num$]/div/p[1]',
            'preco': '/html/body/section[1]/div[$preco$]/div/p[2]',
            'preco2': '/html/body/section[2]/div[$preco$]/div/p[2]',
            'preco3': '/html/body/section[3]/div[$preco$]/div/p[2]',
        }

        self.driver = webdriver.Chrome()
        self.driver.minimize_window()
        self.getDados()
        self.criarExcel()

    def getDados(self):
        lista = []
        titulos = []
        self.driver.get(self.site['site'])

        for i in range(1,4):
            produto1 = self.driver.find_element(By.XPATH, self.site['titulo2'].replace("$num$", str(i))).text
            preco1 = self.driver.find_element(By.XPATH, self.site['preco2'].replace("$preco$", str(i))).text
            precofinal1 = preco1.replace("$", "")
            inserir_modelos(produto1, precofinal1)

        for i in range(1,4):
            produto2 = self.driver.find_element(By.XPATH, self.site['titulo3'].replace("$num$", str(i))).text
            preco2 = self.driver.find_element(By.XPATH, self.site['preco3'].replace("$preco$", str(i))).text
            precofinal2 = preco2.replace("$", "")
            inserir_modelos2(produto2, precofinal2)

        for i in range(1,4):
            produto3 = self.driver.find_element(By.XPATH, self.site['titulo'].replace("$num$", str(i))).text
            preco3 = self.driver.find_element(By.XPATH, self.site['preco'].replace("$preco$", str(i))).text
            precofinal3 = preco3.replace("$", "")
            inserir_modelos3(produto3, precofinal3)

    def criarExcel(self):
        excelArq = pd.DataFrame(listar())
        header = ['Título', 'Preço']
        excelArq.to_excel('Excel-Produtos.xlsx', index=False, header=header)
