INSERT INTO tbl_cliente (nome, dinheiro) VALUES ('Wilson Pedro', 5000.0);
INSERT INTO tbl_cliente (nome, dinheiro) VALUES ('Ana Julia', 7000.0);
INSERT INTO tbl_cliente (nome, dinheiro) VALUES ('Julio Cezar', 4500.0);


INSERT INTO tbl_produto (nome_produto, quantidade, preco, sub_total, id_cliente, cliente_id) VALUES ('Relógio', 1, 20.0, 20.0, 1, 1);
INSERT INTO tbl_produto (nome_produto, quantidade, preco, sub_total,  id_cliente, cliente_id) VALUES ('Celular', 1, 2500.0, 25000, 2, 2);
INSERT INTO tbl_produto (nome_produto, quantidade, preco, sub_total,  id_cliente, cliente_id) VALUES ('Livro', 5, 850.0, 5420.0, 3, 3);

INSERT INTO tbl_compra (cliente_id, items) VALUES (1, 1);
INSERT INTO tbl_compra (cliente_id, items) VALUES (2, 1);
INSERT INTO tbl_compra (cliente_id, items) VALUES (3, 1);