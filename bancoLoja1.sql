drop table item_venda;
drop table venda;
drop table produto;
drop table cliente;
CREATE TABLE cliente(
codigo SERIAL NOT NULL PRIMARY KEY,
nome VARCHAR(50) NOT NULL,
email VARCHAR(50) NOT NULL UNIQUE,
senha VARCHAR(50) NOT NULL, 
telefone INTEGER
);

CREATE TABLE produto(
codigo SERIAL NOT NULL PRIMARY KEY,
nome VARCHAR(50) NOT NULL,
tipo VARCHAR(20) NOT NULL,
descricao VARCHAR(100) NOT NULL,
preco NUMERIC(8,3) NOT NULL, 
quantEstoque INTEGER NOT NULL, 
imagem VARCHAR(100) NOT NULL
);

CREATE TABLE venda(
codigo SERIAL NOT NULL PRIMARY KEY,
dataVenda DATE NOT NULL DEFAULT CURRENT_DATE,
valorTotal NUMERIC(8,2),
codigoCli INTEGER NOT NULL,
FOREIGN KEY (codigoCli) REFERENCES cliente(codigo)
);

CREATE TABLE item_venda(
codigo SERIAL NOT NULL PRIMARY KEY,
quantProd INTEGER NOT NULL,
preco NUMERIC(8,3) NOT NULL, 
codVenda INTEGER NOT NULL,
codProduto INTEGER NOT NULL,
FOREIGN KEY (codProduto) REFERENCES produto(codigo),
FOREIGN KEY (codVenda) REFERENCES venda(codigo)
);

INSERT INTO cliente (nome, email, senha, telefone) VALUES ('administrador', 'admin@admin', 123, 123456);

/*Função para controle de estoque*/
	CREATE OR REPLACE FUNCTION altera_estoque()
	RETURNS TRIGGER AS $altera_estoque$
	DECLARE
	
	BEGIN
		IF(TRUE) THEN
		
		UPDATE produto
		SET quantEstoque = quantEstoque - (SELECT quantProd FROM Item_venda WHERE codProduto = NEW.codProduto)
		WHERE codigo = new.codigo;

		END IF;
		RETURN NEW;
	END; $altera_estoque$ LANGUAGE PLPGSQL;	

CREATE TRIGGER controlaEstoque
AFTER INSERT ON item_venda 
FOR EACH ROW EXECUTE PROCEDURE altera_estoque();
/*
select * from produto
select * from item_venda
select * from venda
select * from cliente;
INSERT INTO item_venda (quantProd, preco, codVenda, codProduto) VALUES (1, 2, 14, 2);*/

