import mysql.connector

conexao = mysql.connector.connect(
    host='localhost',
    database='ornamentum',
    user='root',
    password=''
)

cursor = conexao.cursor()


def inserir_modelos(produto1, precofinal1):
    sql = f"""INSERT INTO produtos(titulo, preco)
    values  ('{produto1}','{precofinal1}');"""
    cursor.execute(sql)
    conexao.commit()

def inserir_modelos2(produto2, precofinal2):
    sql = f"""INSERT INTO produtos(titulo, preco)
    values  ('{produto2}','{precofinal2}');"""
    cursor.execute(sql)
    conexao.commit()
def inserir_modelos3(produto3, precofinal3):
    sql = f"""INSERT INTO produtos(titulo, preco)
    values  ('{produto3}','{precofinal3}');"""
    cursor.execute(sql)
    conexao.commit()
def listar():
    sql = "SELECT * FROM produtos;"
    cursor.execute(sql)
    return cursor.fetchall()